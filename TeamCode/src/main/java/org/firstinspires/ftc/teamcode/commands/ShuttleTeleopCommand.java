package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.ShuttleSubsystem;

public class ShuttleTeleopCommand {

    ShuttleSubsystem shuttleSubsystem;
    IO io;

    public ShuttleTeleopCommand(Telemetry telem, HardwareMap hwmap, IO io) {
        shuttleSubsystem = new ShuttleSubsystem(telem, hwmap);

        this.io = io;
    }

    public void init(){
        shuttleSubsystem.setPower(0);
    }

    public void execute() {
        shuttleSubsystem.setPower(io.shuttlePower());
    }

    public void stop() {
        shuttleSubsystem.setPower(0);
    }
}
