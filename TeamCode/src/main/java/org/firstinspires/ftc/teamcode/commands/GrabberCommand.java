package org.firstinspires.ftc.teamcode.commands;

        import com.qualcomm.robotcore.hardware.HardwareMap;

        import org.firstinspires.ftc.robotcore.external.Telemetry;
        import org.firstinspires.ftc.teamcode.IO;
        import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

public class GrabberCommand {

    GrabberSubsystem grabberSubsystem;
    IO io;

    public GrabberCommand(Telemetry telem, HardwareMap hwmap, IO io) {
        grabberSubsystem = new GrabberSubsystem(telem, hwmap);

        this.io = io;

    }
    public void init(){
        grabberSubsystem.setBigGrabberPosition(0);
        grabberSubsystem.setSmallGrabberPosition(0);

    }

    public void execute() {

        if (io.bigGrabberToggle()) {
            grabberSubsystem.toggleBigGrabber();
        }
        if (io.smallGrabberToggle()) {
            grabberSubsystem.toggleSmallGrabber();
        }

       /* if (io.rotGrabberNegative() > 0) {
            grabberSubsystem.rotGrabber(-io.rotGrabberNegative());
        } else {
            grabberSubsystem.rotGrabber(io.rotGrabberPositive());
            */
        }


    public void stop() {

    }
}
