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
    public static int TMPosition;
    public static double rulerAngle;
    public static double rulerBase;

    public AutoRun(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
    }

    @Override
    public void run() {
        switch (ImageDetection.duckPosition){
            case Left:
                detectedCase = new A();
                TMPosition = 1;
                rulerAngle = 0.669;
                rulerBase = 0.560;
                break;
            case Middle:
                detectedCase = new B();
                TMPosition = 2;
                rulerAngle = 0.58;
                rulerBase = 0.536;
                break;
            case Right:
                detectedCase = new C();
                TMPosition = 3;
                rulerAngle = 0.48;
                rulerBase = 0.545;
                break;
        }

        ImageDetection.camera.stopStreaming();
        PoseStorage.armPosition = detectedCase.getArmPosition();
        PoseStorage.servoPosition = detectedCase.getServoPosition();
        Trajectories.shippingHubPose = detectedCase.getShippingHubPose();
        detectedCase.spinCarusel(sampleMecanumDrive);
        detectedCase.goToShippingHub(sampleMecanumDrive);
        opMode.sleep(350);

        for (int i = 0; i < 2; i++) {
            detectedCase.intake(sampleMecanumDrive);
            opMode.sleep(1000);
            detectedCase.place(sampleMecanumDrive);
            opMode.sleep(1300);
        }
        detectedCase.park(sampleMecanumDrive);
    }
}

