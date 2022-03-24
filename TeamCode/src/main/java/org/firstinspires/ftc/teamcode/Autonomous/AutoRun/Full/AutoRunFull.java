package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.Full;

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

public class AutoRunFull implements Runnable {

    private SampleMecanumDriveCancelable sampleMecanumDrive;
    AutoCases detectedCase;
    private LinearOpMode opMode;

    public AutoRunFull(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        this.sampleMecanumDrive = sampleMecanumDrive;
        this.opMode = opMode;
        FullTrajectories.setDrive(sampleMecanumDrive);
        FullTrajectories.InitTrajectories();
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


        FullTrajectories.shippingHubPose = detectedCase.getShippingHubPose();

        PoseStorage.armPosition = detectedCase.getArmPosition();
        goToShippingHub(sampleMecanumDrive);
        spinCarusel(sampleMecanumDrive);

        opMode.sleep(2000);

        for (int i = 0; i < 2; i++) {
            intake(sampleMecanumDrive);
            opMode.sleep(500);
            place(sampleMecanumDrive);
            opMode.sleep(800);
        }
        park(sampleMecanumDrive);
    }

    public void spinCarusel(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(FullTrajectories.CaruselTrajectory(drive.getPoseEstimate()));
        long firstTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - firstTime < 300);
        AutoUtil.spinCarusel();
    }

    public void goToShippingHub(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(FullTrajectories.ShippingHubTrajectory(drive.getPoseEstimate()));
    }

    public void intake(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(FullTrajectories.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(FullTrajectories.IntakeTrajectory(drive.getPoseEstimate()));
    }

    public void place(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(FullTrajectories.ReturnBackTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(FullTrajectories.ReturnBackTrajectory2(drive.getPoseEstimate()));
    }

    public void park(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(FullTrajectories.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(FullTrajectories.ParkTrajectory(drive.getPoseEstimate()));
    }
}

