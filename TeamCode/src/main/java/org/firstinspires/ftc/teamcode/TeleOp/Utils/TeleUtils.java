package org.firstinspires.ftc.teamcode.TeleOp.Utils;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class TeleUtils {

    private static final int armThreshold = 100;
    private static final int slidersThreshold = 40;

    public static boolean isArmAtPosition(int position) {
        return Math.abs((int) (Hardware.potentiometer.getVoltage() * 1000) - position) <= armThreshold;
    }

    public static boolean isBoxAtPosition(double position) {
        return (int) (BoxAngle.getPositionBoxAngle() * 100) == (int) (position * 100);
    }

    public static boolean isSlidersAtPosition(int position) {
        return Math.abs(Hardware.slider_right.getCurrentPosition() - position) < slidersThreshold;
    }
}
