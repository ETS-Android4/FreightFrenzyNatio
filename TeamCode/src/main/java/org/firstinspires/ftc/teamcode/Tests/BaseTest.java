package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name = "pulaAAvutCarte")
public class BaseTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor back_right = hardwareMap.get(DcMotor.class, "back_right");
        DcMotor back_left = hardwareMap.get(DcMotor.class, "back_left");
        DcMotor front_left = hardwareMap.get(DcMotor.class, "front_left");
        DcMotor front_right = hardwareMap.get(DcMotor.class, "front_right");
        waitForStart();
        while (!isStopRequested() && opModeIsActive()) {
            back_left.setPower(gamepad1.left_trigger); 
            front_left.setPower(gamepad1.left_trigger);
            back_right.setPower(-gamepad1.left_trigger);
            front_right.setPower(-gamepad1.left_trigger);
        }
    }
}
