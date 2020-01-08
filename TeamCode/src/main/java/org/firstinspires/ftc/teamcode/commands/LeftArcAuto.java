package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;

public class LeftArcAuto {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    public LeftArcAuto(Telemetry telem, HardwareMap hwmap, IO io) {
        driveTrainSubsystem = new DriveTrainSubsystem(telem, hwmap);

        this.io = io;
    }

    public void init(){

    }

    public void execute() {
        driveTrainSubsystem.moveToPosition(25.4, 0.2);
        //
        driveTrainSubsystem.turnWithGyro(90, 0.2);
        //
        driveTrainSubsystem.moveToPosition(32.6, 0.2);
    }

    public void stop() {

    }
}
