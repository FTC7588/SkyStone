package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;


public class GrabberSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    boolean bigGrabberClosed=true;
    boolean smallGrabberClosed=true;

    double          clawOffset      = 0;                       // Servo mid position
    final double    CLAW_SPEED      = 0.02 ;                   // sets rate to move servo
    final double    MID_SERVO       = .5;

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
        if(smallGrabberClosed){
            hardware.grabberSmall.setPosition(1);
            smallGrabberClosed = false;

        }
        else {
            hardware.grabberSmall.setPosition(0);
            smallGrabberClosed = true;
        }
    }
    public void setSmallGrabberPosition (int grabberPosition) {
        hardware.grabberSmall.setPosition(grabberPosition);
    }

    public void rotGrabber(double amount) {
        // Use gamepad left & right Bumpers to open and close the claw

        amount = amount*CLAW_SPEED;

        clawOffset += amount;

        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        hardware.rotGrabber.setPosition(MID_SERVO + clawOffset);
        hardware.rotGrabber.setPosition(MID_SERVO - clawOffset);
    }

}
