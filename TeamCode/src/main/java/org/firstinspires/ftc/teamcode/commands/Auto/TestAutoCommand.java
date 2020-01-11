package org.firstinspires.ftc.teamcode.commands.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;

public class TestAutoCommand {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    public TestAutoCommand(Telemetry telem, DriveTrainSubsystem driveTrainSubsystem, IO io) {
        this.driveTrainSubsystem = driveTrainSubsystem;

        this.io = io;
    }

    public void init(){

    }

    public void execute() {
        driveTrainSubsystem.moveToPosition(30, 0.2);
    }

    public void stop() {

    }
}
