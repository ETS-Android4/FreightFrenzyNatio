package org.firstinspires.ftc.teamcode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.TeleUtils;
import org.firstinspires.ftc.teamcode.Utilities.DelayedAction;
import org.firstinspires.ftc.teamcode.Utilities.OneTapTimer;

public class IntakeSensor {

    //se asigura ca bratul si cutia sunt la pozitia de colectare iar freight ul este colectat pentru cel putin 100 ms
    private static final OneTapTimer intake = new OneTapTimer(100);
    //se asigura bratul si cutia sunt la pozitia de sus si freight ul a cazut de cel putin 100ms
    private static final OneTapTimer outtakeUp = new OneTapTimer(100);
    //se asigura bratul si cutia sunt la pozitia de below si freight ul a cazut de cel putin 100ms
    private static final OneTapTimer outtakeBelow = new OneTapTimer(100);

    //asteapta sa fie freight ul colectat ca sa poata porni low power intake
    private static final DelayedAction lowPowerIntake = new DelayedAction(200);

    //il opreste outtake ul dupa ce lasa cubul
    private static final DelayedAction stopOutake = new DelayedAction(200);

    //se ocupa de sincronizarea cutiei si a glisierelor pentru a nu cadea freight ul, se intampla dupa colectare si trb sa pui sus
    private static final DelayedAction delayedActionBoxDown = new DelayedAction(300);

    //asteapta sa se ridice glisierele pentru a trece bratul pe sub (pozitia de shared)
    private static final DelayedAction delayedActionGoUnderShared = new DelayedAction(500);

    //asteapta sa se duca cutia intr o pozitie in care bratul poate sa treaca pe sub robot. apeleaza delayedActionReturnSlidersArmBelow pentru a cobori glisierele dupa trecerea bratului
    private static final DelayedAction delayedActionArmReturnBelow = new DelayedAction(350);

    // asteapta sa treaca bratul pe sub robot pentru coborarea glisierelor
    private static final DelayedAction delayedActionReturnSlidersArmBelow = new DelayedAction(700); ///TODO:

    //se apeleaza call action a lasat cubul si aveam brartul sus pe nivelu 3 si glisierele putin ridicate. Asteapta ca bratul sa se intoarca de deasupra shipping hub
    private static final DelayedAction delayedActionReturnSlidersArmUp = new DelayedAction(500);

    //variabila updatata la fiecare frame
    public static double distance = 0;

    public static void detectTeleOp() {
        if (delayedActionReturnSlidersArmUp.runAction()) {
            Hardware.slider_right.setTargetPosition(0);
            Hardware.slider_left.setTargetPosition(0);
        }
        if (lowPowerIntake.runAction()) {
            Hardware.intake.setPower(-0.1);
            Box.power = 0;
        }
        if (stopOutake.runAction()) {
            Hardware.intake.setPower(0);
            Box.power = 0;
        }
        if (delayedActionBoxDown.runAction()) {
            //se ridica glisierele pentru a ajunge in pozitita de pus sus
            Hardware.slider_right.setTargetPosition(150);
            Hardware.slider_left.setTargetPosition(150);
            Hardware.boxAngle.setPosition(Positions.Box.Down);
        }
        if (delayedActionGoUnderShared.runAction()) {
            Arm.armPid.setTarget((int) Positions.Arm.Shared);
        }
        if (delayedActionArmReturnBelow.runAction()) {
            Arm.armPid.setTarget((int) Positions.Arm.Down);
            delayedActionReturnSlidersArmBelow.callAction();
        }
        if (delayedActionReturnSlidersArmBelow.runAction()) {
            Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Down);
            Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Down);
        }
        distance = Hardware.intakeSensor.getDistance(DistanceUnit.CM);
        if (intake.onActionRun(TeleUtils.isArmAtPosition((int) Positions.Arm.Down) && TeleUtils.isBoxAtPosition(Positions.Box.Mid) && distance < 8)) {
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            if (Arm.armGoUpAfterColect) {
                Arm.armPid.setTarget((int) Positions.Arm.Up);
                delayedActionBoxDown.callAction();
                lowPowerIntake.callAction();
            } else {
                Arm.isBelow = true;
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
            delayedActionReturnSlidersArmUp.callAction();
            stopOutake.callAction();
        }
        if (outtakeBelow.onActionRun(Arm.isBelow && distance > 9)) {
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            delayedActionArmReturnBelow.callAction();
            stopOutake.callAction();
        }
    }

}