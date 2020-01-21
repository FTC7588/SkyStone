package org.firstinspires.ftc.teamcode.commands.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;

public class TestAutoCommand {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    Telemetry telemetry;

    public TestAutoCommand(Telemetry telem, DriveTrainSubsystem driveTrainSubsystem, IO io) {
        this.driveTrainSubsystem = driveTrainSubsystem;

        this.io = io;

        this.telemetry = telem;
    }

    public void init(){
        driveTrainSubsystem.initGyro();

        driveTrainSubsystem.meccanumDrive(0,0,0);
    }

    public void execute() {
        telemetry.addData("no", "");
        telemetry.update();

        driveTrainSubsystem.moveToPosition(30, 0);
        driveTrainSubsystem.turnWithGyro(180, .5);
        driveTrainSubsystem.moveToPosition(60, 0);
        driveTrainSubsystem.moveToPosition(-30, 0);
        driveTrainSubsystem.turnWithGyro(0, .5);
        driveTrainSubsystem.moveToPosition(12, 90);
        driveTrainSubsystem.moveToPosition(12, 180);
    }

    public void stop() {
        driveTrainSubsystem.meccanumDrive(0,0,0);
    }
}
