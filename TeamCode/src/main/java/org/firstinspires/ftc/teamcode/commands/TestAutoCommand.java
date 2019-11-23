package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;

public class TestAutoCommand {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    public TestAutoCommand(Telemetry telem, HardwareMap hwmap, IO io) {
        driveTrainSubsystem = new DriveTrainSubsystem(telem, hwmap);

        this.io = io;
    }

    public void init(){

    }

    public void execute() {
        driveTrainSubsystem.moveToPosition(30, 0.2);

        driveTrainSubsystem.arc(-45.0, 13.0, 0.2);

        driveTrainSubsystem.arc(45.0, 30.0, 0.2);
    }

    public void stop() {

    }
}
