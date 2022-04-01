package org.firstinspires.ftc.teamcode.Tests;

import static org.firstinspires.ftc.teamcode.Hardware.HardwareUtils.changeDirection;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoUtil;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Initializations;

@TeleOp(name = "TestCarusel")
public class TestCarusel extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Initializations.AutoInitialization(telemetry, hardwareMap);

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {

        }
    }
}
