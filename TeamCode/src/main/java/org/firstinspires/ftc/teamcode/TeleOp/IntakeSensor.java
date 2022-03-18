package org.firstinspires.ftc.teamcode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.ConditionAction;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.TeleUtils;
import org.firstinspires.ftc.teamcode.Utilities.DelayedAction;
import org.firstinspires.ftc.teamcode.Utilities.OneTapTimer;

public class IntakeSensor {

    /**
     * Logica senzorului
     */
    //se asigura ca bratul si cutia sunt la pozitia de colectare iar freight ul este colectat pentru cel putin 100 ms
    private static final OneTapTimer intake = new OneTapTimer(150);

    //se asigura bratul si cutia sunt la pozitia de sus si freight ul a cazut de cel putin 100ms
    private static final OneTapTimer outtakeUp = new OneTapTimer(100);

    //se asigura bratul si cutia sunt la pozitia de below si freight ul a cazut de cel putin 100ms
    private static final OneTapTimer outtakeBelow = new OneTapTimer(100);

    //il opreste outtake ul dupa ce lasa cubul
    private static final DelayedAction stopOutake = new DelayedAction(200);

    //variabila updatata la fiecare frame
    public static double distance = 0;


    /**
     * UP
     */
    //asteapta sa fie freight ul colectat ca sa poata porni low power intake
    private static final DelayedAction lowPowerIntake = new DelayedAction(200);

    //se ocupa de sincronizarea cutiei si a glisierelor pentru a nu cadea freight ul, se intampla dupa colectare si trb sa pui sus
    private static final DelayedAction delayedActionBoxDown = new DelayedAction(300);

    //se apeleaza call action a lasat cubul si aveam brartul sus pe nivelu 3 si glisierele putin ridicate. Asteapta ca bratul sa se intoarca de deasupra shipping hub
    private static final ConditionAction conditionActionReturnSlidersArmUp = new ConditionAction();


    /**
     * SHARED
     */
    //asteapta sa se ridice glisierele si cutia pentru a trece bratul pe sub (pozitia de shared)
    private static final ConditionAction conditionActionGoUnderShared = new ConditionAction();

    //asteapta sa se duca cutia intr o pozitie in care bratul poate sa treaca pe sub robot. apeleaza delayedActionReturnSlidersArmBelow pentru a cobori glisierele dupa trecerea bratului
    private static final ConditionAction delayedActionArmReturnBelow = new ConditionAction();

    // asteapta sa treaca bratul pe sub robot pentru coborarea glisierelor
    private static final ConditionAction conditionActionReturnSlidersArmBelow = new ConditionAction();


    public static void detectTeleOp() {
        if (conditionActionReturnSlidersArmUp.runAction(Hardware.potentiometer.getVoltage() * 1000 > Positions.Arm.Up + 300)) {
            Hardware.slider_right.setTargetPosition(0);
            Hardware.slider_left.setTargetPosition(0);
        }
        if (lowPowerIntake.runAction()) {
            Hardware.intake.setPower(-0.2);
            Box.power = 0;
        }
        if (stopOutake.runAction()) {
            Hardware.intake.setPower(0);
            Box.power = 0;
        }
        if (delayedActionBoxDown.runAction()) {
            //se ridica glisierele pentru a ajunge in pozitita de pus sus
            Hardware.slider_right.setTargetPosition(300);
            Hardware.slider_left.setTargetPosition(300);
            Hardware.boxAngle.setPosition(Positions.Box.Down);
        }
        if (conditionActionGoUnderShared.runAction(TeleUtils.isSlidersAtPosition((int) Positions.Sliders.Up)
                && Hardware.boxPotentiometer.getVoltage() < Positions.PotentiometerBox.Up)) { //TODO: asteapta si box
            Arm.armPid.setTarget((int) Positions.Arm.Shared);
        }
        if (delayedActionArmReturnBelow.runAction(Hardware.boxPotentiometer.getVoltage() < Positions.PotentiometerBox.Up)) {
            Arm.armPid.setTarget((int) Positions.Arm.Down);
            //delayedActionReturnSlidersArmBelow.callAction();
            conditionActionReturnSlidersArmBelow.callAction();
        }
        if (conditionActionReturnSlidersArmBelow.runAction(TeleUtils.isArmAtPosition((int) Positions.Arm.Down))) {
            Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Down);
            Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Down);
            Hardware.boxAngle.setPosition(Positions.Box.Mid);
            Hardware.intake.setPower(-1);
            Box.power = 1;
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
                Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Up + 15);
                Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Up);
                Hardware.intake.setPower(-0.2);
                Box.power = 1;
                //delayedActionGoUnderShared.callAction();
                conditionActionGoUnderShared.callAction();
            }
        }
        if (outtakeUp.onActionRun(TeleUtils.isArmAtPosition((int) Positions.Arm.Up) && TeleUtils.isBoxAtPosition(Positions.Box.Up) && distance > 9)) {
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            Arm.armPid.setTarget((int) Positions.Arm.Down + 60);
            //delayedActionReturnSlidersArmUp.callAction();
            conditionActionReturnSlidersArmUp.callAction();
            stopOutake.callAction();
        }
        if (outtakeBelow.onActionRun(Arm.isBelow && TeleUtils.isBoxAtPosition(Positions.Box.Shared) && distance > 11)) {
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            delayedActionArmReturnBelow.callAction();
            stopOutake.callAction();
        }
    }

}