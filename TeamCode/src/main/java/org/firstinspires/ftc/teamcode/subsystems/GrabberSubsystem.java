package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;


public class GrabberSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    boolean grabberClosed =true;

    double          clawOffset      = 0;                       // Servo mid position
    final double    CLAW_SPEED      = 0.5 ;                   // sets rate to move servo
    final double    MID_SERVO       = .5;

    public GrabberSubsystem(Telemetry telem, Hardware hardware) {
        telemetry = telem;

        this.hardware = hardware;
    }

    public void toggleGrabber() {
        if(grabberClosed){
           hardware.grabber.setPosition(1);
           grabberClosed = false;

        }
        else {
            hardware.grabber.setPosition(.7);
            grabberClosed = true;
        }
    }
    public void setGrabberPosition (double grabberPosition) {
        hardware.grabber.setPosition(grabberPosition);
    }

    public void rotateGrabber(double amount) {
        // Use gamepad left & right Bumpers to open and close the claw

        //amount = amount*CLAW_SPEED;

        //clawOffset += amount;

        // Move both servos to new position.  Assume servos are mirror image of each other.
        //clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        hardware.rotGrabber.setPosition(.5 + amount);
    }

}
