package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;


public class DriveTrainSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    public DriveTrainSubsystem(Telemetry telem, HardwareMap hwmap) {
        telemetry = telem;

        hardware = new Hardware();

        hardware.init(hwmap);
    }

    public void arcadeDrive(double drive, double turn) {

       double left;
       double right;
       double max;

        // Combine drive and turn for blended motion.
        left  = drive + turn;
        right = drive - turn;

        // Normalize the values so neither exceed +/- 1.0
        max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0)
        {
            left /= max;
            right /= max;
        }

        hardware.frontRightDrive.setPower(right);
        hardware.backRightDrive.setPower(right);
        hardware.frontLeftDrive.setPower(left);
        hardware.backLeftDrive.setPower(left);
    }
}
