package org.firstinspires.ftc.teamcode.TeleOp.Debug;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareUtils;
import org.firstinspires.ftc.teamcode.TeleOp.Arm;
import org.firstinspires.ftc.teamcode.TeleOp.Box;
import org.firstinspires.ftc.teamcode.TeleOp.MAIN;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.BoxAngle;

public class PositionsDebug {

    public static void GetSlidersPosition(boolean update) {
        Hardware.telemetry.addData("Current left slider position is: ", Hardware.slider_left.getCurrentPosition());
        Hardware.telemetry.addData("Current right slider position is: ", Hardware.slider_right.getCurrentPosition());
        Hardware.telemetry.addData("Current PIDF for left slider is: ", ((DcMotorEx) Hardware.slider_left).getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
        Hardware.telemetry.addData("Current PIDF for right slider is: ", ((DcMotorEx) Hardware.slider_right).getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
        Hardware.telemetry.addData("Current TargetPostion for left slider is: ", ((DcMotorEx) Hardware.slider_left).getTargetPosition());
        Hardware.telemetry.addData("Current TargetPostion for right slider is: ", ((DcMotorEx) Hardware.slider_right).getTargetPosition());
        Hardware.telemetry.addData("Current arm Power is: ", Hardware.arm.getPower());
        Hardware.telemetry.addData("Arm pid target pos", Arm.armPid.getTargetPosition());
        if (update)
            Hardware.telemetry.update();
    }

    public static void GetCaruselInfo(boolean update) {
        Hardware.telemetry.addData("Carusel position: ", Hardware.carusel.getCurrentPosition());
        if (update)
            Hardware.telemetry.update();
    }

    public static void GetSlidersPosition() {
        Hardware.telemetry.addData("Current left slider position is: ", Hardware.slider_left.getCurrentPosition());
        Hardware.telemetry.addData("Current right slider position is: ", Hardware.slider_right.getCurrentPosition());
    }

    public static void GetArmPosition(boolean update) {
        Hardware.telemetry.addData("Current arm position is: ", Hardware.arm.getCurrentPosition());
        if (update)
            Hardware.telemetry.update();
    }

    public static void GetArmPosition() {
        Hardware.telemetry.addData("Current arm position is: ", Hardware.arm.getCurrentPosition());

        Hardware.telemetry.addData("Current ARM P: ", Arm.armPid.getKp());
        Hardware.telemetry.addData("Current ARM I: ", Arm.armPid.getKi());
        Hardware.telemetry.addData("Current ARM D: ", Arm.armPid.getKd());

        Hardware.telemetry.addData("Current Velocity COEF : ", ((DcMotorEx) Hardware.arm).getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));

    }

    public static void GetSensors() {
        Hardware.telemetry.addData("Potentiometer Voltage: ", Hardware.potentiometer.getVoltage());
        Hardware.telemetry.addData("Intake Sensor Distance: ", Hardware.intakeSensor.getDistance(DistanceUnit.CM));
    }

    public static void GetBoxPosition() {
        Hardware.telemetry.addData("Current boxAngle position is: ", BoxAngle.getPositionBoxAngle());
        Hardware.telemetry.addData("Current boxAngleMirror position is: ", BoxAngle.getPositionBoxAngleMirror());
    }


}
