package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.Movement;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;

@TeleOp(name = "pulaNAREcarte")
public class NewBase extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive sampleMecanumDrive = new SampleMecanumDrive(hardwareMap);

        Movement.setDrive(sampleMecanumDrive);
        waitForStart();
        while (!isStopRequested() && opModeIsActive()) {
            Gamepads.update(gamepad1,gamepad2);
            Movement.driving();
            telemetry.addData("LF: ", sampleMecanumDrive.leftFront.getCurrentPosition());
            telemetry.addData("LB: ", sampleMecanumDrive.leftRear.getCurrentPosition());
            telemetry.addData("RF: ", sampleMecanumDrive.rightFront.getCurrentPosition());
            telemetry.addData("RB: ", sampleMecanumDrive.rightRear.getCurrentPosition());
            telemetry.update();
        }
    }
}
