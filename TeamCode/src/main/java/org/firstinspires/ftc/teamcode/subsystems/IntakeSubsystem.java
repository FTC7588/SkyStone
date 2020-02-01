package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;


public class IntakeSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    boolean grabberClosed =true;

    double          clawOffset      = 0;                       // Servo mid position
    final double    CLAW_SPEED      = 0.02 ;                   // sets rate to move servo
    final double    MID_SERVO       = .5;

    public IntakeSubsystem(Telemetry telem, Hardware hardware) {
        telemetry = telem;

        this.hardware = hardware;
    }

    public void setpower (double power) {
        hardware.intake.setPower(power);
    }
}
