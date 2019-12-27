package org.firstinspires.ftc.teamcode;

import android.sax.TextElementListener;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.DifferentialControlLoopCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDController {
    private double P;
    private double I;
    private double D;
    private int tol;
    private double lastError = 1;
    private double lastTime = 1;
    private double deltaTime = 1;
    private boolean enabled = false;
    private Encoder encoder;
    private Servo driven;
    private ElapsedTime time = new ElapsedTime();
    private Telemetry telemetry;

    int setPoint;

    public PIDController (double P, double I, double D, int tol, Encoder encoder, Servo driven, Telemetry telemetry) {
        this.P = P;
        this.I = I;
        this.D = D;
        this.tol = tol;
        this.encoder = encoder;
        this.driven = driven;

        this.telemetry = telemetry;
    }

    public void update() {
        if (enabled && !(encoder.getAbsoluteTicks() < setPoint-tol) && !(encoder.getAbsoluteTicks() > setPoint + tol)) {
            double error = setPoint - encoder.getAbsoluteTicks();

            double calculated_value = P * error + D * ((error - lastError) / deltaTime);

            driven.setPosition(calculated_value);

            //driven.setPosition(-1);

            telemetry.addData("calculated value :", calculated_value);

            lastError = error;
            deltaTime = lastTime - getTime();
            lastTime = getTime();
        } else {
            driven.setPosition(0);
        }
    }

    public void setPoint(int setPoint) {
        this.setPoint = setPoint;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    private double getTime() {
        return time.milliseconds();
    }
}
