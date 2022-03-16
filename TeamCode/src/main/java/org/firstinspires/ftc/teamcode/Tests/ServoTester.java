package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

@TeleOp(name = "ServoTest")
public class ServoTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
//        Servo servo1 = hardwareMap.get(Servo.class, "intake");
//        ServoImplEx servo = (ServoImplEx)(servo1);
//        servo.setPwmRange(new PwmControl.PwmRange(500,2500));

        CRServo crServo1 = hardwareMap.get(CRServo.class,"intake");
        CRServoImplEx crServo = (CRServoImplEx)(crServo1);
        crServo.setPwmRange(new PwmControl.PwmRange(1000,2000));


        waitForStart();
        while (!isStopRequested() && opModeIsActive()) {
            if (gamepad1.a){
                crServo.setPower(1);
            }
            else if (gamepad1.b){
                crServo.setPower(-1);
            }

            else {
                crServo.setPower(0);
            }
            telemetry.addData("pwm Range Continous " , crServo.getPower() +" " + crServo.getPwmRange().usPulseLower + " " + crServo.getPwmRange().usPulseUpper);

//
//            if (gamepad1.a){
//                servo.setPosition(1);
//            }
//            else if(gamepad1.b) {
//                servo.setPosition(0);
//            }
//            else{
//                servo.setPosition(0.5);
//            }
//            telemetry.addData("servo power", servo.getPosition() + " " + servo.getPwmRange().usPulseLower+ " " + servo.getPwmRange().usPulseUpper);
            telemetry.update();
        }
    }
}
