package org.firstinspires.ftc.teamcode.commands.Auto;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;

public class RightArcAuto {

    DriveTrainSubsystem driveTrainSubsystem;
    IO io;

    public RightArcAuto(Telemetry telem, DriveTrainSubsystem driveTrainSubsystem, IO io) {
        this.driveTrainSubsystem = driveTrainSubsystem;

        this.io = io;
    }

    public void init(){

    }

    public void execute() {
        //driveTrainSubsystem.moveToPosition(26.4, 0.2);

        //driveTrainSubsystem.turnWithGyro(90, -0.2);

        //driveTrainSubsystem.moveToPosition(34, 0.2);
    }

    public void stop() {

            }
}
