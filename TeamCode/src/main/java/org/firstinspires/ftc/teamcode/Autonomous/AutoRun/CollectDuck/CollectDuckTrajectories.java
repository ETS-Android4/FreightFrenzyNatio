package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.CollectDuck;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class CollectDuckTrajectories {
    public static Pose2d shippingHubPose;
    public static Pose2d warehouseParkSharedPose2;
    public static Pose2d shippingHubCaruselSidePose;
    public static Pose2d duckCollectPose;
    public static Pose2d duckCollectPose2;
    public static Pose2d caruselPositionWithDuckCollect1;
    public static Pose2d caruselPositionWithDuckCollect2;
    public static Pose2d duckCollectPoseIntermediary;
    public static Pose2d placeDuckPose;
    public static Pose2d goOverPose;
    public static Pose2d warehouseOverPose;

    public static void InitTrajectories() {
        caruselPositionWithDuckCollect1 = PoseColorNormalizer.calculate(new Pose2d(-50, -50, java.lang.Math.toRadians(55.75)));
        caruselPositionWithDuckCollect2 = PoseColorNormalizer.calculate(new Pose2d(-56, -53, java.lang.Math.toRadians(55.75)));
        shippingHubPose = PoseColorNormalizer.calculate(new Pose2d(-11, -44, java.lang.Math.toRadians(90))); /// suprascris dupa initializare, e in fiecare a b c alta pozitie
        shippingHubCaruselSidePose = PoseColorNormalizer.calculate(new Pose2d(-20, -43, java.lang.Math.toRadians(70)));
        placeDuckPose = PoseColorNormalizer.calculate(new Pose2d(-17.5, -42, java.lang.Math.toRadians(70)));
        PoseStorage.startPosition = PoseColorNormalizer.calculate(new Pose2d(-36, -60.5, Math.toRadians(90)));
        warehouseParkSharedPose2 = PoseColorNormalizer.calculate(new Pose2d(60, -37, Math.toRadians(270)));

        duckCollectPose = PoseColorNormalizer.calculate(new Pose2d(-50, -53, Math.toRadians(240)));
        duckCollectPose2 = PoseColorNormalizer.calculate(new Pose2d(-55, -52, Math.toRadians(200)));
        duckCollectPoseIntermediary = PoseColorNormalizer.calculate(new Pose2d(-45, -50, Math.toRadians(55)));

        goOverPose = PoseColorNormalizer.calculate(new Pose2d(5, -44, Math.toRadians(0)));
        warehouseOverPose = PoseColorNormalizer.calculate(new Pose2d(53, -43, Math.toRadians(0)));

    }

    private static SampleMecanumDriveCancelable drive;

    public static void setDrive(SampleMecanumDriveCancelable Drive) {
        drive = Drive;
    }


    public static TrajectorySequence CaruselTrajectoryWithDuckCollect1(Pose2d pose2d) {
        return drive.trajectorySequenceBuilder(pose2d)
                .addTemporalMarker(0, () -> {
                    Hardware.intake.setPower(-1);
                })
                .addTemporalMarker(0.2, () -> {
                    Hardware.boxAngle.setPosition(Positions.Box.Up);
                })
                .addTemporalMarker(0.8, () -> {
                    PoseStorage.armPosition = (int) Positions.Arm.Down + 100;
                })
                .setReversed(true)
                .splineToLinearHeading(caruselPositionWithDuckCollect1, Math.toRadians(PoseColorNormalizer.calculateAngleDegrees(220)))
                .setReversed(false)
                .build();
    }

    public static Trajectory CaruselTrajectoryWithDuckCollect2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(caruselPositionWithDuckCollect2,
                        SampleMecanumDrive.getVelocityConstraint(10, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(10))
                .build();
    }


    public static Trajectory sharedWarehouseTrajectory2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseParkSharedPose2)
                .build();
    }

    public static Trajectory ShippingHubTrajectoryCaruselSide(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(shippingHubCaruselSidePose)
                .addTemporalMarker(1.3, () -> {
                    Hardware.intake.setPower(1);
                })
                .addTemporalMarker(0.7, () -> {
                    Hardware.boxAngle.setPosition(PoseStorage.servoPosition);
                })
                .build();
    }

    public static Trajectory CollectDuckIntermediatyTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(duckCollectPoseIntermediary)
                .build();
    }


    public static TrajectorySequence CollectDuckTrajectory(Pose2d pose2d) {
        return drive.trajectorySequenceBuilder(pose2d)
                .addTemporalMarker(0, () -> {
                    Hardware.boxAngle.setPosition(Positions.Box.Mid);
                    Hardware.intake.setPower(-1);
                })
                .lineToLinearHeading(duckCollectPose)
                .turn(Math.toRadians(-80), 1, 1)
                .lineToLinearHeading(duckCollectPose2)
                .turn(Math.toRadians(80), 0.4, 0.5)
                .build();
    }


    public static Trajectory PlaceDuckTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(placeDuckPose)
                .addTemporalMarker(0.5, () -> {
                    PoseStorage.armPosition = (int) Positions.Arm.Down - 510;
                    Hardware.boxAngle.setPosition(Positions.Box.Mid + 0.25);
                })
                .addTemporalMarker(2, () -> {
                    Hardware.intake.setPower(1);
                })
                .build();
    }

    public static Trajectory GoOverBarriers1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToSplineHeading(goOverPose)
                .addDisplacementMarker(() -> {
                    PoseStorage.armPosition = (int) Positions.Arm.Down - 200;
                    Hardware.boxAngle.setPosition(Positions.Box.Up);
                    Hardware.intake.setPower(0);
                })
                .build();
    }

    public static Trajectory GoOverBarriers2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseOverPose)
                .addDisplacementMarker(() -> {
                    PoseStorage.armPosition = (int) Positions.Arm.Down;
                })
                .build();
    }
}
