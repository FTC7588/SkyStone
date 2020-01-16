package org.firstinspires.ftc.teamcode.commands.Teleop;

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
        grabberSubsystem.setGrabberPosition(1);

    }

    public void execute() {
        if (io.grabberToggle()) {
            grabberSubsystem.toggleGrabber();
        }

        if(io.rotGrabberNegative() > .2) {
            grabberSubsystem.rotateGrabber(io.rotGrabberNegative());
        } else if(io.rotGrabberPositive() > .2) {
            grabberSubsystem.rotateGrabber(io.rotGrabberPositive());
        }
    }


    public void stop() {

    }
}
