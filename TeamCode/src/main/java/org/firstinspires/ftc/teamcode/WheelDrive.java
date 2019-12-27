package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class WheelDrive {
    private DcMotor drive;
    private Servo turn;
    private PIDController pidController;

    private double countsPerDegree = 2048/2;

    public WheelDrive (Servo angleMotor, DcMotor speedMotor, Encoder encoder, Telemetry telemetry) {
        this.drive = speedMotor;
        this.turn = angleMotor;
        pidController = new PIDController (1, 0, 0, 5, encoder, turn, telemetry);

        pidController.enable();
    }

    public void drive (double speed, double angle) {
        drive.setPower(speed);

        int setpoint = (int)(angle*countsPerDegree);

        //pidController.setPoint(setpoint);

        pidController.update();
    }
}
