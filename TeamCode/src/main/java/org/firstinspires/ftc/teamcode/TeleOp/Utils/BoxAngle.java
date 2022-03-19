package org.firstinspires.ftc.teamcode.TeleOp.Utils;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareDeclarations;

public class BoxAngle extends HardwareDeclarations {
    public static void setPosition(double boxAnglePosition) {
        Hardware.boxAngle.setPosition(boxAnglePosition);
        Hardware.boxAngleMirror.setPosition(boxAnglePosition);
    }
    public static double getPositionBoxAngle(){
        return Hardware.boxAngle.getPosition();
    }
    public static double getPositionBoxAngleMirror(){
        return Hardware.boxAngleMirror.getPosition();
    }
}
