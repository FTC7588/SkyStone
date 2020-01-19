package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.PIDController;
import org.firstinspires.ftc.teamcode.control.SwerveDrive;
import org.firstinspires.ftc.teamcode.control.WheelDrive;

public class DriveTrainSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    WheelDrive frontLeft;
    WheelDrive frontRight;
    WheelDrive backLeft;
    WheelDrive backRight;

    PIDController frontLeftPID;
    PIDController frontRightPID;
    PIDController backLeftPID;
    PIDController backRightPID;

    PIDController turnPID;

    private SwerveDrive swerveDrive;

    //Calculate encoder conversion
    Double width = 18.0; //inches
    Integer cpr = 28; //counts per rotation
    Integer gearratio = 20;
    Double diameter = 4.125;
    Double cpi = (cpr * gearratio)/(Math.PI * diameter); //counts per inch -> counts per rotation / circumference
    Double bias = 1.0;
    Double arcBias = 0.0;//Not recommended

    Double conversion = cpi * bias;
    Boolean exit = false;

    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;

    public DriveTrainSubsystem(Telemetry telem, Hardware hardware) {
        telemetry = telem;

        this.hardware = hardware;

        frontLeft = this.hardware.frontLeft;
        frontRight = this.hardware.frontRight;
        backLeft = this.hardware.backLeft;
        backRight = this.hardware.backRight;

        swerveDrive = new SwerveDrive(backLeft, backRight, frontLeft, frontRight, telemetry);

        backLeftPID = new PIDController(1,0,0);
        backRightPID = new PIDController(1,0,0);
        frontLeftPID = new PIDController(1,0,0);
        frontRightPID = new PIDController(1,0,0);

        turnPID = new PIDController(1,0,0);

        backLeftPID.setOutputRange(-1,1);
        backLeftPID.enable();
        backRightPID.setOutputRange(-1,1);
        backRightPID.enable();
        frontLeftPID.setOutputRange(-1,1);
        frontLeftPID.enable();
        frontRightPID.setOutputRange(-1,1);
        frontRightPID.enable();

        turnPID.setContinuous();
        turnPID.setInputRange(-180,180);
        turnPID.setOutputRange(-1,1);
        turnPID.enable();

        imu = hardware.imu;
        angles = hardware.angles;
        gravity = hardware.gravity;
    }

    public void swerveDrive(double drive, double turn, double strafe) {
        swerveDrive.drive(strafe, drive, turn);

        telemetry.addData("back left speed :", hardware.rearLeftEncoder.getAbsoluteTicks());
        telemetry.addData("back right speed :", hardware.rearRightEncoder.getAbsoluteTicks());
        telemetry.addData("front left speed :", hardware.frontLeftEncoder.getAbsoluteTicks());
        telemetry.addData("front right speed :", hardware.frontRightEncoder.getAbsoluteTicks());
    }

    /*
    This function's purpose is simply to drive forward or backward.
    To drive backward, simply make the inches input negative.
     */
    public void moveToPosition(double inches, double angle){
        backLeftPID.setSetpoint(inches*conversion);
        backRightPID.setSetpoint(inches*conversion);
        frontLeftPID.setSetpoint(inches*conversion);
        frontRightPID.setSetpoint(inches*conversion);

        while (!backLeftPID.onTarget() || !backRightPID.onTarget() || !frontLeftPID.onTarget() || !frontRightPID.onTarget()) {
            backLeft.drive(backLeftPID.performPID(hardware.rearLeftDrive.getCurrentPosition()), angle);
            backRight.drive(backRightPID.performPID(hardware.rearRightDrive.getCurrentPosition()), angle);
            frontLeft.drive(frontLeftPID.performPID(hardware.frontLeftDrive.getCurrentPosition()), angle);
            frontRight.drive(frontRightPID.performPID(hardware.frontRightDrive.getCurrentPosition()), angle);
        }
    }

    /*
    This function uses the Expansion Hub IMU Integrated Gyro to turn a precise number of degrees (+/- 5).
    Degrees should always be positive, make speedDirection negative to turn left.
     */
    public void turnWithGyro(double degrees, double speedDirection){
        //<editor-fold desc="Initialize">
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double yaw = -angles.firstAngle;//make this negative
        telemetry.addData("Speed Direction", speedDirection);
        telemetry.addData("Yaw", yaw);
        telemetry.update();

        turnPID.setSetpoint(degrees);

        turnPID.performPID(yaw);

        backLeft.drive(backLeftPID.performPID(hardware.rearLeftDrive.getCurrentPosition()), -45);
        backRight.drive(backRightPID.performPID(hardware.rearRightDrive.getCurrentPosition()), 45);
        frontLeft.drive(frontLeftPID.performPID(hardware.frontLeftDrive.getCurrentPosition()), 45);
        frontRight.drive(frontRightPID.performPID(hardware.frontRightDrive.getCurrentPosition()), -45);

        while (!turnPID.onTarget()) {
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            yaw = -angles.firstAngle;//make this negative
            telemetry.addData("Speed Direction", speedDirection);
            telemetry.addData("Yaw", yaw);
            telemetry.update();

            turnPID.setSetpoint(degrees);

            double power = turnPID.performPID(yaw);

            backLeft.drive(power, -45);
            backRight.drive(power, 45);
            frontLeft.drive(power, 45);
            frontRight.drive(power, -45);
        }
    }

    /*
    WARNING: This Movement type is INCOMPATIBLE with your chassis, use is NOT RECOMMENDED
    This is our function for arcing, a special type of movement that allows for turning while moving.
    Use the angle and length to determine where the robot will end up.
     */
    private void arc(Double angle, Double length, Double speed){
        //\frac{c*sin*(90-b)}{\sin2b}
        Double radius = ((length + arcBias) * Math.sin(Math.toRadians(90-angle)))/(Math.sin(Math.toRadians(2 * angle)));
        telemetry.addData("radius", radius);
        telemetry.update();
        //2\pi\left(r+a\right)\left(\frac{b}{180}\right)
        //2\pi\left(r-a\right)\left(\frac{b}{180}\right)
        //
        Double rightMotor;
        Double leftMotor;
        rightMotor = 2 * Math.PI * (radius - (width / 2)) * (angle / 180);
        leftMotor = 2 * Math.PI * (radius + (width / 2)) * (angle / 180);
        //
        int rightd = (int) (Math.round(rightMotor * conversion));
        int leftd = (int) (Math.round(leftMotor * conversion));
        //
        telemetry.addData("left motor", leftMotor + ", " + leftd);
        telemetry.addData("right motor", rightMotor + ", " + rightd);
        telemetry.update();
        //
        //frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //
        //frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
       // frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
       //backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       // backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        //backright.setTargetPosition(backright.getCurrentPosition() + rightd);
        //backleft.setTargetPosition(backleft.getCurrentPosition() + leftd);
        //
        //frontleft.setPower(0);
        //frontright.setPower(0);
        //backleft.setPower(speed);
       //backright.setPower((rightMotor / leftMotor) * speed);
        //
       // while (backleft.isBusy() && backright.isBusy()){}
        //
       // frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // backright.setPower(0);
        return;
    }

    /*
    These functions are used in the turnWithGyro function to ensure angle
    inputs are interpreted properly.
     */
    private double devertify(double degrees){
        if (degrees < 0){
            degrees = degrees + 360;
        }
        return degrees;
    }
    private double convertify(double degrees){
        if (degrees > 179){
            degrees = -(360 - degrees);
        } else if(degrees < -180){
            degrees = 360 + degrees;
        } else if(degrees > 360){
            degrees = degrees - 360;
        }
        return degrees;
    }

    /*
    This function is used in the turnWithGyro function to set the
    encoder mode and begin turning.
     */
    private void turnWithEncoder(double input){
        //frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //
       // frontleft.setPower(input);
    //    frontright.setPower(-input);
      //  backleft.setPower(input);
    //    backright.setPower(-input);
    }

}
