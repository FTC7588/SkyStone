package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;


public class GrabberSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    boolean bigGrabberClosed=true;
    boolean smallGrabberClosed=true;

    public GrabberSubsystem(Telemetry telem, HardwareMap hwmap) {
        telemetry = telem;

        hardware = new Hardware();

        hardware.init(hwmap);
    }

    public void toggleBigGrabber() {
        if(bigGrabberClosed){
           hardware.grabberBig.setPosition(1);
           bigGrabberClosed = false;

        }
        else {
            hardware.grabberBig.setPosition(0);
            bigGrabberClosed = true;
        }
    }
    public void setBigGrabberPosition (int grabberPosition) {
        hardware.grabberBig.setPosition(grabberPosition);
    }
    public void toggleSmallGrabber() {
        if(bigGrabberClosed){
            hardware.grabberSmall.setPosition(1);
            bigGrabberClosed = false;

        }
        else {
            hardware.grabberSmall.setPosition(0);
            smallGrabberClosed = true;
        }
    }
    public void setSmallGrabberPosition (int grabberPosition) {
        hardware.grabberSmall.setPosition(grabberPosition);
    }

}
