package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;


@TeleOp(name = "TestMotorSpeed")
public class TestMotorSpeed extends LinearOpMode {
    public static DcMotorEx motor;
    @Override
    public void runOpMode(){
        motor = hardwareMap.get(DcMotorEx.class, "motor1");
        MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
        motorConfigurationType.setGearing(5.2);
        motorConfigurationType.setTicksPerRev(145.1);
        motorConfigurationType.setMaxRPM(1150);
        motor.setMotorType(motorConfigurationType);
        waitForStart();
        while(opModeIsActive()&&!isStopRequested()){
           // motor.setVelocity(10*(2*Math.PI), AngleUnit.RADIANS);
          motor.setPower(1);
            telemetry.addData("velocity", motor.getVelocity(AngleUnit.RADIANS)/(2*Math.PI));
            telemetry.update();
        }
    }
}
