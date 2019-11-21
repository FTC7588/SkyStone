package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;


public class ShuttleSubsystem {

    Telemetry telemetry;
    Hardware hardware;

    public ShuttleSubsystem(Telemetry telem, HardwareMap hwmap) {
        telemetry = telem;

        hardware = new Hardware();

        hardware.init(hwmap);
    }

    public void setPower(double power) {
        hardware.shuttleDrive.setPower(power);
    }
}
