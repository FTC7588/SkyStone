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
    boolean SlowMode = false;
    boolean buttonIsReleased = true;

    public GrabberCommand(Telemetry telem, GrabberSubsystem grabberSubsystem, ShuttleSubsystem shuttleSubsystem, IO io) {
        this.grabberSubsystem = grabberSubsystem;
        this.shuttleSubsystem = shuttleSubsystem;

        this.io = io;

    }

    public void init() {
        grabberSubsystem.setGrabberPosition(.65);

        //grabberSubsystem.rotateGrabber(0);
    }

    public void execute() {
        if (io.grabberToggle()) {
            if (buttonIsReleased) {
                buttonIsReleased = false;
                if (SlowMode == false) {
                    SlowMode = true;
                    grabberSubsystem.toggleGrabber();
                } else if (SlowMode == true) {
                    SlowMode = false;
                    grabberSubsystem.toggleGrabber();
                }
            }
        } else {
            buttonIsReleased = true;
        }

        if(io.rotGrabberNegative()>=.001){
            grabberSubsystem.rotateGrabber(io.rotGrabberNegative());
        } else if(io.rotGrabberPositive()>=.001){
            grabberSubsystem.rotateGrabber(-io.rotGrabberPositive());
        } else{
            grabberSubsystem.rotateGrabber(0);
        }
    }


    public void stop() {

    }
}
