package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.Half;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.BoxAngle;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class HalfTrajectories {
    public static Pose2d caruselPosition;
    public static Pose2d shippingHubPose;
    public static Pose2d gapPose;
    public static Pose2d parkPose;
    public static Pose2d warehouseParkSharedPose1;
    public static Pose2d warehouseParkSharedPose2;

    public static void InitTrajectories() {
        caruselPosition = PoseColorNormalizer.calculate(new Pose2d(-58.6, -53.9, java.lang.Math.toRadians(90)));
        if (PoseColorNormalizer.getColorCase() == PoseColorNormalizer.Color.BLUE) {
            caruselPosition = PoseColorNormalizer.calculate(new Pose2d(-56, -58, java.lang.Math.toRadians(0)));
        }
        shippingHubPose = PoseColorNormalizer.calculate(new Pose2d(-11, -44, java.lang.Math.toRadians(90))); /// suprascris dupa initializare, e in fiecare a b c alta pozitie
        gapPose = PoseColorNormalizer.calculate(new Pose2d(12, -60.3, Math.toRadians(0)));
        parkPose = PoseColorNormalizer.calculate(new Pose2d(42, -60.3, Math.toRadians(0)));
        PoseStorage.startPosition = PoseColorNormalizer.calculate(new Pose2d(-36, -60.5, Math.toRadians(90)));
        warehouseParkSharedPose1 = PoseColorNormalizer.calculate(new Pose2d(43, -37, Math.toRadians(0)));
        warehouseParkSharedPose2 = PoseColorNormalizer.calculate(new Pose2d(60, -37, Math.toRadians(270)));
    }

    private static SampleMecanumDriveCancelable drive;

    public static void setDrive(SampleMecanumDriveCancelable Drive) {
        drive = Drive;
    }

    public static Trajectory CaruselTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(caruselPosition)
                .addTemporalMarker(0, () -> {
                    Hardware.intake.setPower(-0.7);
                })
                .build();
    }


    public static Trajectory ShippingHubTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(shippingHubPose)
                .addTemporalMarker(1.8, () -> {
                    Hardware.intake.setPower(1);
                })
                .addTemporalMarker(0.0, () -> {
                    BoxAngle.setPosition(PoseStorage.servoPosition);
                })
                .build();
    }

    public static Trajectory WarehouseTrajectory1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(gapPose)
                .addTemporalMarker(0, () -> {
                    Hardware.intake.setPower(0);
                    BoxAngle.setPosition(Positions.Box.Up);
                })
                .addTemporalMarker(0.35, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Down; // -15

                    Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Down);
                    Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Down);
                })
                .build();
    }


    public static Trajectory ParkTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(parkPose)
                .build();
    }


    public static Trajectory sharedWarehouseTrajectory1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseParkSharedPose1)
                .build();
    }

    public static Trajectory sharedWarehouseTrajectory2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseParkSharedPose2)
                .build();
    }


}
