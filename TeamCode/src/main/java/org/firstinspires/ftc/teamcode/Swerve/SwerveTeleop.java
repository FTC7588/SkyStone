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

package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic Swerve Teleop", group="Swerve")
//@Disabled
public class SwerveTeleop extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    // State used for updating telemetry
    private Orientation angles;
    private Acceleration gravity;

    private SwerveHardware swerve = new SwerveHardware();

    private double voltages = 0;
    private int volatgesRead = 0;
    private double voltage = 0;
    private int normInterval = 250;
    private boolean downBounce = false;
    private boolean upBounce = false;

    private WheelDrive backRight;
    private WheelDrive backLeft;
    private WheelDrive frontRight;
    private WheelDrive frontLeft;

    private SwerveDrive swerveDrive;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        swerve.init(hardwareMap);

        backLeft = new WheelDrive(swerve.rearLeftTurn, swerve.rearLeftDrive, swerve.rearLeftEncoder);
        backRight = new WheelDrive(swerve.rearRightTurn, swerve.rearRightDrive, swerve.rearRightEncoder);
        frontLeft = new WheelDrive(swerve.frontLeftTurn, swerve.frontLeftDrive, swerve.frontLeftEncoder);
        frontRight = new WheelDrive(swerve.frontRightTurn, swerve.frontRightDrive, swerve.frontRightEncoder);

        swerveDrive = new SwerveDrive(backLeft, backRight, frontLeft, frontRight);

        angles   = swerve.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity  = swerve.imu.getGravity();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Rear Left Encoder Value: ", swerve.rearLeftEncoder.getAbsoluteTicks());
        telemetry.addData("Rear Right Encoder Value: ", swerve.rearRightEncoder.getAbsoluteTicks());
        telemetry.addData("Front Left Encoder Value: ", swerve.frontLeftEncoder.getAbsoluteTicks());
        telemetry.addData("Front Right Encoder Value: ", swerve.frontRightEncoder.getAbsoluteTicks());

        telemetry.addData("","");

        telemetry.addData("Front Left Drive", swerve.frontLeftDrive.getCurrentPosition());
        telemetry.addData("Front Right Drive", swerve.frontRightDrive.getCurrentPosition());
        telemetry.addData("Rear Left Drive", swerve.rearLeftDrive.getCurrentPosition());
        telemetry.addData("Rear Right Drive", swerve.rearRightDrive.getCurrentPosition());

        //backLeft.drive(0,gamepad1.left_stick_y);
        //backRight.drive(0,gamepad1.left_stick_y);
        //frontLeft.drive(0,gamepad1.left_stick_y);
        //frontRight.drive(0,gamepad1.left_stick_y);

        swerveDrive.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, telemetry);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
