package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "IntakeSensorDistances")
public class IntakeSensorDistances extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DistanceSensor intakeSensor;
        intakeSensor = hardwareMap.get(DistanceSensor.class, "intakeSensor");

        waitForStart();
        while (!isStopRequested() && opModeIsActive()) {
            telemetry.addData("intakeSensor", intakeSensor.getDistance(DistanceUnit.MM));
            telemetry.update();
        }
    }

}
