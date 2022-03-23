package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;

@Config
public class AutoUtil {
    public static double maxVelCarusel = 44;

    public static void spinCarusel() {
        if (PoseColorNormalizer.getColorCase() == PoseColorNormalizer.Color.BLUE) {
            maxVelCarusel = -maxVelCarusel;
        }

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= 2300) {
            if (System.currentTimeMillis() - startTime <= 2000) {
                ((DcMotorEx) Hardware.carusel).setVelocity(maxVelCarusel, AngleUnit.RADIANS);
            } else {
                ((DcMotorEx) Hardware.carusel).setVelocity(maxVelCarusel * 3, AngleUnit.RADIANS);
            }
        }
        Hardware.carusel.setPower(0);
    }

    public static void spinCaruselDuckColect() {
        if (PoseColorNormalizer.getColorCase() == PoseColorNormalizer.Color.BLUE) {
            maxVelCarusel = -maxVelCarusel;
        }

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= 2000) {
            if (System.currentTimeMillis() - startTime < 1800) {
                ((DcMotorEx) Hardware.carusel).setVelocity(maxVelCarusel, AngleUnit.RADIANS);
            } else {
                ((DcMotorEx) Hardware.carusel).setVelocity(maxVelCarusel / 4 , AngleUnit.RADIANS);
            }
        }
        Hardware.carusel.setPower(0);
    }

    public static double normalizeVelocity(double velocity) {
        if (velocity > maxVelCarusel) {
            return maxVelCarusel;
        }
        return Math.max(velocity, -maxVelCarusel);
    }
}
