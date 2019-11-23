package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
<<<<<<< HEAD
import org.firstinspires.ftc.teamcode.commands.CrosslineAuto;
=======
import org.firstinspires.ftc.robotcore.internal.android.dx.rop.cst.CstArray.List;
>>>>>>> 40cd9ab78f2bffc0c5b42c89566a18cdcedc2395
import org.firstinspires.ftc.teamcode.commands.DriveTrainTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorTeleopCommand;
import org.firstinspires.ftc.teamcode.commands.FoundationMoverCommand;
import org.firstinspires.ftc.teamcode.commands.GrabberCommand;
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
    CrosslineAuto crosslineAuto;

    GrabberCommand grabberCommand;

    List auto;

    IO io;

    public Robot(String callerName, Telemetry telem, HardwareMap hwmap, Gamepad gamepad1, Gamepad gamepad2) {
        programName = callerName;

        io = new IO(gamepad1, gamepad2);

        shuttleTeleopCommand = new ShuttleTeleopCommand(telem, hwmap, io);
        driveTrainTeleopCommand = new DriveTrainTeleopCommand(telem, hwmap, io);
        elevatorTeleopCommand = new ElevatorTeleopCommand(telem, hwmap, io);
        foundationMoverCommand = new FoundationMoverCommand(telem, hwmap, io);
        grabberCommand = new GrabberCommand(telem, hwmap, io);

        testAutoCommand = new TestAutoCommand(telem, hwmap, io);
<<<<<<< HEAD
        crosslineAuto = new CrosslineAuto(telem, hwmap, io);
        grabberCommand = new GrabberCommand(telem, hwmap, io);
=======
>>>>>>> 40cd9ab78f2bffc0c5b42c89566a18cdcedc2395
    }

    public void autoInit() {
        if (programName == "Test Auto") {
            testAutoCommand.init();
        }
        else if (programName == "Line Cross Auto"){
                crosslineAuto.init();
            }
        }
    public void autoExecute() {
        if (programName == "Test Auto"){
            testAutoCommand.execute();
        }
        else if (programName == "Line Cross Auto"){
            crosslineAuto.execute();
        }
    }

    public void autoEnd() {
        if (programName == "Test Auto") {
            testAutoCommand.stop();
        }
        else if (programName == "Line Cross Auto"){
                crosslineAuto.stop();
        }
    }

    public  void teleopInit() {
        shuttleTeleopCommand.init();
        driveTrainTeleopCommand.init();
        elevatorTeleopCommand.init();
        foundationMoverCommand.init();
        grabberCommand.init();
    }

    public void teleopExecute() {
        shuttleTeleopCommand.execute();
        driveTrainTeleopCommand.execute();
        elevatorTeleopCommand.execute();
        foundationMoverCommand.execute();
        grabberCommand.execute();
    }

    public void teleopEnd() {
        shuttleTeleopCommand.stop();
        driveTrainTeleopCommand.stop();
        elevatorTeleopCommand.stop();
        foundationMoverCommand.stop();
        grabberCommand.stop();

    }

}
