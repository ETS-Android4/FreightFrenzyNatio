package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.WarehouseSide.cycles3;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.AutoRun.WarehouseSide.cycles3.WarehouseSideTrajectories3;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.ImageDetection;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.Autonomous.MainAuto;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.PoseStorageTeleOp;

public class AutoRunWarehouseSide3 implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;

    public AutoRunWarehouseSide3(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
        WarehouseSideTrajectories3.setDrive(sampleMecanumDrive);
        WarehouseSideTrajectories3.InitTrajectories();
    }

    @Override
    public void run() {
        switch (ImageDetection.duckPosition) {
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
        ImageDetection.camera.stopStreaming();
        PoseStorageTeleOp.setRulerPositions(PoseStorageTeleOp.TMPosition);

        PoseStorage.armPosition = detectedCase.getArmPosition();
        PoseStorage.servoPosition = detectedCase.getServoPosition();
        WarehouseSideTrajectories3.shippingHubWarehouseSidePose = detectedCase.getShippingHubWarehouseSidePose();
        PoseStorage.armPosition = detectedCase.getArmPosition();
        if (PoseStorageTeleOp.TMPosition == 2) {
            PoseStorage.armPosition += 80;
        }
        if(PoseStorageTeleOp.TMPosition == 3 && PoseColorNormalizer.getColorCase()== PoseColorNormalizer.Color.BLUE) {
            WarehouseSideTrajectories3.shippingHubWarehouseSidePose = WarehouseSideTrajectories3.shippingHubWarehouseSidePose.plus(new Pose2d( -4.5, 0, 0));
        }
        goToShippingHubWarehouseSide(sampleMecanumDrive);
        opMode.sleep(350);
        for (int i = 0; i < 3; i++) {
            intake(sampleMecanumDrive);
            opMode.sleep(700);
            place(sampleMecanumDrive);
            opMode.sleep(700);
        }
        park(sampleMecanumDrive);
    }

    public void goToShippingHubWarehouseSide(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories3.ShippingHubTrajectoryWarehouseSide(drive.getPoseEstimate()));
    }

    public void intake(SampleMecanumDriveCancelable drive) {
        drive.followTrajectorySequence(WarehouseSideTrajectories3.IntakeTrajectorySequence(drive.getPoseEstimate()));
    }

    public void place(SampleMecanumDriveCancelable drive) {
        drive.followTrajectorySequence(WarehouseSideTrajectories3.PlaceTrajectorySequence(drive.getPoseEstimate()));
    }

    public void park(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories3.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(WarehouseSideTrajectories3.ParkTrajectory(drive.getPoseEstimate()));
    }
}
