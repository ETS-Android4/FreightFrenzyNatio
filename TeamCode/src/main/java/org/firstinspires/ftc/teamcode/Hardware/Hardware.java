package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.*;

import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hardware extends HardwareDeclarations {

    private static void hardwareMapping() {
        front_left = getDc("front_left");
        front_right = getDc("front_right");
        back_right = getDc("back_right");
        back_left = getDc("back_left");

        potentiometer = getAnalogInput("potentiometer");
        boxPotentiometer = getAnalogInput("boxPotentiometer");

        rulerBase = getServo("rulerLateral");
        rulerAngle = getServo("rulerLongitudinal");
        rulerSpin = getCRServo("rulerSpin");

        slider_left = getDc("slider_left");
        slider_right = getDc("slider_right");
        arm = getDc("arm");

        intake = getCRServo("intake");
        ((CRServoImplEx)(intake)).setPwmRange(new PwmControl.PwmRange(1000,2000));
        boxAngle = getServo("boxAngle");

        carusel = getDc("carusel");

        intakeSensor = getDistanceSensor("intakeSensor");

        batteryVoltage = hardwareMap.voltageSensor.iterator().next();
    }

    public static void init(HardwareMap hm, Telemetry telemetry) {
        HardwareDeclarations.telemetry = telemetry;
        HardwareDeclarations.hardwareMap = hm;
        HardwareDeclarations.telemetry.addLine("Initializing robot...");
        hardwareMapping();
        HardwareDeclarations.telemetry.addLine("Hardware mapping done!");

        ResetEncoders(slider_left, slider_right, arm);
        RunningWithEncoders(slider_left, slider_right);

        Hardware.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorConfigurationType motorConfigurationType = Hardware.arm.getMotorType().clone();
        motorConfigurationType.setGearing(5.2);
        motorConfigurationType.setTicksPerRev(145.1);
        motorConfigurationType.setMaxRPM(1150);
        Hardware.arm.setMotorType(motorConfigurationType);

        SetPIDCoefficients();

        changeDirection(carusel);
        carusel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        carusel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        HardwareDeclarations.telemetry.addLine("Direction changing done!");

    }

    public static void autoInit(HardwareMap hm, Telemetry telemetry){
        HardwareDeclarations.telemetry = telemetry;
        HardwareDeclarations.hardwareMap = hm;
        HardwareDeclarations.telemetry.addLine("Initializing robot...");
        hardwareMapping();
        HardwareDeclarations.telemetry.addLine("Hardware mapping done!");

        ResetEncoders(slider_left, slider_right, arm);
        RunningWithEncoders(slider_left, slider_right);

        Hardware.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorConfigurationType motorConfigurationType = Hardware.arm.getMotorType().clone();
        motorConfigurationType.setGearing(5.2);
        motorConfigurationType.setTicksPerRev(145.1);
        motorConfigurationType.setMaxRPM(1150);
        Hardware.arm.setMotorType(motorConfigurationType);

        SetPIDCoefficients();

        Hardware.carusel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        carusel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        changeDirection(carusel);
    }

}
