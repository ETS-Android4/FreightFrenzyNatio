package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class Sliders {

    public static void toPosition() {
        if (Gamepads.slidersUp()) {
            Hardware.slider_left.setTargetPosition((int)Positions.Sliders.Up);
            Hardware.slider_right.setTargetPosition((int)Positions.Sliders.Up-10);
            changePidf(Hardware.slider_left.getCurrentPosition(), Hardware.slider_left.getTargetPosition());
        } else if (Gamepads.slidersMid()) {
            Hardware.slider_left.setTargetPosition((int)Positions.Sliders.Mid);
            Hardware.slider_right.setTargetPosition((int)Positions.Sliders.Mid-5);
            changePidf(Hardware.slider_left.getCurrentPosition(), Hardware.slider_left.getTargetPosition());
        } else if (Gamepads.slidersDown()) {
            Hardware.slider_left.setTargetPosition((int)Positions.Sliders.Down);
            Hardware.slider_right.setTargetPosition((int)Positions.Sliders.Down);
            changePidf(Hardware.slider_left.getCurrentPosition(), Hardware.slider_left.getTargetPosition());
        }
    }
    private static void changePidf(int currentPos, int targetPos){
        if (currentPos>targetPos){
            ((DcMotorEx)Hardware.slider_left).setPositionPIDFCoefficients(6);
            ((DcMotorEx)Hardware.slider_right).setPositionPIDFCoefficients(6);
        }
        else {
            ((DcMotorEx)Hardware.slider_left).setPositionPIDFCoefficients(9);
            ((DcMotorEx)Hardware.slider_right).setPositionPIDFCoefficients(9);
        }
    }
}
