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
        driveTrainSubsystem.swerveDrive(0,0,0);
    }

    public void execute() {
        driveTrainSubsystem.moveToPosition(30, 0);
        driveTrainSubsystem.turnWithGyro(180, .5);
        driveTrainSubsystem.moveToPosition(60, 0);
        driveTrainSubsystem.moveToPosition(-30, 0);
        driveTrainSubsystem.turnWithGyro(0, .5);
        driveTrainSubsystem.moveToPosition(12, 90);
        driveTrainSubsystem.moveToPosition(12, 180);
    }

    public void stop() {
        driveTrainSubsystem.swerveDrive(0,0,0);
    }
}
