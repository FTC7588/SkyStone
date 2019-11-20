package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.firstinspires.ftc.teamcode.TeleopController;
import org.firstinspires.ftc.teamcode.menuItems.TeleopMenuExample;


public class ExampleSubsystem {

    public ExampleSubsystem() {
    }

    public void sendTelemetry(String telemetryData, String telemetryData2) {
        TeleopMenuExample.telem.addData("ab","4");
        //telemetry.addData(telemetryData, telemetryData2);
        TeleopMenuExample.telem.update();
    }
}
