package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class IO {

    Gamepad gamepad1;
    Gamepad gamepad2;

    public IO (Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public double meccPower() {
        return gamepad1.left_stick_y;
    }

    public double meccStrafe() {
        return gamepad1.left_stick_x;
    }

    public double meccTurn() {
        return gamepad1.right_stick_x;
    }

    public double shuttlePower() {
        double power = -gamepad1.right_stick_y;

        return power;
    }

    public double elevator() {
        double power = -gamepad2.left_stick_y /2 +.05;

        return power;
    }

    public boolean foundationGrabber() {
        return gamepad2.b;
    }

    public boolean grabberToggle() {
        return gamepad1.right_bumper;
    }

    public double rotGrabberPositive() {
        return gamepad2.right_trigger;
    }

    public double rotGrabberNegative() {
        return gamepad2.left_trigger;
    }

    public boolean intake() {
        return gamepad2.a;
    }

    public boolean outTake() {
        return gamepad2.y;
    }
}
