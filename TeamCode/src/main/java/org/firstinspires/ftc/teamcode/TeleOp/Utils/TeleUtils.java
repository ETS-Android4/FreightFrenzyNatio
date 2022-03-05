package org.firstinspires.ftc.teamcode.TeleOp.Utils;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class TeleUtils {

    private static final int armThreshold = 100;

    public static boolean isArmAtPosition(int position) {
        return Math.abs((int) (Hardware.potentiometer.getVoltage() * 1000) - position) <= armThreshold;
    }

    public static boolean isBoxAtPosition(double position) {
        return (int) (Hardware.boxAngle.getPosition() * 1000) == (int) (position * 1000);
    }
}
