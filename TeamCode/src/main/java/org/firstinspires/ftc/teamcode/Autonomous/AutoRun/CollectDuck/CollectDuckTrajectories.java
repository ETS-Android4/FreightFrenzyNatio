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
import org.firstinspires.ftc.teamcode.TeleOp.Utils.BoxAngle;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class CollectDuckTrajectories {
    public static Pose2d warehouseParkSharedPose2;
    public static Pose2d shippingHubCaruselSidePose;
    public static Pose2d duckCollectPose;
    public static Pose2d duckCollectPose2;
    public static Pose2d duckCollectPose3;
    public static Pose2d caruselPositionWithDuckCollect1;
    public static Pose2d caruselPositionWithDuckCollect2;
    public static Pose2d duckCollectPoseIntermediary;
    public static Pose2d placeDuckPose;
    public static Pose2d goOverPose;
    public static Pose2d warehouseOverPose;

    public static void InitTrajectories() {
        caruselPositionWithDuckCollect1 = PoseColorNormalizer.calculate(new Pose2d(-50, -50, java.lang.Math.toRadians(55.75)));
        caruselPositionWithDuckCollect2 = PoseColorNormalizer.calculate(new Pose2d(-56.3, -51, java.lang.Math.toRadians(55.75)));
        shippingHubCaruselSidePose = PoseColorNormalizer.calculate(new Pose2d(-17, -40, java.lang.Math.toRadians(70)));
        placeDuckPose = PoseColorNormalizer.calculate(new Pose2d(-16, -38.5, java.lang.Math.toRadians(70)));
        PoseStorage.startPosition = PoseColorNormalizer.calculate(new Pose2d(-36, -60.5, Math.toRadians(90)));
        warehouseParkSharedPose2 = PoseColorNormalizer.calculate(new Pose2d(63, -35, Math.toRadians(270)));

        duckCollectPose = PoseColorNormalizer.calculate(new Pose2d(-50, -53.2, Math.toRadians(260)));
        duckCollectPose2 = PoseColorNormalizer.calculate(new Pose2d(-56, -53, Math.toRadians(200)));
        duckCollectPose3 = PoseColorNormalizer.calculate(new Pose2d(-55, -50, Math.toRadians(260)));
        duckCollectPoseIntermediary = PoseColorNormalizer.calculate(new Pose2d(-45, -50, Math.toRadians(55)));

        goOverPose = PoseColorNormalizer.calculate(new Pose2d(5, -38, Math.toRadians(0)));
        warehouseOverPose = PoseColorNormalizer.calculate(new Pose2d(51, -38, Math.toRadians(0)));

        if (PoseColorNormalizer.getColorCase() == PoseColorNormalizer.Color.BLUE) {
            caruselPositionWithDuckCollect1 = new Pose2d(-55, 48, java.lang.Math.toRadians(304.25));
            caruselPositionWithDuckCollect2 = new Pose2d(-65, 50, java.lang.Math.toRadians(315));
            duckCollectPose2 = new Pose2d(-58, 53, Math.toRadians(160));
            duckCollectPose3 = new Pose2d(-55, 53.5, Math.toRadians(100));

            goOverPose = new Pose2d(5, 42, Math.toRadians(0));
            warehouseOverPose = new Pose2d(51, 42, Math.toRadians(0));
            placeDuckPose = new Pose2d(-16, 36, java.lang.Math.toRadians(290));
        }

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
                    BoxAngle.setPosition(Positions.BoxAuto.Up);
                })
                .addTemporalMarker(0.8, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Down + 100;
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
                .addTemporalMarker(0.5, () -> {
                    BoxAngle.setPosition(PoseStorage.servoPosition);
                })
                .build();
    }

    public static Trajectory CollectDuckIntermediaryTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(duckCollectPoseIntermediary)
                .addTemporalMarker(0, () -> {
                    PoseStorage.armPosition =(int) Positions.AutoArm.Down;
                })
                .build();
    }


    public static TrajectorySequence CollectDuckTrajectory(Pose2d pose2d) {
        return drive.trajectorySequenceBuilder(pose2d)
                .addTemporalMarker(0, () -> {
                    BoxAngle.setPosition(Positions.BoxAuto.Mid);
                    Hardware.intake.setPower(-1);
                })
                .lineToLinearHeading(duckCollectPose)
                .turn(Math.toRadians(PoseColorNormalizer.calculateTurnAngleDegrees(-75)), 1.5, 1)
                .lineToLinearHeading(duckCollectPose2)
                .turn(Math.toRadians(PoseColorNormalizer.calculateTurnAngleDegrees(60)), 1.5, 1)
                .lineToLinearHeading(duckCollectPose3)
                .turn(Math.toRadians(PoseColorNormalizer.calculateTurnAngleDegrees(15)), 2, 1)
                .build();
    }


    public static Trajectory PlaceDuckTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(placeDuckPose)
                .addTemporalMarker(0.65, () -> {
                    BoxAngle.setPosition(Positions.BoxAuto.Mid + 0.15);
                })
                .addTemporalMarker(0.5, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Down - 560;
                })
                .addTemporalMarker(1.8, () -> {
                    Hardware.intake.setPower(1);
                })
                .build();
    }

    public static Trajectory GoOverBarriers1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToSplineHeading(goOverPose)
                .addTemporalMarker(0, () -> {
                    BoxAngle.setPosition(Positions.BoxAuto.Up);
                })
                .addDisplacementMarker(() -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Down - 200;
                    Hardware.intake.setPower(0);
                })
                .build();
    }

    public static Trajectory GoOverBarriers2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseOverPose)
                .addDisplacementMarker(() -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Down;
                })
                .build();
    }
}
