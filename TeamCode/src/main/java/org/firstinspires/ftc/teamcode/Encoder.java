package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Encoder {
    private DcMotor encoder;

    public Encoder (DcMotor encoder) {
        this.encoder = encoder;
    }

    public int getRawTicks() {
        return encoder.getCurrentPosition();
    }

    public  double getAbsoluteTicks() {
        int encoderHalfRevs= (encoder.getCurrentPosition()/2048);
        int encoderRemainder = encoder.getCurrentPosition()-(encoderHalfRevs*2048);
        double encoderValue;

        if (encoderHalfRevs % 2 == 0) {
            encoderValue = encoderRemainder;
        } else {
            if (encoder.getCurrentPosition() < 0) {
                encoderValue = (2048+encoderRemainder);
            } else {
                encoderValue = -(2048-encoderRemainder);
            }
        }

        return encoderValue;
    }
}
