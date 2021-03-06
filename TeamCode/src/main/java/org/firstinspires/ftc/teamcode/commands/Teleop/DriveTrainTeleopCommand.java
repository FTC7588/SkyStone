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
        driveTrainSubsystem.meccanumDrive(0,0,0);

        driveTrainSubsystem.initGyro();
    }

    public void execute() {
        //driveTrainSubsystem.meccanumDrive(io.meccTurn(), io.meccStrafe(), io.meccPower());
        driveTrainSubsystem.meccanumDrive(io.meccStrafe(), io.meccPower(), io.meccTurn());
    }

    public void stop() {
        driveTrainSubsystem.meccanumDrive(0,0,0);
    }
}
