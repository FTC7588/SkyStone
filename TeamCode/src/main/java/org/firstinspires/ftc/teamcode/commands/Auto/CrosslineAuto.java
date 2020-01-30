package org.firstinspires.ftc.teamcode.commands.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;

public class CrosslineAuto {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    public CrosslineAuto(Telemetry telem, DriveTrainSubsystem driveTrainSubsystem, IO io) {
        this.driveTrainSubsystem = driveTrainSubsystem;

        this.io = io;
    }

    public void init(){

    }

    public void execute() {
        driveTrainSubsystem.moveToPositionPID(12, 0.5);
    }

    public void stop() {

    }
}
