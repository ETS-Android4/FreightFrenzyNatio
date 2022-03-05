package org.firstinspires.ftc.teamcode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.Utilities.DelayedAction;
import org.firstinspires.ftc.teamcode.Utilities.OneTap;

public class Box {
    public static OneTap intake = new OneTap();
    public static OneTap outtake = new OneTap();
    public static double power = 0;
    public static DelayedAction startIntake = new DelayedAction(100);
    public static DelayedAction startOuttake = new DelayedAction(300);

    public static OneTap boxAngleAdjustPlus = new OneTap();
    public static OneTap boxAngleAdjustMinus = new OneTap();

    public static void toPosition() {
        if (boxAngleAdjustPlus.onPress(Gamepads.boxSharedAdjustPlus())){
            Positions.Box.Shared+=0.03;
            Hardware.boxAngle.setPosition(Positions.Box.Shared);
        }
        if (boxAngleAdjustMinus.onPress(Gamepads.boxSharedAdjustMinus())){
            Positions.Box.Shared-=0.03;
            Hardware.boxAngle.setPosition(Positions.Box.Shared);
        }
        if (startIntake.runAction()) {
            Hardware.intake.setPower(-1);
            power = 1;
        }
        if (startOuttake.runAction()){
            Hardware.intake.setPower(0.25);
            power = 1;
        }
        if (Gamepads.releaseFreight()) {
            Hardware.intake.setPower(0);
            power = 0;
            if (Hardware.potentiometer.getVoltage()*1000 > Positions.Arm.Below - 100){
                Hardware.boxAngle.setPosition(Positions.Box.Below);
                Hardware.intake.setPower(1);
                power = 1;
            }
            else if (Hardware.potentiometer.getVoltage()*1000 > Positions.Arm.Shared - 200){
                Hardware.boxAngle.setPosition(Positions.Box.Shared);
                Hardware.intake.setPower(1);
                power = 1;
            }
            else{
                Hardware.boxAngle.setPosition(Positions.Box.Up);
            }
            if ((int) (Hardware.potentiometer.getVoltage() * 1000) < Positions.Arm.Mid + 300) {
                startOuttake.callAction();
                Hardware.intake.setPower(-1);
                power = 1;
            }
        } else if (Gamepads.boxMid()) {
            Hardware.boxAngle.setPosition(Positions.Box.Mid);
            if (Hardware.potentiometer.getVoltage() * 1000 > Positions.Arm.Mid + 200 && Hardware.potentiometer.getVoltage() * 1000 < Positions.Arm.Down + 30) {
                Hardware.intake.setPower(-1);
                power = 1;
            }
            if (Hardware.potentiometer.getVoltage() * 1000 > Positions.Arm.Down + 30 && Hardware.potentiometer.getVoltage() * 1000 < Positions.Arm.Down + 120) {
                Arm.armPid.setTarget((int) Positions.Arm.Down);
                startIntake.callAction();
            }
        } else if (Gamepads.boxDown()) {
            Hardware.boxAngle.setPosition(Positions.Box.Down - 0.04);
        }
    }

    public static void intake() {
        if (intake.onPress(Gamepads.boxIntake())) {
            power = 1 - power;
            Hardware.intake.setPower(-power);
        }
        if (outtake.onPress(Gamepads.boxOutake())) {
            power = 1;
            Hardware.intake.setPower(power);
        }
    }
}
