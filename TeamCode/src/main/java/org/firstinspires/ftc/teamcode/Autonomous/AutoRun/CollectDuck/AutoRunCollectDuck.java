package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.CollectDuck;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoUtil;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.ImageDetection;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Arm;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.BoxAngle;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.PoseStorageTeleOp;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class AutoRunCollectDuck implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;

    public AutoRunCollectDuck(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
        CollectDuckTrajectories.setDrive(sampleMecanumDrive);
        CollectDuckTrajectories.InitTrajectories();
    }

    @Override
    public void run() {
        ///12 cm in stanga robotului pana la celalalt tile
        //17.4 cm in dreapta robotului pana la celalalt tile
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
        if (PoseStorageTeleOp.TMPosition == 2) {
            PoseStorage.armPosition += 70;
        }
        CollectDuckTrajectories.shippingHubCaruselSidePose = detectedCase.getShippingHubCaruselSidePose();
        if (PoseStorageTeleOp.TMPosition == 2 && PoseColorNormalizer.getColorCase() == PoseColorNormalizer.Color.BLUE) {
            CollectDuckTrajectories.shippingHubCaruselSidePose = CollectDuckTrajectories.shippingHubCaruselSidePose.plus(new Pose2d(0, 2, 0));
        }
        if (PoseStorageTeleOp.TMPosition == 3 && PoseColorNormalizer.getColorCase() == PoseColorNormalizer.Color.BLUE) {
            CollectDuckTrajectories.shippingHubCaruselSidePose = CollectDuckTrajectories.shippingHubCaruselSidePose.plus(new Pose2d(5, 0, 0));
        }
        opMode.sleep(1750);
        goToShippingHubCaruselSide(sampleMecanumDrive);
        opMode.sleep(350);
        spinCaruselWithDuckCollect(sampleMecanumDrive);
        collectDuck(sampleMecanumDrive);
        opMode.sleep(500);
        placeDuck(sampleMecanumDrive);
        opMode.sleep(850);//sa nu dea robotu in delta
        parkAfterDuck(sampleMecanumDrive);
    }

    public void goToShippingHubCaruselSide(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(CollectDuckTrajectories.ShippingHubTrajectoryCaruselSide(drive.getPoseEstimate()));
    }

    public void spinCaruselWithDuckCollect(SampleMecanumDriveCancelable drive) {
        drive.followTrajectorySequence(CollectDuckTrajectories.CaruselTrajectoryWithDuckCollect1(drive.getPoseEstimate()));
        drive.followTrajectory(CollectDuckTrajectories.CaruselTrajectoryWithDuckCollect2(drive.getPoseEstimate()));
        PoseStorage.armPosition = (int) Positions.AutoArm.Down;
        BoxAngle.setPosition(Positions.BoxAuto.Up);
        long firstTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - firstTime < 300) {
        }
        AutoUtil.spinCaruselDuckColect(drive);
    }

    public void collectDuck(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(CollectDuckTrajectories.CollectDuckIntermediaryTrajectory(drive.getPoseEstimate()));
        drive.followTrajectorySequence(CollectDuckTrajectories.CollectDuckTrajectory(drive.getPoseEstimate()));
    }

    public void placeDuck(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(CollectDuckTrajectories.PlaceDuckTrajectory(drive.getPoseEstimate()));
    }

    public void parkAfterDuck(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(CollectDuckTrajectories.GoOverBarriers1(drive.getPoseEstimate()));
        drive.followTrajectory(CollectDuckTrajectories.GoOverBarriers2(drive.getPoseEstimate()));
        drive.followTrajectory(CollectDuckTrajectories.sharedWarehouseTrajectory2(drive.getPoseEstimate()));
    }

}



