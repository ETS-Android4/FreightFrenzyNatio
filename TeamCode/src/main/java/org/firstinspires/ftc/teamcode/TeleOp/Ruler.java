package org.firstinspires.ftc.teamcode.TeleOp;


import static org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions.rulerAngleInit;
import static org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions.rulerBaseInit;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.PoseStorageTeleOp;
import org.firstinspires.ftc.teamcode.Utilities.OneTap;

public class Ruler {
    private static final double speed = 0.00005;
    private static double power = 0;
    private static double powerX = 0;
    private static double powerY = 0;
    private static final double exponent = 4;
    private static ElapsedTime timer = new ElapsedTime();
    private static ElapsedTime timerX = new ElapsedTime();
    private static ElapsedTime timerY = new ElapsedTime();
    private static boolean firstTime = true;
    private static boolean firstTimeX = true;
    private static boolean firstTimeY = true;
    private static double rulerSlowIncrement = 0.008;
    private static OneTap rulerBaseUpTap = new OneTap();
    private static OneTap rulerBaseDownTap = new OneTap();
    private static OneTap rulerAngleLeftTap = new OneTap();
    private static OneTap rulerAngleRightTap = new OneTap();
    private static double DeliverTMPosition = 0.32;

    public static void controlRulerDpad(LinearOpMode opMode) {
        power = Math.pow(timer.seconds() + 3.5, exponent);

        if (Gamepads.reInitRuler()) {
            Hardware.rulerAngle.setPosition(rulerAngleInit);
            Hardware.rulerBase.setPosition(rulerBaseInit);
        }

//        if (Gamepads.rulerAngleColectPose()){
//            Hardware.rulerAngle.setPosition(0.93);
//        }

        //TODO onetap REFACTOOOOOR
        if (Gamepads.deliverTM()) {

            if (Hardware.rulerAngle.getPosition() > PoseStorageTeleOp.rulerAnglePlace)
                Hardware.rulerAngle.setPosition(Hardware.rulerAngle.getPosition() - 0.06);
            else {
                Hardware.rulerAngle.setPosition(PoseStorageTeleOp.rulerAnglePlace);
            }

            if (Hardware.rulerBase.getPosition() < PoseStorageTeleOp.rulerBasePlace - 0.011 && Hardware.rulerAngle.getPosition() < 0.15) {
                Hardware.rulerBase.setPosition(Hardware.rulerBase.getPosition() + 0.02);
            } else if (Hardware.rulerBase.getPosition() > PoseStorageTeleOp.rulerBasePlace + 0.011 && Hardware.rulerAngle.getPosition() < 0.15) {
                Hardware.rulerBase.setPosition(Hardware.rulerBase.getPosition() - 0.02);
            } else if (Hardware.rulerAngle.getPosition() < 0.2) {
                Hardware.rulerBase.setPosition(PoseStorageTeleOp.rulerBasePlace);
            }

        }

        if (Gamepads.rulerAngleColectPose()) {
            Hardware.rulerAngle.setPosition(PoseStorageTeleOp.rulerAngle);
            Hardware.rulerBase.setPosition(PoseStorageTeleOp.rulerBase);
        }

        if (Gamepads.rulerSlow()) {
            if (rulerBaseUpTap.onPress(Gamepads.rulerBaseLeft())) {
                Hardware.rulerBase.setPosition(Hardware.rulerBase.getPosition() + rulerSlowIncrement);
            } else if (rulerBaseDownTap.onPress(Gamepads.rulerBaseRight())) {
                Hardware.rulerBase.setPosition(Hardware.rulerBase.getPosition() - rulerSlowIncrement);
            }

            if (rulerAngleRightTap.onPress(Gamepads.rulerAngleUp())) {
                Hardware.rulerAngle.setPosition(Hardware.rulerAngle.getPosition() - rulerSlowIncrement);
            } else if (rulerAngleLeftTap.onPress(Gamepads.rulerAngleRight())) {
                Hardware.rulerAngle.setPosition(Hardware.rulerAngle.getPosition() + rulerSlowIncrement);
            }
        } else {
            if (Gamepads.rulerBaseLeft()) {
                if (firstTime) {
                    timer.startTime();
                    firstTime = false;
                }
                Hardware.rulerBase.setPosition(Hardware.rulerBase.getPosition() + power * speed);
            } else if (Gamepads.rulerBaseRight()) {
                if (firstTime) {
                    timer.startTime();
                    firstTime = false;
                }
                Hardware.rulerBase.setPosition(Hardware.rulerBase.getPosition() - power * speed);
            } else if (Gamepads.rulerAngleUp()) {
                if (firstTime) {
                    timer.startTime();
                    firstTime = false;
                }
                Hardware.rulerAngle.setPosition(Hardware.rulerAngle.getPosition() - power * speed / 2);
            } else if (Gamepads.rulerAngleRight()) {
                if (firstTime) {
                    timer.startTime();
                    firstTime = false;
                }
                Hardware.rulerAngle.setPosition(Hardware.rulerAngle.getPosition() + power * speed / 2);
            } else {
                firstTime = true;
                timer.reset();
                power = 0;
            }
        }
        if (Gamepads.rulerExtend() > 0.01) {
            double power = Math.pow(Gamepads.rulerExtend(), 5);
            if (power < 0.15) {
                power = 0.15;
            }
            Hardware.rulerSpin.setPower(power);
        } else if (Gamepads.rulerRetract() > 0.01) {
            double power = Math.pow(Gamepads.rulerRetract(), 5);
            if (power < 0.15) {
                power = 0.15;
            }
            Hardware.rulerSpin.setPower(-power);
        } else {
            Hardware.rulerSpin.setPower(0);
        }
    }

    public static void controlRulerJoystick() {
        powerX = Math.pow(timerX.seconds() + 3.5, exponent);
        powerY = Math.pow(timerY.seconds() + 3.5, exponent);

        if (Math.abs(Gamepads.joystickXRulerControl()) > 0.05) {
            if (firstTimeX) {
                timerX.startTime();
                firstTimeX = false;
            }
            Hardware.rulerBase.setPosition(Hardware.rulerBase.getPosition() + powerX * speed * Math.pow(Gamepads.joystickXRulerControl(), 1));
        } else {
            firstTimeX = true;
            timerX.reset();
            powerX = 0;
        }

        if (Math.abs(Gamepads.joystickYRulerControl()) > 0.05) {
            if (firstTimeY) {
                timerY.startTime();
                firstTimeY = false;
            }
            Hardware.rulerAngle.setPosition(Hardware.rulerAngle.getPosition() + powerY * speed * Math.pow(Gamepads.joystickYRulerControl(), 1));
        } else {
            firstTimeY = true;
            timerY.reset();
            power = 0;
        }

        if (Gamepads.rulerExtend() > 0.01) {
            Hardware.rulerSpin.setPower(Gamepads.rulerExtend());
        } else if (Gamepads.rulerRetract() > 0.01) {
            Hardware.rulerSpin.setPower(-Gamepads.rulerRetract());
        } else {
            Hardware.rulerSpin.setPower(0);
        }
    }
}
