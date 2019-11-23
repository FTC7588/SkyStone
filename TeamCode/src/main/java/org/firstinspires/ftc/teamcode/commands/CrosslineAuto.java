package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;

public class CrosslineAuto {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    public CrosslineAuto(Telemetry telem, HardwareMap hwmap, IO io) {
        driveTrainSubsystem = new DriveTrainSubsystem(telem, hwmap);

        this.io = io;
    }

    public void init(){

    }

    public void execute() {
        driveTrainSubsystem.moveToPosition(12, 0.2);
    }

    public void stop() {

    }
}
