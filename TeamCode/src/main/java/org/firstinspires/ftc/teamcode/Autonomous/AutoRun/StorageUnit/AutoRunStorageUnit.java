package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.StorageUnit;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.AutoRun.CollectDuck.CollectDuckTrajectories;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoUtil;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.ImageDetection;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Arm;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.BoxAngle;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.PoseStorageTeleOp;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class AutoRunStorageUnit implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;

    public AutoRunStorageUnit(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
        StorageUnitTrajectories.setDrive(sampleMecanumDrive);
        StorageUnitTrajectories.InitTrajectories();
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
        Arm.armGoUpAfterColect=false;
        ImageDetection.camera.stopStreaming();
        PoseStorage.armPosition = detectedCase.getArmPosition();
        PoseStorage.servoPosition = detectedCase.getServoPosition();
        StorageUnitTrajectories.shippingHubCaruselSidePose = detectedCase.getShippingHubCaruselSidePose();
        opMode.sleep(1750);
        goToShippingHubCaruselSide(sampleMecanumDrive);
        opMode.sleep(350);
        spinCaruselWithDuckCollect(sampleMecanumDrive);
        collectDuck(sampleMecanumDrive);
        opMode.sleep(500);
        placeDuck(sampleMecanumDrive);
        opMode.sleep(850);//sa nu dea robotu in delta
        parkAfterDuck(sampleMecanumDrive); ///TODO: Park in storage unit
    }

    public void goToShippingHubCaruselSide(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(StorageUnitTrajectories.ShippingHubTrajectoryCaruselSide(drive.getPoseEstimate()));
    }

    public void spinCaruselWithDuckCollect(SampleMecanumDriveCancelable drive) {
        drive.followTrajectorySequence(StorageUnitTrajectories.CaruselTrajectoryWithDuckCollect1(drive.getPoseEstimate()));
        drive.followTrajectory(StorageUnitTrajectories.CaruselTrajectoryWithDuckCollect2(drive.getPoseEstimate()));
        PoseStorage.armPosition = (int) Positions.AutoArm.Down;
        BoxAngle.setPosition(Positions.BoxAuto.Up);
        opMode.sleep(300);
        AutoUtil.spinCaruselDuckColect(drive);
    }

    public void collectDuck(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(StorageUnitTrajectories.CollectDuckIntermediaryTrajectory(drive.getPoseEstimate()));
        drive.followTrajectorySequence(StorageUnitTrajectories.CollectDuckTrajectory(drive.getPoseEstimate()));
    }

    public void placeDuck(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(StorageUnitTrajectories.PlaceDuckTrajectory(drive.getPoseEstimate()));
    }

    public void parkAfterDuck(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(StorageUnitTrajectories.ParkTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(StorageUnitTrajectories.ParkTrajectory2(drive.getPoseEstimate()));
    }
}



