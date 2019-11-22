package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.IO;


public class FoundationMoverSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    public FoundationMoverSubsystem(Telemetry telem, HardwareMap hwmap) {
        telemetry = telem;

        hardware = new Hardware();

        hardware.init(hwmap);
    }

    public void setPosition (double position) {


        hardware.foundationGrabberLeft.setPosition(position);
        hardware.foundationGrabberRight.setPosition(position);
    }
            }
