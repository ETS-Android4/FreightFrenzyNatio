package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.StorageUnit;

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

public class StorageUnitTrajectories {
    public static Pose2d shippingHubCaruselSidePose;
    public static Pose2d duckCollectPose;
    public static Pose2d duckCollectPose2;
    public static Pose2d duckCollectPose3;
    public static Pose2d caruselPositionWithDuckCollect1;
    public static Pose2d caruselPositionWithDuckCollect2;
    public static Pose2d duckCollectPoseIntermediary;
    public static Pose2d placeDuckPose;
    public static Pose2d parkStorageUnitPose1;
    public static Pose2d parkStorageUnitPose2;

    public static void InitTrajectories() {
        caruselPositionWithDuckCollect1 = PoseColorNormalizer.calculate(new Pose2d(-50, -50, Math.toRadians(55.75)));
        caruselPositionWithDuckCollect2 = PoseColorNormalizer.calculate(new Pose2d(-57, -51.5, Math.toRadians(55.75)));
        shippingHubCaruselSidePose = PoseColorNormalizer.calculate(new Pose2d(-17, -40, Math.toRadians(70)));
        placeDuckPose = PoseColorNormalizer.calculate(new Pose2d(-16, -38.5, Math.toRadians(70)));
        PoseStorage.startPosition = PoseColorNormalizer.calculate(new Pose2d(-36, -60.5, Math.toRadians(90)));

        duckCollectPose = PoseColorNormalizer.calculate(new Pose2d(-50, -53.2, Math.toRadians(260)));
        duckCollectPose2 = PoseColorNormalizer.calculate(new Pose2d(-56, -53, Math.toRadians(200)));
        duckCollectPose3 = PoseColorNormalizer.calculate(new Pose2d(-55, -50, Math.toRadians(260)));
        duckCollectPoseIntermediary = PoseColorNormalizer.calculate(new Pose2d(-45, -50, Math.toRadians(55)));

        parkStorageUnitPose1 = PoseColorNormalizer.calculate(new Pose2d(-45, -53, Math.toRadians(0)));
        parkStorageUnitPose2 = PoseColorNormalizer.calculate(new Pose2d(-60, -28.5, Math.toRadians(0)));

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


    public static Trajectory ShippingHubTrajectoryCaruselSide(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(shippingHubCaruselSidePose)
                .addTemporalMarker(1.4, () -> {
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
                .build();
    }


    public static TrajectorySequence CollectDuckTrajectory(Pose2d pose2d) {
        return drive.trajectorySequenceBuilder(pose2d)
                .addTemporalMarker(0, () -> {
                    BoxAngle.setPosition(Positions.BoxAuto.Mid);
                    Hardware.intake.setPower(-1);
                })
                .lineToLinearHeading(duckCollectPose)
                .turn(Math.toRadians(-75), 1.5, 1)
                .lineToLinearHeading(duckCollectPose2)
                .turn(Math.toRadians(60), 1.5, 1)
                .lineToLinearHeading(duckCollectPose3)
                .turn(Math.toRadians(15), 2, 1)
                .build();
    }


    public static Trajectory PlaceDuckTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(placeDuckPose)
                .addTemporalMarker(0.3, () -> {
                    BoxAngle.setPosition(Positions.BoxAuto.Mid + 0.15);
                })
                .addTemporalMarker(0.4, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Down - 580;
                })
                .addTemporalMarker(1.8, () -> {
                    Hardware.intake.setPower(1);
                })
                .build();
    }

    public static Trajectory ParkTrajectory1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(parkStorageUnitPose1)
                .addTemporalMarker(0.8, () -> {
                    PoseStorage.armPosition = (int) Positions.Arm.Down + 90;
                    BoxAngle.setPosition(Positions.Box.Up);
                    Hardware.intake.setPower(0);
                })
                .build();
    }

    public static Trajectory ParkTrajectory2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(parkStorageUnitPose2)
                .build();
    }
}
