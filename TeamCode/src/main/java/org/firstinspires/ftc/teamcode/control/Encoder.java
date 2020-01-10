package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Encoder {
    private DcMotor encoder;

    private int ticksPerRev;

    public Encoder (DcMotor encoder, int ticksPerRev) {
        this.encoder = encoder;

        this.ticksPerRev = ticksPerRev;
    }

    public int getRawTicks() {
        return encoder.getCurrentPosition();
    }

    public int getAbsoluteTicks() {
        int encoderHalfRevs= (encoder.getCurrentPosition()/2048);
        int encoderRemainder = encoder.getCurrentPosition()-(encoderHalfRevs*2048);
        int encoderValue;

        if (encoderHalfRevs % 2 == 0) {
            encoderValue = encoderRemainder;
        } else {
            if (encoder.getCurrentPosition() < 0) {
                encoderValue = (getTicksPerRev()/2+encoderRemainder);
            } else {
                encoderValue = -(getTicksPerRev()/2-encoderRemainder);
            }
        }

        return encoderValue;
    }

    public int getTicksPerRev() {
        return ticksPerRev;
    }
}
