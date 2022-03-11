package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.source.doctree.StartElementTree;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.PoseStorageTeleOp;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class AutoRunCollectDuck implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;

    public AutoRunCollectDuck(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
    }

    @Override
    public void run() {
        switch (ImageDetection.duckPosition) {
            case Left:
                detectedCase = new A();
                PoseStorageTeleOp.TMPosition = 1;
                PoseStorageTeleOp.rulerAngle = 0.669;
                PoseStorageTeleOp.rulerBase = 0.560;
                break;
            case Middle:
                detectedCase = new B();
                PoseStorageTeleOp.TMPosition = 2;
                PoseStorageTeleOp.rulerAngle = 0.58;
                PoseStorageTeleOp.rulerBase = 0.536;
                break;
            case Right:
                detectedCase = new C();
                PoseStorageTeleOp.TMPosition = 3;
                PoseStorageTeleOp.rulerAngle = 0.48;
                PoseStorageTeleOp.rulerBase = 0.545;
                break;
        }
        PoseStorageTeleOp.setRulerPositions(PoseStorageTeleOp.TMPosition);

        ImageDetection.camera.stopStreaming();
        PoseStorage.armPosition = detectedCase.getArmPosition();
        PoseStorage.servoPosition = detectedCase.getServoPosition();
        Trajectories.shippingHubPose = detectedCase.getShippingHubPose();

        detectedCase.goToShippingHubCaruselSide(sampleMecanumDrive);
        opMode.sleep(350);
        detectedCase.spinCaruselWithDuckCollect(sampleMecanumDrive);
        detectedCase.collectDuck(sampleMecanumDrive);
        opMode.sleep(500);
        detectedCase.placeDuck(sampleMecanumDrive);
        opMode.sleep(350);
        opMode.sleep(500);//sa nu dea robotu in delta
        detectedCase.parkAfterDuck(sampleMecanumDrive);
    }
}

