package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.exampleSubsystem;

public class exampleCommand {

    exampleSubsystem exampleSubsystem;

    public exampleCommand() {

    }

    public void init(){
        exampleSubsystem.sendTelemetry("exampleCommand Inited!");
    }

    public void execute() {
        exampleSubsystem.sendTelemetry("exampleCommand Executed!");
    }

    public void stop() {
        exampleSubsystem.sendTelemetry("exampleCommand Stopped!");
    }
}
