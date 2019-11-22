package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.DriveTrainTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.ShuttleTeleopCommand;

public class Robot {
    String programName;

    ShuttleTeleopCommand shuttleTeleopCommand;
    DriveTrainTeleopCommand driveTrainTeleopCommand;
    ElevatorTeleopCommand elevatorTeleopCommand;

    IO io;

    public Robot(String callerName, Telemetry telem, HardwareMap hwmap, Gamepad gamepad1, Gamepad gamepad2) {
        programName = callerName;

        io = new IO(gamepad1, gamepad2);

        shuttleTeleopCommand = new ShuttleTeleopCommand(telem, hwmap, io);
        driveTrainTeleopCommand = new DriveTrainTeleopCommand(telem, hwmap, io);
        elevatorTeleopCommand = new ElevatorTeleopCommand(telem, hwmap, io);
    }

    public void autoInit() {

    }

    public void autoExecute() {

    }

    public void autoEnd() {

    }

    public  void teleopInit() {
        shuttleTeleopCommand.init();
        driveTrainTeleopCommand.init();
        elevatorTeleopCommand.init();
    }

    public void teleopExecute() {
        shuttleTeleopCommand.execute();
        driveTrainTeleopCommand.execute();
        elevatorTeleopCommand.execute();
    }

    public void teleopEnd() {
        shuttleTeleopCommand.stop();
        driveTrainTeleopCommand.stop();
        elevatorTeleopCommand.stop();
    }
}
