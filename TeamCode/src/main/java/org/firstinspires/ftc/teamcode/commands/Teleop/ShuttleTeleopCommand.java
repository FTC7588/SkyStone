package org.firstinspires.ftc.teamcode.commands.Teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.ShuttleSubsystem;

public class ShuttleTeleopCommand {

    ShuttleSubsystem shuttleSubsystem;
    IO io;

    public ShuttleTeleopCommand(Telemetry telem, ShuttleSubsystem shuttleSubsystem, IO io) {
        this.shuttleSubsystem = shuttleSubsystem;

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
