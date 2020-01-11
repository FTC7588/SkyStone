package org.firstinspires.ftc.teamcode.commands.Teleop;

import org.firstinspires.ftc.teamcode.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

public class ElevatorTeleopCommand {

    ElevatorSubsystem elevatorSubsystem;
    IO io;

    public ElevatorTeleopCommand(Telemetry telem, ElevatorSubsystem elevatorSubsystem, IO io) {
        this.elevatorSubsystem = elevatorSubsystem;

        this.io = io;
    }

    public void init(){
        elevatorSubsystem.setPower(0);
    }

    public void execute() {
       elevatorSubsystem.setPower(io.elevator());
    }

    public void stop() {
      elevatorSubsystem.setPower(0);
    }
}
