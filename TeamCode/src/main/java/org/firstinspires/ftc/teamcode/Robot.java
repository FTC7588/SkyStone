package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.DriveTrainTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.FoundationMoverCommand;
import org.firstinspires.ftc.teamcode.commands.ShuttleTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.TestAutoCommand;
import org.firstinspires.ftc.teamcode.subsystems.FoundationMoverSubsystem;

public class Robot {
    String programName;

    ShuttleTeleopCommand shuttleTeleopCommand;
    DriveTrainTeleopCommand driveTrainTeleopCommand;
    ElevatorTeleopCommand elevatorTeleopCommand;
    FoundationMoverCommand foundationMoverCommand;
    TestAutoCommand testAutoCommand;

    IO io;

    public Robot(String callerName, Telemetry telem, HardwareMap hwmap, Gamepad gamepad1, Gamepad gamepad2) {
        programName = callerName;

        io = new IO(gamepad1, gamepad2);

        shuttleTeleopCommand = new ShuttleTeleopCommand(telem, hwmap, io);
        driveTrainTeleopCommand = new DriveTrainTeleopCommand(telem, hwmap, io);
        elevatorTeleopCommand = new ElevatorTeleopCommand(telem, hwmap, io);
        foundationMoverCommand = new FoundationMoverCommand(telem, hwmap, io);
        testAutoCommand = new TestAutoCommand(telem, hwmap, io);
    }

    public void autoInit() {

    }

    public void autoExecute() {
        if (programName == "Test Auto"){
            testAutoCommand.execute();
        }
    }

    public void autoEnd() {

    }

    public  void teleopInit() {
        shuttleTeleopCommand.init();
        driveTrainTeleopCommand.init();
        elevatorTeleopCommand.init();
        foundationMoverCommand.init();
    }

    public void teleopExecute() {
        shuttleTeleopCommand.execute();
        driveTrainTeleopCommand.execute();
        elevatorTeleopCommand.execute();
        foundationMoverCommand.execute();
    }

    public void teleopEnd() {
        shuttleTeleopCommand.stop();
        driveTrainTeleopCommand.stop();
        elevatorTeleopCommand.stop();
        foundationMoverCommand.stop();
    }
}
