package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.source.doctree.StartElementTree;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;

public class TestAutoRun implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;

    public TestAutoRun(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
    }

    @Override
    public void run() {
        switch (ImageDetection.duckPosition){
            case Left:
                detectedCase = new A();
                break;
            case Middle:
                detectedCase = new B();
                break;
            case Right:
                detectedCase = new C();
                break;
        }

        ImageDetection.camera.stopStreaming();
        PoseStorage.armPosition = detectedCase.getArmPosition();
        PoseStorage.servoPosition = detectedCase.getServoPosition();
        Trajectories.shippingHubPose = detectedCase.getShippingHubPose();
        opMode.sleep(350);
        detectedCase.goToBarriers(sampleMecanumDrive);
        for (int i=1;i<=2;i++){
            detectedCase.goOver(sampleMecanumDrive);
            sampleMecanumDrive.setPoseEstimate(new Pose2d(PoseStorage.cameraPosition.getX(),PoseStorage.cameraPosition.getY(),sampleMecanumDrive.getPoseEstimate().getHeading()));
            detectedCase.goOverBack(sampleMecanumDrive);
            sampleMecanumDrive.setPoseEstimate(new Pose2d(PoseStorage.cameraPosition.getX(),PoseStorage.cameraPosition.getY(),sampleMecanumDrive.getPoseEstimate().getHeading()));
        }
        detectedCase.goOver(sampleMecanumDrive);
    }
}

