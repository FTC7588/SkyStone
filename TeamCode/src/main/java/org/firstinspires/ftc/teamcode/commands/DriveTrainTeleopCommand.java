package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShuttleSubsystem;

public class DriveTrainTeleopCommand {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    public DriveTrainTeleopCommand(Telemetry telem, HardwareMap hwmap, IO io) {
        driveTrainSubsystem = new DriveTrainSubsystem(telem, hwmap);

        this.io = io;
    }

    public void init(){
        driveTrainSubsystem.arcadeDrive(0,0);
    }

    public void execute() {
        driveTrainSubsystem.arcadeDrive(io.drive(), -io.turn());
    }

    public void stop() {
        driveTrainSubsystem.arcadeDrive(0,0);
    }
}
