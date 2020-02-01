package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
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

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;
    //28 * 20 / (2ppi * 4.125)
    Double width = 18.0; //inches
    Integer cpr = 28; //counts per rotation
    Integer gearratio = 20;
    Double diameter = 3.0;
    Double cpi = (cpr * gearratio)/(Math.PI * diameter); //counts per inch, 28cpr * gear ratio / (2 * pi * diameter (in inches, in the center))
    Double bias = 0.85;//default 0.8
    Double meccyBias = 0.9;//change to adjust only strafing movement
    //
    Double conversion = cpi * bias;
    Boolean exit = false;
    //
    BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;

    double angleFromDriver;

    public DriveTrainSubsystem(Telemetry telem, Hardware hardware) {
        telemetry = telem;

        this.hardware = hardware;

        frontleft = this.hardware.frontLeftDrive;
        frontright = this.hardware.frontRightDrive;
        backleft = this.hardware.rearLeftDrive;
        backright = this.hardware.rearRightDrive;

        imu = hardware.imu;
        angles = hardware.angles;
        gravity = hardware.gravity;
    }

    public void meccanumDrive (double leftX, double leftY, double rightX) {
        double fl;
        double fr;
        double bl;
        double br;

        double jTheta;
        double jp;
        double theta;

        double heading = angles.firstAngle;

        /**jTheta = Math.toDegrees(Math.atan2(-leftY,leftX));
        jp = Math.sqrt(leftX * leftX + leftY * leftY);
        if (jp > 1)
            jp = 1;
        theta = Math.toRadians(jTheta + angleFromDriver - heading);
        fl = (Math.sin(theta) + Math.cos(theta)) * jp / 2 + rightX;
        fr = (Math.sin(theta) - Math.cos(theta)) * jp / 2 - rightX;
        bl = (Math.sin(theta) - Math.cos(theta)) * jp / 2 + rightX;
        br = (Math.sin(theta) + Math.cos(theta)) * jp / 2 - rightX;*/

        fl = leftY+leftX+rightX;
        fr = leftY-leftX-rightX;
        bl = leftY-leftX+rightX;
        br = leftY+leftX-rightX;

        frontleft.setPower(fl);
        frontright.setPower(fr);
        backleft.setPower(bl);
        backright.setPower(br);
    }

    /*
   This function's purpose is simply to drive forward or backward.
   To drive backward, simply make the inches input negative.
    */
    private void moveToPosition(double inches, double speed){
        //
        int move = (int)(Math.round(inches*conversion));
        //
        backleft.setTargetPosition(backleft.getCurrentPosition() + move);
        frontleft.setTargetPosition(frontleft.getCurrentPosition() + move);
        backright.setTargetPosition(backright.getCurrentPosition() + move);
        frontright.setTargetPosition(frontright.getCurrentPosition() + move);
        //
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        frontleft.setPower(speed);
        backleft.setPower(speed);
        frontright.setPower(speed);
        backright.setPower(speed);
        //
        while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()){
            if (exit){
                frontright.setPower(0);
                frontleft.setPower(0);
                backright.setPower(0);
                backleft.setPower(0);
                return;
            }
        }
        frontright.setPower(0);
        frontleft.setPower(0);
        backright.setPower(0);
        backleft.setPower(0);
        return;
    }

    /*
   This function's purpose is simply to drive forward or backward.
   To drive backward, simply make the inches input negative.
    */
    public void moveToPositionPID(double inches, double maxSpeed){
        //
        int move = (int)(Math.round(inches*conversion));
        //

        telemetry.addData("move: ", move);

        PIDController backLeftPID = new PIDController(.01,0,0);
        PIDController backRightPID = new PIDController(.01,0,0);
        PIDController frontLeftPID = new PIDController(.01,0,0);
        PIDController frontRightPID = new PIDController(.01,0,0);

        backLeftPID.setSetpoint(backleft.getCurrentPosition() + move);
        frontLeftPID.setSetpoint(frontleft.getCurrentPosition() + move);
        backRightPID.setSetpoint(backright.getCurrentPosition() + move);
        frontRightPID.setSetpoint(frontright.getCurrentPosition() + move);
        //
        frontLeftPID.setTolerance(5);
        frontRightPID.setTolerance(5);
        backLeftPID.setTolerance(5);
        backRightPID.setTolerance(5);

        frontLeftPID.setOutputRange(-maxSpeed, maxSpeed);
        frontRightPID.setOutputRange(-maxSpeed, maxSpeed);
        backLeftPID.setOutputRange(-maxSpeed, maxSpeed);
        backRightPID.setOutputRange(-maxSpeed, maxSpeed);

        frontLeftPID.enable();
        frontRightPID.enable();
        backLeftPID.enable();
        backRightPID.enable();
        //
        frontleft.setPower(frontLeftPID.performPID(frontleft.getCurrentPosition()));
        backleft.setPower(backLeftPID.performPID(backleft.getCurrentPosition()));
        frontright.setPower(frontRightPID.performPID(frontright.getCurrentPosition()));
        backright.setPower(backRightPID.performPID(backright.getCurrentPosition()));
        //

        telemetry.addData("","");
        telemetry.addData("front left :", frontleft.getCurrentPosition());
        telemetry.addData("front right :", frontright.getCurrentPosition());
        telemetry.addData("back left :", backleft.getCurrentPosition());
        telemetry.addData("back right :", backright.getCurrentPosition());
        telemetry.addData("","");
        telemetry.addData("front left :", frontLeftPID.performPID(frontleft.getCurrentPosition()));
        telemetry.addData("front right :", backLeftPID.performPID(frontright.getCurrentPosition()));
        telemetry.addData("rear left :", frontRightPID.performPID(backleft.getCurrentPosition()));
        telemetry.addData("back right :", backRightPID.performPID(backright.getCurrentPosition()));
        telemetry.update();


        while (!frontLeftPID.onTarget() && !backLeftPID.onTarget() && !frontRightPID.onTarget() && !backRightPID.onTarget()){
            frontleft.setPower(frontLeftPID.performPID(frontleft.getCurrentPosition()));
            backleft.setPower(backLeftPID.performPID(backleft.getCurrentPosition()));
            frontright.setPower(frontRightPID.performPID(frontright.getCurrentPosition()));
            backright.setPower(backRightPID.performPID(backright.getCurrentPosition()));

            telemetry.addData("","");
            telemetry.addData("front left :", frontleft.getCurrentPosition());
            telemetry.addData("front right :", frontright.getCurrentPosition());
            telemetry.addData("back left :", backleft.getCurrentPosition());
            telemetry.addData("back right :", backright.getCurrentPosition());
            telemetry.addData("","");
            telemetry.addData("front left :", frontLeftPID.performPID(frontleft.getCurrentPosition()));
            telemetry.addData("front right :", backLeftPID.performPID(frontright.getCurrentPosition()));
            telemetry.addData("rear left :", frontRightPID.performPID(backleft.getCurrentPosition()));
            telemetry.addData("back right :", backRightPID.performPID(backright.getCurrentPosition()));
            telemetry.update();
        }
        frontright.setPower(0);
        frontleft.setPower(0);
        backright.setPower(0);
        backleft.setPower(0);
        return;
    }

    //
    /*
    This function uses the Expansion Hub IMU Integrated Gyro to turn a precise number of degrees (+/- 5).
    Degrees should always be positive, make speedDirection negative to turn left.
     */
    private void turnWithGyro(double degrees, double speedDirection){
        //<editor-fold desc="Initialize">
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
            if (degrees > 10){
                first = (degrees - 10) + devertify(yaw);
                second = degrees + devertify(yaw);
            }else{
                first = devertify(yaw);
                second = degrees + devertify(yaw);
            }
            //</editor-fold>
        }else{
            //<editor-fold desc="turn left">
            if (degrees > 10){
                first = devertify(-(degrees - 10) + devertify(yaw));
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
            //
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
            while (!(seconda < yaw && yaw < secondb)) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("second before", second);
                telemetry.addData("second after", convertify(second));
                telemetry.update();
            }
            while (!((seconda < yaw && yaw < 180) || (-180 < yaw && yaw < secondb))) {//within range?
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                gravity = imu.getGravity();
                yaw = -angles.firstAngle;
                telemetry.addData("Position", yaw);
                telemetry.addData("second before", second);
                telemetry.addData("second after", convertify(second));
                telemetry.update();
            }
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);
        }
        //</editor-fold>
        //
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /*
    This function uses the Expansion Hub IMU Integrated Gyro to turn a precise number of degrees (+/- 5).
    Degrees should always be positive, make speedDirection negative to turn left.
     */
    private void turnWithGyroPID(double degrees, double speedDirection){
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double yaw = -angles.firstAngle;//make this negative
        telemetry.addData("Speed Direction", speedDirection);
        telemetry.addData("Yaw", yaw);
        telemetry.update();
        //
        telemetry.addData("stuff", speedDirection);
        telemetry.update();

        PIDController turnPID = new PIDController(1,0,0);

        turnPID.setTolerance(5);
        turnPID.setContinuous(true);
        turnPID.setInputRange(-180,180);
        turnPID.setSetpoint(degrees);
        turnPID.enable();

        double turnPower = turnPID.performPID(angles.firstAngle);

        backleft.setPower(turnPower);
        frontleft.setPower(turnPower);
        backright.setPower(-turnPower);
        frontright.setPower(-turnPower);

        while (!turnPID.onTarget()) {
            turnPower = turnPID.performPID(angles.firstAngle);

            backleft.setPower(turnPower);
            frontleft.setPower(turnPower);
            backright.setPower(-turnPower);
            frontright.setPower(-turnPower);
        }

    }

    //
    /*
    This function uses the encoders to strafe left or right.
    Negative input for inches results in left strafing.
     */
    private void strafeToPosition(double inches, double speed){
        //
        int move = (int)(Math.round(inches * cpi * meccyBias));
        //
        backleft.setTargetPosition(backleft.getCurrentPosition() - move);
        frontleft.setTargetPosition(frontleft.getCurrentPosition() + move);
        backright.setTargetPosition(backright.getCurrentPosition() + move);
        frontright.setTargetPosition(frontright.getCurrentPosition() - move);
        //
        frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        frontleft.setPower(speed);
        backleft.setPower(speed);
        frontright.setPower(speed);
        backright.setPower(speed);
        //
        while (frontleft.isBusy() && frontright.isBusy() && backleft.isBusy() && backright.isBusy()){}
        frontright.setPower(0);
        frontleft.setPower(0);
        backright.setPower(0);
        backleft.setPower(0);
        return;
    }
    //
    /*
    These functions are used in the turnWithGyro function to ensure inputs
    are interpreted properly.
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
    //
    /*
    This function is called at the beginning of the program to activate
    the IMU Integrated Gyro.
     */
    public void initGyro(){
        hardware.initGyro();
        angles = hardware.angles;
        gravity = hardware.gravity;
    }
    //
    /*
    This function is used in the turnWithGyro function to set the
    encoder mode and turn.
     */
    private void turnWithEncoder(double input){
        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //
        frontleft.setPower(input);
        backleft.setPower(input);
        frontright.setPower(-input);
        backright.setPower(-input);
    }
    //
}
