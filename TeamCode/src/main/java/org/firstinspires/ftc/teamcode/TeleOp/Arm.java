package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.ArmPositionKd;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.ArmPositionKi;
import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.armMaxVelocity;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareUtils;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.ConditionAction;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.CustomPid;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.TeleUtils;
import org.firstinspires.ftc.teamcode.Utilities.DelayedAction;
import org.firstinspires.ftc.teamcode.Utilities.OneTap;

@Config
public class Arm {
    public static CustomPid armPid = new CustomPid(HardwareUtils.ArmPositionKp, ArmPositionKi, ArmPositionKd, armMaxVelocity);
    private static final DelayedAction delayedActionBoxAngleChange = new DelayedAction(300);
    private static final ConditionAction conditionActionGoUnder = new ConditionAction();
    private static final ConditionAction conditionActionGoUnderShared = new ConditionAction();
    private static final ConditionAction conditionActionReturnSliders = new ConditionAction();
    private static final DelayedAction delayedActionArmReturn = new DelayedAction(250);
    public static boolean armGoUpAfterColect = true;
    public static boolean isBelow = false;
    private static OneTap oneTap = new OneTap();

    public static void toPosition() {
        ((DcMotorEx) Hardware.arm).setVelocity(armPid.control((int) (Hardware.potentiometer.getVoltage() * 1000)), AngleUnit.RADIANS);
        if (delayedActionBoxAngleChange.runAction()) {
            Hardware.boxAngle.setPosition(Positions.Box.Down);
        }
        if (conditionActionGoUnder.runAction(TeleUtils.isSlidersAtPosition((int)Positions.Sliders.Up))) {
            armPid.setTarget((int) Positions.Arm.Below);
        }
        if (conditionActionGoUnderShared.runAction(TeleUtils.isSlidersAtPosition((int)Positions.Sliders.Up))) {
            armPid.setTarget((int) Positions.Arm.Shared);
        }
        if(delayedActionArmReturn.runAction()){
            armPid.setTarget((int)Positions.Arm.Down);
            conditionActionReturnSliders.callAction();
        }
        if (conditionActionReturnSliders.runAction(TeleUtils.isArmAtPosition((int)Positions.Arm.Down))) {
            Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Down);
            Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Down);
            isBelow = false;
        }
        if (Gamepads.armDown()) { //coboara bratul
            if (isBelow){
                Hardware.boxAngle.setPosition(Positions.Box.Up);
                delayedActionArmReturn.callAction();
            }
            else{
                armPid.setTarget((int) Positions.Arm.Down);
            }
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            if (armGoUpAfterColect) {
                Hardware.slider_left.setTargetPosition(0);
                Hardware.slider_right.setTargetPosition(0);
            }
        } else if (Gamepads.armShared()) { // mid bratul
            armGoUpAfterColect = false;
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Up + 15);
            Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Up);
            Hardware.intake.setPower(-1);
            Box.power = 1;
            isBelow = true;
            conditionActionGoUnderShared.callAction();
        } else if (Gamepads.armUp()) {//urca bratul
            if (isBelow){
                Hardware.boxAngle.setPosition(Positions.Box.Up);
                delayedActionArmReturn.callAction();
            }
            else{
                armPid.setTarget((int) Positions.Arm.Up);
            }
            armGoUpAfterColect = true;
            armPid.setTarget((int) Positions.Arm.Up);
            if (Math.abs((int) (Hardware.potentiometer.getVoltage() * 1000) - (int) Positions.Arm.Mid + 700) <= 200) {
                Hardware.boxAngle.setPosition(Positions.Box.Down);
            }
            delayedActionBoxAngleChange.callAction();
            if (armGoUpAfterColect) {
                Hardware.slider_left.setTargetPosition(200); /// sliders.up // 500 /// !!!!!!400!!!!!!!!!
                Hardware.slider_right.setTargetPosition(200);
            }
        } else if (Gamepads.armBelow()) {
            armGoUpAfterColect = false;
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Up + 15);
            Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Up);
            Hardware.intake.setPower(-1);
            Box.power = 1;
            isBelow = true;
            conditionActionGoUnder.callAction();
        } else if (Gamepads.armIntermediary()){
            if (isBelow){
                Hardware.boxAngle.setPosition(Positions.Box.Up);
                delayedActionArmReturn.callAction();
            }
            else{
                armPid.setTarget((int) Positions.Arm.Down - 400);
            }
            Hardware.boxAngle.setPosition(Positions.Box.Up);
            if (armGoUpAfterColect) {
                Hardware.slider_left.setTargetPosition(0);
                Hardware.slider_right.setTargetPosition(0);
            }
        }
    }
    public static void checkUpOrBelow(){
        if(oneTap.onPress(Gamepads.changeArmGoUpAfterCollect())){
            Arm.armGoUpAfterColect=!Arm.armGoUpAfterColect;
        }
    }

}