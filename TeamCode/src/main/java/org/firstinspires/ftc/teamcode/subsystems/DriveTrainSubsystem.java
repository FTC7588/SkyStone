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

        hardware = new Hardware();

        this.hardware = hardware;

        frontLeft = hardware.frontLeft;
        frontRight = hardware.frontRight;
        backLeft = hardware.backLeft;
        backRight = hardware.backRight;

        swerveDrive = new SwerveDrive(backLeft, backRight, frontLeft, frontRight);

        backLeftPID.setOutputRange(-1,1);
        backLeftPID.enable();
        backRightPID.setOutputRange(-1,1);
        backRightPID.enable();
        frontLeftPID.setOutputRange(-1,1);
        frontLeftPID.enable();
        frontRightPID.setOutputRange(-1,1);
        frontRightPID.enable();

        imu = hardware.imu;
        angles = hardware.angles;
        gravity = hardware.gravity;
    }

    public void swerveDrive(double drive, double turn, double strafe) {
        swerveDrive.drive(strafe, drive, turn);
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
        //
        telemetry.addData("stuff", speedDirection);
        telemetry.update();
        //
        double first;
        double second;
        //</editor-fold>
        //
        if (speedDirection > 0){//set target positions
            //<editor-fold desc="turn right">
            if (degrees > 20){
                first = (degrees - 20) + devertify(yaw);
                second = degrees + devertify(yaw);
            }else{
                first = devertify(yaw);
                second = degrees + devertify(yaw);
            }
            //</editor-fold>
        }else{
            //<editor-fold desc="turn left">
            if (degrees > 20){
                first = devertify(-(degrees - 20) + devertify(yaw));
                second = devertify(-degrees + devertify(yaw));
            }else{
                first = devertify(yaw);
                second = devertify(-degrees + devertify(yaw));
            }
            //
            //</editor-fold>
        }
        //
        //<editor-fold desc="Go to position">
        Double firsta = convertify(first - 5);//175
        Double firstb = convertify(first + 5);//-175
        //
        turnWithEncoder(speedDirection);
        //
        if (Math.abs(firsta - firstb) < 11) {

            /**
             * add opmode is active here
             */

            while (!(firsta < yaw && yaw < firstb)) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("first before", first);
                telemetry.addData("first after", convertify(first));
                telemetry.update();
            }
        }else{

            /**
             * add opmode is active
             *
             */

            while (!((firsta < yaw && yaw < 180) || (-180 < yaw && yaw < firstb))) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("first before", first);
                telemetry.addData("first after", convertify(first));
                telemetry.update();
            }
        }
        //
        Double seconda = convertify(second - 5);//175
        Double secondb = convertify(second + 5);//-175
        //
        turnWithEncoder(speedDirection / 3);
        //
        if (Math.abs(seconda - secondb) < 11) {

            /**
             * add opmode is active
             */

            while (!(seconda < yaw && yaw < secondb)) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("second before", second);
                telemetry.addData("second after", convertify(second));
                telemetry.update();
            }
        }else {

            /**
             * add opmode is active
             */

            while (!((seconda < yaw && yaw < 180) || (-180 < yaw && yaw < secondb))) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("second before", second);
                telemetry.addData("second after", convertify(second));
                telemetry.update();
            }
        }
        //</editor-fold>
        //
     //   frontleft.setPower(0);
      //  frontright.setPower(0);
       // backleft.setPower(0);
       // backright.setPower(0);
    }

    /*
    WARNING: This Movement type is INCOMPATIBLE with your chassis, use is NOT RECOMMENDED
    This is our function for arcing, a special type of movement that allows for turning while moving.
    Use the angle and length to determine where the robot will end up.
     */
    public void arc(Double angle, Double length, Double speed){
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
       // frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //
        //frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
       // backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       // backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
       // backright.setTargetPosition(backright.getCurrentPosition() + rightd);
        //backleft.setTargetPosition(backleft.getCurrentPosition() + leftd);
        //
        //frontleft.setPower(0);
       // frontright.setPower(0);
       // backleft.setPower(speed);
        //backright.setPower((rightMotor / leftMotor) * speed);
        //
       // while (backleft.isBusy() && backright.isBusy()){}
        //
        //frontleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
       // backright.setPower(0);
        return;
    }

    /*
    These functions are used in the turnWithGyro function to ensure angle
    inputs are interpreted properly.
     */
    public double devertify(double degrees){
        if (degrees < 0){
            degrees = degrees + 360;
        }
        return degrees;
    }
    public double convertify(double degrees){
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
    public void turnWithEncoder(double input){
      //  frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //
        //frontleft.setPower(input);
        //frontright.setPower(-input);
       // backleft.setPower(input);
        //backright.setPower(-input);
    }

}
