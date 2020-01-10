package org.firstinspires.ftc.teamcode.control;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.control.WheelDrive;

public class SwerveDrive {
    private WheelDrive backRight;
    private WheelDrive backLeft;
    private WheelDrive frontRight;
    private WheelDrive frontLeft;

    public final double L = 17.5;
    public final double W = 16;

    public SwerveDrive(WheelDrive backLeft, WheelDrive backRight, WheelDrive frontLeft, WheelDrive frontRight) {
        this.backRight = backRight;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
    }

    public void drive(double x1, double y1, double x2, Telemetry telemetry) {
        double r = Math.sqrt((L * L) + (W * W));
        y1 *= 1;

        double a = x1 - x2 * (L / r);
        double b = x1 + x2 * (L / r);
        double c = y1 - x2 * (W / r);
        double d = y1 + x2 * (W / r);

        double backRightSpeed = Math.sqrt ((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));

        double backRightAngle = Math.atan2 (a, d) / Math.PI;
        double backLeftAngle = Math.atan2 (a, c) / Math.PI;
        double frontRightAngle = Math.atan2 (b, d) / Math.PI;
        double frontLeftAngle = Math.atan2 (b, c) / Math.PI;

        telemetry.addData("back left angle :", -backLeftAngle);
        telemetry.addData("back right angle :", -backRightAngle);
        telemetry.addData("front left angle :", -frontLeftAngle);
        telemetry.addData("front right angle :", -frontRightAngle);

        telemetry.addData("","");

        telemetry.addData("back left speed :", backLeftSpeed);
        telemetry.addData("back right speed :", backRightSpeed);
        telemetry.addData("front left speed :", -frontLeftSpeed);
        telemetry.addData("front right speed :", -frontRightSpeed);

        backRight.drive (backRightSpeed, -backRightAngle);
        backLeft.drive (backLeftSpeed, -backLeftAngle);
        frontRight.drive (-frontRightSpeed, -frontRightAngle);
        frontLeft.drive (-frontLeftSpeed, -frontLeftAngle);
    }
}
