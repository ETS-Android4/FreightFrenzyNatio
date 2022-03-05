package org.firstinspires.ftc.teamcode.TeleOp.Utils;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareUtils;
import org.firstinspires.ftc.teamcode.TeleOp.IntakeSensor;
import org.firstinspires.ftc.teamcode.Utilities.OneTap;

public class SafetyFeatures {
    public static boolean isOk = true;
    public static boolean isSensorOverriden = false;
    private static int resetSliders = 0;
    private static final int timer = 100;
    private static OneTap onetap = new OneTap();
    private static OneTap onetap2 = new OneTap();
    private static OneTap onetap3 = new OneTap();
    private static long firstTime;
    public static void check(){
        if(onetap.onPress(Hardware.potentiometer.getVoltage() == 0)){
            firstTime = System.currentTimeMillis();
        }
        if (Hardware.potentiometer.getVoltage() == 0 && System.currentTimeMillis() - firstTime > timer){
            isOk = false;
        }
    }
    public static void setZeroPower(){
        Hardware.arm.setPower(0);
        Hardware.carusel.setPower(0);
        Hardware.slider_left.setPower(0);
        Hardware.slider_right.setPower(0);
        Hardware.front_left.setPower(0);
        Hardware.front_right.setPower(0);
        Hardware.back_right.setPower(0);
        Hardware.back_left.setPower(0);
        Hardware.intake.setPower(0);
        Hardware.rulerSpin.setPower(0);
    }
    public static void overrideDistanceSensor(){
        if(IntakeSensor.distance >800&&IntakeSensor.distance<900){
            Hardware.intakeSensor = null;
            Hardware.intakeSensor = HardwareUtils.getDistanceSensor("intakeSensor");
        }
        if (onetap2.onPress(Gamepads.overrideDistanceSensor())){
            isSensorOverriden = !isSensorOverriden;
        }
    }

    public static void resetSlidersPosition(){
        if (onetap3.onPress(Gamepads.resetSlidersPosition())){
            resetSliders = 1 - resetSliders;
            if (resetSliders == 1){
                HardwareUtils.RunningWithoutEncoders(Hardware.slider_right, Hardware.slider_left);
                Hardware.slider_right.setPower(0);
                Hardware.slider_left.setPower(0);
            }
            else{
                HardwareUtils.ResetEncoders(Hardware.slider_right, Hardware.slider_right);
                HardwareUtils.RunningWithEncoders(Hardware.slider_right, Hardware.slider_right);
                Hardware.slider_right.setTargetPosition(0);
                Hardware.slider_left.setTargetPosition(0);
                Hardware.slider_left.setPower(1);
                Hardware.slider_right.setPower(1);
            }
        }
    }

    public static void safety(){
        check();
        overrideDistanceSensor();
        resetSlidersPosition();
    }
}
