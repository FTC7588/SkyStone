package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.exampleCommand;

public class Robot {
    String programName;

    exampleCommand exampleCommand;

    public Robot(String callerName, Telemetry telem) {
        programName = callerName;

        exampleCommand = new exampleCommand(telem);
    }

    public void autoInit() {

    }

    public void autoExecute() {

    }

    public void autoEnd() {

    }

    public  void teleopInit() {
        exampleCommand.init();
    }

    public void teleopExecute() {
        exampleCommand.execute();
    }

    public void teleopEnd() {
        exampleCommand.stop();
    }
}
