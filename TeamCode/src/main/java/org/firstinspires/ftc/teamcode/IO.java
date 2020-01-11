package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class IO {

    Gamepad gamepad1;
    Gamepad gamepad2;

    public IO (Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public double swervepower() {
        return gamepad1.left_stick_y;
    }

    public double swerveStrafe() {
        return gamepad1.left_stick_x;
    }

    public double swerveturn() {
        return gamepad1.right_stick_x;
    }

    public double shuttlePower() {
        double power = -gamepad1.right_stick_y;

        return power;
    }

    public double turn() {
        double power = gamepad1.left_stick_x;

        return power;
    }

    public double drive() {
        double power = -gamepad1.left_stick_y;

        return power;
    }

    public double elevator() {
        double power = -gamepad2.left_stick_y /2 +.05;

        return power;
    }

    public boolean foundationGrabber() {
        return gamepad2.b;
    }

    public boolean bigGrabberToggle() {
        return gamepad1.right_bumper;
    }

    public boolean smallGrabberToggle() {
        return gamepad2.y;
    }

    public double rotGrabberPositive() {
        return gamepad2.right_trigger;
    }

    public double rotGrabberNegative() {
        return gamepad2.left_trigger;
    }
}
