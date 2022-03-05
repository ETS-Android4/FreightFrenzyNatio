package org.firstinspires.ftc.teamcode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.TeleUtils;
import org.firstinspires.ftc.teamcode.Utilities.DelayedAction;
import org.firstinspires.ftc.teamcode.Utilities.OneTapTimer;

public class IntakeSensor {

    private static final OneTapTimer intakeUp = new OneTapTimer(100);
    private static final OneTapTimer outtakeUp = new OneTapTimer(100);
    private static final OneTapTimer intakeBelow = new OneTapTimer(100);
    private static final OneTapTimer outtakeBelow = new OneTapTimer(100);

    private static final DelayedAction lowPowerIntake = new DelayedAction(200);
    private static final DelayedAction stopIntake = new DelayedAction(200);
    private static final double maxDistance = 5;

    private static final DelayedAction delayedActionBoxDown = new DelayedAction(300);
    private static final DelayedAction delayedActionGoUnderShared = new DelayedAction(500);
    private static final DelayedAction delayedActionArmReturn = new DelayedAction(350);
    private static final DelayedAction delayedActionReturnSliders = new DelayedAction(600);
    private static final DelayedAction delayedActionReturnSlidersQuick = new DelayedAction(500);

    public static double distance = 0;

    public static void detectTeleOp() {
        if (delayedActionReturnSlidersQuick.runAction()) {
            Hardware.slider_right.setTargetPosition(0);
            Hardware.slider_left.setTargetPosition(0);
        }
        if (lowPowerIntake.runAction()) {
            Hardware.intake.setPower(-0.1);
            Box.power = 0;
        }
        if (stopIntake.runAction()) {
            Hardware.intake.setPower(0);
            Box.power = 0;
        }
        if (delayedActionBoxDown.runAction()) {
            Hardware.slider_right.setTargetPosition(150);
            Hardware.slider_left.setTargetPosition(150);
            Hardware.boxAngle.setPosition(Positions.Box.Down);
        }
        if (delayedActionGoUnderShared.runAction()) {
            Arm.armPid.setTarget((int) Positions.Arm.Shared);
        }
        if (delayedActionArmReturn.runAction()) {
            Arm.armPid.setTarget((int) Positions.Arm.Down);
            delayedActionReturnSliders.callAction();
        }
        if (delayedActionReturnSliders.runAction()) {
            Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Down);
            Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Down);
        }
        distance = Hardware.intakeSensor.getDistance(DistanceUnit.CM);
        if (intakeUp.onActionRun(TeleUtils.isArmAtPosition((int) Positions.Arm.Down) && TeleUtils.isBoxAtPosition(Positions.Box.Mid) && distance < 8)) {
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            if (Arm.armGoUpAfterColect) {
                Arm.armPid.setTarget((int) Positions.Arm.Up);
                delayedActionBoxDown.callAction();
                lowPowerIntake.callAction();
            } else {
                Arm.isBelow=true;
                Hardware.boxAngle.setPosition(Positions.Box.Up);
                Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Up + 15);
                Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Up);
                Hardware.intake.setPower(-1);
                Box.power = 1;
                delayedActionGoUnderShared.callAction();
            }
        }
        if (outtakeUp.onActionRun(TeleUtils.isArmAtPosition((int) Positions.Arm.Up) && TeleUtils.isBoxAtPosition(Positions.Box.Up) && distance > 9)) {
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            Arm.armPid.setTarget((int) Positions.Arm.Down + 60);
            delayedActionReturnSlidersQuick.callAction();
            stopIntake.callAction();
        }
        if (outtakeBelow.onActionRun(Arm.isBelow && distance > 9)) {
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            delayedActionArmReturn.callAction();
            stopIntake.callAction();
        }
    }

    public static boolean detectAuto() {

        return false;
    }
}