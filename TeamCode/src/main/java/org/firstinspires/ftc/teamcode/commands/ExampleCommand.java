package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ExampleSubsystem;

public class ExampleCommand {

    ExampleSubsystem exampleSubsystem;

    public ExampleCommand(Telemetry telem) {
        exampleSubsystem = new ExampleSubsystem(telem);
    }

    public void init(){
        exampleSubsystem.sendTelemetry("ExampleCommand Init-ed!", "A");
    }

    public void execute() {
        exampleSubsystem.sendTelemetry("ExampleCommand Executed!", "a");
    }

    public void stop() {
        exampleSubsystem.sendTelemetry("ExampleCommand Stopped!", "v");
    }
}
