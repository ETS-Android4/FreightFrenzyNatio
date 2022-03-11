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

public class AutoRun implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;

    public AutoRun(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
    }

    @Override
    public void run() {

        // PENTRU PUS
        // 0 ANGLE
        // BASE 0.65

        switch (ImageDetection.duckPosition){
            case Left:
                detectedCase = new A();
                PoseStorageTeleOp.TMPosition = 1;
                break;
            case Middle:
                detectedCase = new B();
                PoseStorageTeleOp.TMPosition = 2;
                break;
            case Right:
                detectedCase = new C();
                PoseStorageTeleOp.TMPosition = 3;
                break;
        }
        PoseStorageTeleOp.setRulerPositions(PoseStorageTeleOp.TMPosition);

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

