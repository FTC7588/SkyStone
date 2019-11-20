package org.firstinspires.ftc.teamcode.subsystems;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;

public class exampleSubsystem {

    TelemetryImpl telemetry;

    public exampleSubsystem() {
        telemetry = new TelemetryImpl(null);
    }

    public void sendTelemetry(String telemetryData) {
        telemetry.addData(telemetryData, "");
        telemetry.update();
    }
}
