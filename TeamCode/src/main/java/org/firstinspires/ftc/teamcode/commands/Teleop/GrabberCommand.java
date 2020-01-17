package org.firstinspires.ftc.teamcode.commands.Teleop;

        import org.firstinspires.ftc.robotcore.external.Telemetry;
        import org.firstinspires.ftc.teamcode.IO;
        import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
        import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
        import org.firstinspires.ftc.teamcode.subsystems.ShuttleSubsystem;

public class GrabberCommand {

    GrabberSubsystem grabberSubsystem;
    ShuttleSubsystem shuttleSubsystem;
    IO io;

    public GrabberCommand(Telemetry telem, GrabberSubsystem grabberSubsystem, ShuttleSubsystem shuttleSubsystem, IO io) {
        this.grabberSubsystem = grabberSubsystem;
        this.shuttleSubsystem = shuttleSubsystem;

        this.io = io;

    }
    public void init(){
        grabberSubsystem.setGrabberPosition(.85);

        grabberSubsystem.rotateGrabber(0);
    }

    public void execute() {
        if (io.grabberToggle()) {
            grabberSubsystem.toggleGrabber();
        }


        if (shuttleSubsystem.getCurrentHieght() >= 12 || true) {
            if (io.rotGrabberNegative() >= .2) {
                grabberSubsystem.rotateGrabber(io.rotGrabberNegative());
            } else if (io.rotGrabberPositive() >= .2) {
                grabberSubsystem.rotateGrabber(-io.rotGrabberNegative());
            } else {
                grabberSubsystem.rotateGrabber(0);
            }
        } else {
            grabberSubsystem.setGrabberPosition(.5);
        }
    }


    public void stop() {

    }
}
