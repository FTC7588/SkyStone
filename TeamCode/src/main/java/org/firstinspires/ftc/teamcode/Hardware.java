/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.control.Encoder;
import org.firstinspires.ftc.teamcode.control.WheelDrive;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class Hardware
{
    /* Public OpMode members. */
    public DcMotor  frontRightDrive = null;
    public DcMotor  rearRightDrive = null;
    public DcMotor  frontLeftDrive =  null;
    public DcMotor  rearLeftDrive =  null;

    public DcMotor  shuttleDrive =  null;
    public DcMotor  elevatorRight = null;
    public DcMotor  elevatorLeft = null;

    private DcMotor rearLeftEncoderPort;
    private DcMotor rearRightEncoderPort;
    private DcMotor frontLeftEncoderPort;
    private DcMotor frontRightEncoderPort;

    public Servo foundationGrabberLeft = null;
    public Servo foundationGrabberRight = null;
    public Servo grabber = null;
    public Servo rotGrabber = null;

    public CRServo rearLeftTurn;
    public CRServo rearRightTurn;
    public CRServo frontLeftTurn;
    public CRServo frontRightTurn;

    public Encoder rearLeftEncoder;
    public Encoder rearRightEncoder;
    public Encoder frontLeftEncoder;
    public Encoder frontRightEncoder;

    public WheelDrive backRight;
    public WheelDrive backLeft;
    public WheelDrive frontRight;
    public WheelDrive frontLeft;

    public BNO055IMU imu;
    public Orientation angles;
    public Acceleration gravity;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;

    /* Constructor */
    public Hardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        rearRightDrive  = hwMap.get(DcMotor.class, "backRightDrive");
        rearLeftDrive  = hwMap.get(DcMotor.class, "backLeftDrive");
        frontRightDrive = hwMap.get(DcMotor.class, "frontRightDrive");
        frontLeftDrive    = hwMap.get(DcMotor.class, "frontLeftDrive");

        // Encoders
        rearLeftEncoderPort = hwMap.get(DcMotor.class, "rearLeftEncoderPort");
        rearRightEncoderPort = hwMap.get(DcMotor.class, "rearRightEncoderPort");
        frontLeftEncoderPort = hwMap.get(DcMotor.class, "frontLeftEncoderPort");
        frontRightEncoderPort = hwMap.get(DcMotor.class, "frontRightEncoderPort");

        elevatorLeft   = hwMap.get(DcMotor.class, "elevatorLeft");
        elevatorRight  = hwMap.get(DcMotor.class, "elevatorRight");

        shuttleDrive = hwMap.get(DcMotor.class, "shuttleDrive");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        rearLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        rearRightDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);

        elevatorLeft.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        elevatorRight.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors

        shuttleDrive.setDirection(DcMotor.Direction.REVERSE);

        // Stop drive motors
        rearLeftDrive.setPower(0);
        rearRightDrive.setPower(0);
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);

        // Stop Encoder ports
        // This isn't really necessary
        rearLeftEncoderPort.setPower(0);
        rearRightEncoderPort.setPower(0);
        frontLeftEncoderPort.setPower(0);
        frontRightEncoderPort.setPower(0);

        elevatorRight.setPower(0);
        elevatorLeft.setPower(0);

        shuttleDrive.setPower(0);

        // The Rev Robotics Expansion Hub need a zero power behavior specified
        rearLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        elevatorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevatorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shuttleDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rearLeftEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        elevatorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevatorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shuttleDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rearLeftEncoder = new Encoder(rearLeftEncoderPort, 4096);
        rearRightEncoder = new Encoder(rearRightEncoderPort, 4096);
        frontLeftEncoder = new Encoder(frontLeftEncoderPort, 4096);
        frontRightEncoder = new Encoder(frontRightEncoderPort, 4096);



        // Define and initialize ALL installed servos.
        foundationGrabberLeft  = hwMap.get(Servo.class, "foundationLeft");
        foundationGrabberRight = hwMap.get(Servo.class, "foundationRight");
        grabber = hwMap.get(Servo.class, "grabber");
        rotGrabber = hwMap.get(Servo.class, "grabberRotator");

        rearLeftTurn = hwMap.get(CRServo.class, "rearLeftTurn");
        rearRightTurn = hwMap.get(CRServo.class, "rearRightTurn");
        frontLeftTurn = hwMap.get(CRServo.class, "frontLeftTurn");
        frontRightTurn = hwMap.get(CRServo.class, "frontRightTurn");

        // Stop the turning Servos
        rearLeftTurn.setPower(0);
        rearRightTurn.setPower(0);
        frontLeftTurn.setPower(0);
        frontRightTurn.setPower(0);



        backLeft = new WheelDrive(rearLeftTurn, rearLeftDrive, rearLeftEncoder);
        backRight = new WheelDrive(rearRightTurn, rearRightDrive, rearRightEncoder);
        frontLeft = new WheelDrive(frontLeftTurn, frontLeftDrive, frontLeftEncoder);
        frontRight = new WheelDrive(frontRightTurn, frontRightDrive, frontRightEncoder);
    }

    public void initGyro() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        //parameters.calibrationDataFile = "GyroCal.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void resetDriveEncoders() {
        rearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rearLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void resetTurnEncoeders() {
        rearLeftEncoderPort.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightEncoderPort.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftEncoderPort.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightEncoderPort.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rearLeftEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void resetElevatorEncoders(){
        elevatorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elevatorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevatorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void resetShuttleEncoder() {
        shuttleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        shuttleDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}


