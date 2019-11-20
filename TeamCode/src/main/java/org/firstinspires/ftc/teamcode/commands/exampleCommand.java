package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.ExampleSubsystem;

public class exampleCommand {

    ExampleSubsystem exampleSubsystem;

    public exampleCommand() {
        exampleSubsystem = new ExampleSubsystem();
    }

    public void init(){
        exampleSubsystem.sendTelemetry("exampleCommand Init-ed!", "A");
    }

    public void execute() {
        exampleSubsystem.sendTelemetry("exampleCommand Executed!", "a");
    }

    public void stop() {
        exampleSubsystem.sendTelemetry("exampleCommand Stopped!", "v");
    }
}
