package org.firstinspires.ftc.teamcode.commands.Teleop;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeCommand {

    IntakeSubsystem intakeSubsystem;
    IO io;

    public IntakeCommand(Telemetry telem, IntakeSubsystem intakeSubsystem, IO io) {
        this.intakeSubsystem = intakeSubsystem;

        this.io = io;

    }
    public void init(){
        intakeSubsystem.setpower(0);
    }

    public void execute() {
        if (io.intake()) {
            intakeSubsystem.setpower(1);
        } else if (io.outTake()) {
            intakeSubsystem.setpower(-1);
        } else {
            intakeSubsystem.setpower(.1);
        }
    }


    public void stop() {

    }
}
