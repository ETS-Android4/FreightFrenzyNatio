package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.Half;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.A;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoUtil;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.ImageDetection;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Autonomous.B;
import org.firstinspires.ftc.teamcode.Autonomous.C;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.PoseStorageTeleOp;

public class AutoRunHalf implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;

    public AutoRunHalf(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
        HalfTrajectories.setDrive(sampleMecanumDrive);
        HalfTrajectories.InitTrajectories();
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
        HalfTrajectories.shippingHubPose = detectedCase.getShippingHubPose();
        spinCarusel(sampleMecanumDrive);
        PoseStorage.armPosition = detectedCase.getArmPosition();
        goToShippingHub(sampleMecanumDrive);
        opMode.sleep(350);

        sharedPark(sampleMecanumDrive);
    }

    public void spinCarusel(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(HalfTrajectories.CaruselTrajectory(drive.getPoseEstimate()));
        long firstTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - firstTime < 300) {
        }
        AutoUtil.spinCarusel();
    }

    public void goToShippingHub(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(HalfTrajectories.ShippingHubTrajectory(drive.getPoseEstimate()));
    }

    public void sharedPark(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(HalfTrajectories.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(HalfTrajectories.ParkTrajectory(drive.getPoseEstimate()));
        drive.followTrajectory(HalfTrajectories.sharedWarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(HalfTrajectories.sharedWarehouseTrajectory2(drive.getPoseEstimate()));
    }
}

