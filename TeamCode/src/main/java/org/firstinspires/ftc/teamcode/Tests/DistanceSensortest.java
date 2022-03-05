package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "distance sensor test")

public class DistanceSensortest extends LinearOpMode {
    public static DistanceSensor intakeSensor;
    @Override
    public void runOpMode() throws InterruptedException {
        intakeSensor = hardwareMap.get(DistanceSensor.class, "intakeSensor");
        waitForStart();
        while (opModeIsActive()&&!isStopRequested()){
            telemetry.addData("sensor distance cm: ", intakeSensor.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}
