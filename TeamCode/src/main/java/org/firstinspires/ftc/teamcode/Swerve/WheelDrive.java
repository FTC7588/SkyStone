package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;

public class WheelDrive {
    private DcMotor drive;
    private CRServo turn;
    private Encoder encoder;
    private PIDController pidController;

    private double countsPerDegree = 2048/1;

    public WheelDrive (CRServo angleMotor, DcMotor speedMotor, Encoder encoder) {
        this.drive = speedMotor;
        this.turn = angleMotor;
        this.encoder = encoder;

        pidController = new PIDController (.001, 0, 0);

        pidController.setOutputRange(-1.0,1.0);
        pidController.setInputRange(-2048, 2048);
        pidController.setContinuous();
    }

    public void drive (double speed, double angle) {
        pidController.enable();

        drive.setPower(speed*.33);

        int setpoint = (int)(angle*countsPerDegree);

        pidController.setSetpoint(setpoint);

        turn.setPower(-pidController.performPID(encoder.getAbsoluteTicks()));
    }
}
