package org.firstinspires.ftc.teamcode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.Utilities.OneTap;

public class AdjustArmManual {
    private static OneTap oneTapUp = new OneTap();
    private static OneTap oneTapDown = new OneTap();
    private static final int adjustedBy = 20;

    ///NOT IMPLEMENTED
    public static void adjust() {
        if (oneTapUp.onPress(Gamepads.manualIncrement())) {
            Arm.armPid.setTarget(Arm.armPid.getTargetPosition() + adjustedBy);
            Positions.Arm.Mid += adjustedBy;
            Positions.Arm.Down += adjustedBy;
            Positions.Arm.Up += adjustedBy;
        }
        if (oneTapDown.onPress(Gamepads.manualDecrement())) {
            Arm.armPid.setTarget(Arm.armPid.getTargetPosition() - adjustedBy);
            Positions.Arm.Mid -= adjustedBy;
            Positions.Arm.Down -= adjustedBy;
            Positions.Arm.Up -= adjustedBy;
        }
    }
}
