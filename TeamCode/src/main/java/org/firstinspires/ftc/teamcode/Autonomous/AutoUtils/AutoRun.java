package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.source.doctree.StartElementTree;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;

public class AutoRun implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;
    public static int TMPosition = 3;
    public static double rulerAngle = 0.788;
    public static double rulerBase = 0.61;

    public AutoRun(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
    }

    @Override
    public void run() {
        //TODO: REFAACTOOOOOOOR!!!@!@#@#!!!!

        // PENTRU PUS
        // 0 ANGLE
        // BASE 0.65


        switch (ImageDetection.duckPosition) {
            case Left:
                detectedCase = new A();
                TMPosition = 1;
                rulerAngle = 0.932;
                rulerBase = 0.707;
                break;
            case Middle:
                detectedCase = new B();
                TMPosition = 2;
                rulerAngle = 0.842;
                rulerBase = 0.644;
                break;
            case Right:
                detectedCase = new C();
                TMPosition = 3;
                rulerAngle = 0.788;
                rulerBase = 0.61;
                break;

        }

        ImageDetection.camera.stopStreaming();
        PoseStorage.armPosition = detectedCase.getArmPosition();
        PoseStorage.servoPosition = detectedCase.getServoPosition();
        Trajectories.shippingHubPose = detectedCase.getShippingHubPose();
        detectedCase.spinCarusel(sampleMecanumDrive);
        PoseStorage.armPosition = detectedCase.getArmPosition();
        detectedCase.goToShippingHub(sampleMecanumDrive);
        //detectedCase.spinCarusel(sampleMecanumDrive);
        opMode.sleep(350);

        for (int i = 0; i < 2; i++) {
            detectedCase.intake(sampleMecanumDrive);
            opMode.sleep(500);
            detectedCase.place(sampleMecanumDrive);
            opMode.sleep(800);
        }
        detectedCase.park(sampleMecanumDrive);
    }
}

