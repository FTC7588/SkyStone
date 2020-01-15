package org.firstinspires.ftc.teamcode.commands.Teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FoundationMoverSubsystem;

public class FoundationMoverCommand {

    FoundationMoverSubsystem foundationMoverSubsystem;
    IO io;

    public FoundationMoverCommand(Telemetry telem, FoundationMoverSubsystem foundationMoverSubsystem, IO io) {
        this.foundationMoverSubsystem = foundationMoverSubsystem;

        this.io = io;
    }

    public void init(){
        foundationMoverSubsystem.setPosition(0);
    }

    public void execute() {

       if (io.foundationGrabber()){
           foundationMoverSubsystem.toggleFoundationGrabber();

       }
    }

    public void stop() {
    }
}
