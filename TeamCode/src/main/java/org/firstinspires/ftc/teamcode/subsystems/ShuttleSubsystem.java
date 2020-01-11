package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.PIDController;


public class ShuttleSubsystem {

    Telemetry telemetry;
    Hardware hardware;

    PIDController pid;

    double ticksToInch = 1024/(1.25*2*Math.PI);

    public ShuttleSubsystem(Telemetry telem, Hardware hardware) {
        telemetry = telem;

        this.hardware = hardware;

        pid = new PIDController(1,0,0);

        pid.setOutputRange(-1.0,1.0);
        pid.setInputRange(0,6000);
        pid.enable();
    }

    public void setPower(double power) {
        hardware.shuttleDrive.setPower(power);
    }

    public void setHieghht(double inches) {
        pid.setSetpoint(inches*ticksToInch);
    }

    public void goToHieght() {
        hardware.elevatorLeft.setPower(pid.performPID(hardware.elevatorRight.getCurrentPosition()));
    }

    public boolean getLowerSoftLimit() {
        return hardware.elevatorLeft.getCurrentPosition() <= 0;
    }

    public boolean getUpperSoftLimit() {
        return hardware.elevatorLeft.getCurrentPosition() >= 880000;
    }

    public int getCurrentPosistion() {
        return hardware.elevatorLeft.getCurrentPosition();
    }

    public double getCurrentHieght() {
        return hardware.elevatorLeft.getCurrentPosition()*ticksToInch;
    }
}
