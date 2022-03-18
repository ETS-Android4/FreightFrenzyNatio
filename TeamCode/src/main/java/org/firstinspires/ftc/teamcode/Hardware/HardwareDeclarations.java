package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HardwareDeclarations {

    public static Telemetry telemetry;
    public static HardwareMap hardwareMap;
    public static DcMotor front_left, front_right, back_left, back_right;
    public static DcMotor slider_left, slider_right;
    public static DcMotor arm;

    public static CRServo intake;
    public static Servo boxAngle;
    public static AnalogInput boxPotentiometer;

    public static Servo rulerBase, rulerAngle;
    public static CRServo rulerSpin;

    public static DcMotor carusel;
    public static AnalogInput potentiometer;
    public static AnalogInput servoDebug1;
    public static AnalogInput servoDebug2;

    public static VoltageSensor batteryVoltage;

    public static DistanceSensor intakeSensor;
}
