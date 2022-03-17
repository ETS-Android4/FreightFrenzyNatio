package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.WarehouseSide;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.ImageDetection;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;

public class AutoRunWarehouseSide implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;
    public static int TMPosition = 3;
    public static double rulerAngle = 0.788;
    public static double rulerBase = 0.61;

    public AutoRunWarehouseSide(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
        WarehouseSideTrajectories.setDrive(sampleMecanumDrive);
        WarehouseSideTrajectories.InitTrajectories();
    }

    @Override
    public void run() {
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
        WarehouseSideTrajectories.shippingHubPose = detectedCase.getShippingHubPose();
        PoseStorage.armPosition = detectedCase.getArmPosition();
        goToShippingHubWarehouseSide(sampleMecanumDrive);
        opMode.sleep(350);
        for (int i = 0; i < 3; i++) {
            intake(sampleMecanumDrive);
            opMode.sleep(500);
            place(sampleMecanumDrive);
            opMode.sleep(800);
        }
        park(sampleMecanumDrive);
    }

    public void goToShippingHubWarehouseSide(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories.ShippingHubTrajectoryWarehouseSide(drive.getPoseEstimate()));
    }

    public void intake(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(WarehouseSideTrajectories.WarehouseTrajectory2(drive.getPoseEstimate()));
        drive.followTrajectory(WarehouseSideTrajectories.IntakeTrajectory(drive.getPoseEstimate()));
    }

    public void place(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories.ReturnBackTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(WarehouseSideTrajectories.ReturnBackTrajectory2(drive.getPoseEstimate()));
    }

    public void park(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(WarehouseSideTrajectories.ParkTrajectory(drive.getPoseEstimate()));
    }
}

