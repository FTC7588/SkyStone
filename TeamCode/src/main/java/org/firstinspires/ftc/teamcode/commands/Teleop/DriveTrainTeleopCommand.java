package org.firstinspires.ftc.teamcode.commands.Teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShuttleSubsystem;

public class DriveTrainTeleopCommand {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;
    Telemetry telemetry;

    public DriveTrainTeleopCommand(Telemetry telem, DriveTrainSubsystem driveTrainSubsystem, IO io) {
        this.driveTrainSubsystem = driveTrainSubsystem;

        this.telemetry = telem;

        this.io = io;
    }

    public void init(){
        driveTrainSubsystem.swerveDrive(0,0,0);
    }

    public void execute() {
        driveTrainSubsystem.swerveDrive(io.swervepower(), io.swerveturn(), io.swerveStrafe());
    }

    public void stop() {
        driveTrainSubsystem.swerveDrive(0,0,0);
    }
}
