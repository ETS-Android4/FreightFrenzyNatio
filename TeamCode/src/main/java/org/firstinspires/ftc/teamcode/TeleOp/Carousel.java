package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.Utilities.OneTap;

public class Carousel {
    public static OneTap rBumper = new OneTap();
    public static long startTime = 0;
    private static final double maxVelCarusel = 45;
    public static void run() {
        if (rBumper.onPress(Gamepads.carusel())){
            startTime = System.currentTimeMillis();
        }
        if (Gamepads.carusel()){
            if (System.currentTimeMillis() - startTime < 950) {
                ((DcMotorEx) Hardware.carusel).setVelocity(maxVelCarusel, AngleUnit.RADIANS);
            } else {
                ((DcMotorEx) Hardware.carusel).setVelocity(maxVelCarusel * 3, AngleUnit.RADIANS);
            }
        }
        else{
            Hardware.carusel.setPower(0);
        }
    }
}