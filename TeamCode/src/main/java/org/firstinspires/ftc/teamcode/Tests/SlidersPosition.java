package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

@TeleOp(name = "SlidersPosition")
public class SlidersPosition extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor dcMotor = hardwareMap.get(DcMotor.class,"slider_left");
        DcMotor dcMotor1 = hardwareMap.get(DcMotor.class,"slider_right");
        waitForStart();
        while (!isStopRequested()&&opModeIsActive()){
            telemetry.addData("slider_left",dcMotor.getCurrentPosition());
            telemetry.addData("slider_right",dcMotor1.getCurrentPosition());
            telemetry.update();
        }
    }


}
