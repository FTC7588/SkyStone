package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.teamcode.control.PIDController;


public class ElevatorSubsystem {

    Telemetry telemetry;

    Hardware hardware;

    PIDController leftPID;
    PIDController rightPID;

    double ticksToInch = (28*20)/(1*Math.PI);

    public ElevatorSubsystem(Telemetry telem, Hardware hardware) {
        telemetry = telem;

        this.hardware = hardware;

        leftPID = new PIDController(1,0,0);
        rightPID = new PIDController(1,0,0);

        leftPID.setOutputRange(-1.0,1.0);
        leftPID.setInputRange(0,6000);
        leftPID.enable();

        rightPID.setOutputRange(-1.0,1.0);
        rightPID.setInputRange(0,6000);
        rightPID.enable();
    }

    public void setPower(double power) {
        hardware.elevatorLeft.setPower(power);
        hardware.elevatorRight.setPower(-power);
    }

    public void setHieghht(double inches) {
        leftPID.setSetpoint(inches*ticksToInch);
        rightPID.setSetpoint(inches*ticksToInch);
    }

    public void goToHieght() {
        hardware.elevatorLeft.setPower(leftPID.performPID(hardware.elevatorRight.getCurrentPosition()));
        hardware.elevatorRight.setPower(rightPID.performPID(hardware.elevatorLeft.getCurrentPosition()));
    }

    public boolean getLowerSoftLimit() {
        return hardware.elevatorLeft.getCurrentPosition() <= 0;
    }

    public boolean getUpperSoftLimit() {
        return hardware.elevatorLeft.getCurrentPosition() >= 880000;
    }

    public int getCurrentPosistion() {
        return (hardware.elevatorLeft.getCurrentPosition() + hardware.elevatorRight.getCurrentPosition())/2;
    }

    public double getCurrentHieght() {
        return (hardware.elevatorLeft.getCurrentPosition()*ticksToInch + hardware.elevatorRight.getCurrentPosition()*ticksToInch)/2;
    }
}
