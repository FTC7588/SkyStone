package org.firstinspires.ftc.teamcode.commands.Teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.IO;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShuttleSubsystem;

public class ShuttleTeleopCommand {

    ShuttleSubsystem shuttleSubsystem;
    ElevatorSubsystem elevatorSubsystem;
    IO io;

    public ShuttleTeleopCommand(Telemetry telem, ShuttleSubsystem shuttleSubsystem, ElevatorSubsystem elevatorSubsystem, IO io) {
        this.shuttleSubsystem = shuttleSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;

        this.io = io;
    }

    public void init(){
        shuttleSubsystem.setPower(0);
    }

    public void execute() {
        if (elevatorSubsystem.getCurrentHieght() >= 12 || true) {
            shuttleSubsystem.setPower(io.shuttlePower());
        } else if (elevatorSubsystem.getCurrentHieght() <= 13 && false) {
            shuttleSubsystem.setHieghht(0);
            shuttleSubsystem.goToHieght();
        } else {
            shuttleSubsystem.setPower(0);
        }
    }

    public void stop() {
        shuttleSubsystem.setPower(0);
    }
}
