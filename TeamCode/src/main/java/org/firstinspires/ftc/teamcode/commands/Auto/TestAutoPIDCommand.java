package org.firstinspires.ftc.teamcode.commands.Auto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;

public class TestAutoPIDCommand {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    Telemetry telemetry;

    public TestAutoPIDCommand(Telemetry telem, DriveTrainSubsystem driveTrainSubsystem, IO io) {
        this.driveTrainSubsystem = driveTrainSubsystem;

        this.io = io;

        this.telemetry = telem;
    }

    public void init(){
        driveTrainSubsystem.initGyro();

        driveTrainSubsystem.meccanumDrive(0,0,0);
    }

    public void execute() {
        telemetry.addData("test", "");
        telemetry.update();

        driveTrainSubsystem.moveToPositionPID(10, .5);

        driveTrainSubsystem.turnWithGyroPID(90, -.5);
    }

    public void stop() {
        driveTrainSubsystem.meccanumDrive(0,0,0);
    }
}
