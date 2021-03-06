package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.IO;


public class FoundationMoverSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    boolean toggleFoundationGrabberClosed;

    public FoundationMoverSubsystem(Telemetry telem, Hardware hardware) {
        telemetry = telem;

        this.hardware = hardware;

        toggleFoundationGrabberClosed = true;
    }

    public void setPosition(double position) {
        hardware.foundationGrabberLeft.setPosition(position);
        hardware.foundationGrabberRight.setPosition(position);
    }

    public void toggleFoundationGrabber() {
        if(toggleFoundationGrabberClosed){
            toggleFoundationGrabberClosed = false;
            hardware.foundationGrabberLeft.setPosition(0);
            hardware.foundationGrabberRight.setPosition(1);
        }
        else {
            toggleFoundationGrabberClosed = true;
            hardware.foundationGrabberLeft.setPosition(.9);
            hardware.foundationGrabberRight.setPosition(.1);
        }
    }
}