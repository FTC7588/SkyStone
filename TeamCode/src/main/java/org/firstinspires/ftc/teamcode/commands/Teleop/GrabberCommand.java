package org.firstinspires.ftc.teamcode.commands.Teleop;

        import com.qualcomm.robotcore.hardware.HardwareMap;

        import org.firstinspires.ftc.robotcore.external.Telemetry;
        import org.firstinspires.ftc.teamcode.IO;
        import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

public class GrabberCommand {

    GrabberSubsystem grabberSubsystem;
    IO io;

    public GrabberCommand(Telemetry telem, GrabberSubsystem grabberSubsystem, IO io) {
        this.grabberSubsystem = grabberSubsystem;

        this.io = io;

    }
    public void init(){
        grabberSubsystem.setGrabberPosition(0);

    }

    public void execute() {
        if (io.bigGrabberToggle()) {
            grabberSubsystem.toggleGrabber();
        }
        if (io.smallGrabberToggle()) {
            grabberSubsystem.toggleGrabber();
        }
    }


    public void stop() {

    }
}
