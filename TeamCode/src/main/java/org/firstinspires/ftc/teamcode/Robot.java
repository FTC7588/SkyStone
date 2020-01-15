package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.Auto.CrosslineAuto;
import org.firstinspires.ftc.robotcore.internal.android.dx.rop.cst.CstArray.List;
import org.firstinspires.ftc.teamcode.commands.Teleop.DriveTrainTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.Teleop.ElevatorTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.Teleop.FoundationMoverCommand;
import org.firstinspires.ftc.teamcode.commands.Teleop.GrabberCommand;
import org.firstinspires.ftc.teamcode.commands.Auto.LeftArcAuto;
import org.firstinspires.ftc.teamcode.commands.Auto.RightArcAuto;
import org.firstinspires.ftc.teamcode.commands.Teleop.IntakeCommand;
import org.firstinspires.ftc.teamcode.commands.Teleop.ShuttleTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.Auto.TestAutoCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrainSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FoundationMoverSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShuttleSubsystem;

public class Robot {
    String programName;

    ElevatorSubsystem elevatorSubsystem;
    DriveTrainSubsystem driveTrainSubsystem;
    FoundationMoverSubsystem foundationMoverSubsystem;
    GrabberSubsystem grabberSubsystem;
    ShuttleSubsystem shuttleSubsystem;
    IntakeSubsystem intakeSubsystem;

    ShuttleTeleopCommand shuttleTeleopCommand;
    DriveTrainTeleopCommand driveTrainTeleopCommand;
    ElevatorTeleopCommand elevatorTeleopCommand;
    FoundationMoverCommand foundationMoverCommand;
    LeftArcAuto leftArcAuto;
    RightArcAuto rightArcAuto;
    IntakeCommand intakeCommand;

    TestAutoCommand testAutoCommand;
    CrosslineAuto crosslineAuto;

    GrabberCommand grabberCommand;

    List auto;

    IO io;

    public Hardware hardware;

    public Robot(String callerName, Telemetry telem, HardwareMap hwmap, Gamepad gamepad1, Gamepad gamepad2) {
        programName = callerName;

        hardware = new Hardware();

        hardware.init(hwmap);

        io = new IO(gamepad1, gamepad2);

        elevatorSubsystem = new ElevatorSubsystem(telem, hardware);
        driveTrainSubsystem = new DriveTrainSubsystem(telem, hardware);
        shuttleSubsystem = new ShuttleSubsystem(telem, hardware);
        foundationMoverSubsystem = new FoundationMoverSubsystem(telem, hardware);
        grabberSubsystem = new GrabberSubsystem(telem, hardware);
        intakeSubsystem = new IntakeSubsystem(telem, hardware);

        shuttleTeleopCommand = new ShuttleTeleopCommand(telem, shuttleSubsystem, io);
        driveTrainTeleopCommand = new DriveTrainTeleopCommand(telem, driveTrainSubsystem, io);
        elevatorTeleopCommand = new ElevatorTeleopCommand(telem, elevatorSubsystem, io);
        foundationMoverCommand = new FoundationMoverCommand(telem, foundationMoverSubsystem, io);
        grabberCommand = new GrabberCommand(telem, grabberSubsystem, io);
        intakeCommand = new IntakeCommand(telem, intakeSubsystem, io);

        testAutoCommand = new TestAutoCommand(telem, driveTrainSubsystem, io);
        crosslineAuto = new CrosslineAuto(telem, driveTrainSubsystem, io);

        leftArcAuto = new LeftArcAuto(telem, driveTrainSubsystem, io);
        rightArcAuto = new RightArcAuto(telem, driveTrainSubsystem, io);
    }

    public void autoInit() {
        if (programName == "Test Auto") {
            testAutoCommand.init();
        } else if (programName == "Line Cross Auto"){
            crosslineAuto.init();
        } else if (programName == "Left Arc Auto") {
            leftArcAuto.init();
        } else if (programName == "Right Arc Auto") {
            rightArcAuto.init();
        }
    }

    // Does not loop
    public void autoExecute() {
        if (programName == "Test Auto") {
            testAutoCommand.execute();
        } else if (programName == "Line Cross Auto") {
            crosslineAuto.execute();
        }else if (programName == "Left Arc Auto") {
            leftArcAuto.execute();
        } else if (programName == "Right Arc Auto") {
            rightArcAuto.execute();
        }
    }
    public void autoEnd () {
        if (programName == "Test Auto") {
            testAutoCommand.stop();
        } else if (programName == "Line Cross Auto") {
            crosslineAuto.stop();
        }else if (programName == "Left Arc Auto") {
            leftArcAuto.stop();
        } else if (programName == "Right Arc Auto") {
            rightArcAuto.stop();
        }
    }

    public  void teleopInit() { }

    public void teleopStart() {
        shuttleTeleopCommand.init();
        driveTrainTeleopCommand.init();
        elevatorTeleopCommand.init();
        foundationMoverCommand.init();
        grabberCommand.init();
        intakeCommand.init();
    }

    // Loops
    public void teleopExecute() {
        shuttleTeleopCommand.execute();
        driveTrainTeleopCommand.execute();
        elevatorTeleopCommand.execute();
        foundationMoverCommand.execute();
        grabberCommand.execute();
        intakeCommand.execute();
    }

    public void teleopEnd() {
        shuttleTeleopCommand.stop();
        driveTrainTeleopCommand.stop();
        elevatorTeleopCommand.stop();
        foundationMoverCommand.stop();
        grabberCommand.stop();

    }

}
