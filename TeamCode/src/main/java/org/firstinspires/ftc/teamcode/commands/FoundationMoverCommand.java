package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FoundationMoverSubsystem;

public class FoundationMoverCommand {

    FoundationMoverSubsystem foundationMoverSubsystem;
    IO io;

    public FoundationMoverCommand(Telemetry telem, HardwareMap hwmap, IO io) {
        foundationMoverSubsystem = new FoundationMoverSubsystem(telem, hwmap);

        this.io = io;
    }

    public void init(){
        foundationMoverSubsystem.setPosition(.5);
    }

    public void execute() {

       if (io.foundationGrabber()){
           foundationMoverSubsystem.setPosition(0);

       }
    }

    public void stop() {
      foundationMoverSubsystem.setPosition(.5);
    }
}
