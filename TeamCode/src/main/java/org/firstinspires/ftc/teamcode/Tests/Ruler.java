package org.firstinspires.ftc.teamcode.Tests;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ruler")
public class Ruler extends LinearOpMode {
    @Override
    public void runOpMode() {
        Servo rulerLateral, rulerLongitudinal;
        CRServo rulerSpin;
        rulerLateral = hardwareMap.get(Servo.class, "rulerBase");
        rulerLongitudinal = hardwareMap.get(Servo.class, "rulerBase");
        rulerSpin = hardwareMap.get(CRServo.class, "rulerSpin");

        rulerLateral.setPosition(0.5);
        rulerLongitudinal.setPosition(0.5);

        waitForStart();
        while (!isStopRequested() && opModeIsActive()) {

        }
    }

}
