package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.WarehouseSide.cycles4;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.ImageDetection;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.PoseStorageTeleOp;

public class AutoRunWarehouseSide4 implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;
    public static int TMPosition = 3;
    public static double rulerAngle = 0.788;
    public static double rulerBase = 0.61;

    public AutoRunWarehouseSide4(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
        WarehouseSideTrajectories4.setDrive(sampleMecanumDrive);
        WarehouseSideTrajectories4.InitTrajectories();
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
        PoseStorageTeleOp.setRulerPositions(PoseStorageTeleOp.TMPosition);

        ImageDetection.camera.stopStreaming();
        PoseStorage.armPosition = detectedCase.getArmPosition();
        PoseStorage.servoPosition = detectedCase.getServoPosition();
        PoseStorage.armPosition = detectedCase.getArmPosition()-90;
        goToShippingHubWarehouseSide(sampleMecanumDrive);
        opMode.sleep(350);
        for (int i = 0; i < 4; i++) {
            intake(sampleMecanumDrive);
            opMode.sleep(300);
            place(sampleMecanumDrive);
            opMode.sleep(350);
        }
        park(sampleMecanumDrive);
    }

    public void goToShippingHubWarehouseSide(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories4.ShippingHubTrajectoryWarehouseSidePreload(drive.getPoseEstimate()));
    }

    public void intake(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories4.WarehouseTrajectory1(drive.getPoseEstimate()));
//        drive.followTrajectory(WarehouseSideTrajectories.WarehouseTrajectory2(drive.getPoseEstimate()));
        drive.followTrajectory(WarehouseSideTrajectories4.IntakeTrajectory(drive.getPoseEstimate()));
    }

    public void place(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories4.ReturnBackTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(WarehouseSideTrajectories4.ReturnBackTrajectory2(drive.getPoseEstimate()));
    }

    public void park(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(WarehouseSideTrajectories4.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(WarehouseSideTrajectories4.ParkTrajectory(drive.getPoseEstimate()));
    }
}

