package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.Full;

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

public class FullTrajectories {
    public static Pose2d caruselPosition;
    public static Pose2d shippingHubPose;
    public static Pose2d gapPose;
    public static Pose2d warehousePose;
    public static Pose2d intakePose;
    public static Pose2d parkPose;
    public static Pose2d shippingHubReturnPose;
    public static Pose2d secondIntakeIncrementer;
    public static int incrementer = 0;

    public static void InitTrajectories() {
        caruselPosition = PoseColorNormalizer.calculate(new Pose2d(-58.6, -53.9, java.lang.Math.toRadians(90)));
        if (PoseColorNormalizer.getColorCase() == PoseColorNormalizer.Color.BLUE) {
            caruselPosition = PoseColorNormalizer.calculate(new Pose2d(-56, -58, java.lang.Math.toRadians(0)));
        }
        shippingHubPose = PoseColorNormalizer.calculate(new Pose2d(-11, -44, java.lang.Math.toRadians(90))); /// suprascris dupa initializare, e in fiecare a b c alta pozitie
        gapPose = PoseColorNormalizer.calculate(new Pose2d(12, -60.3, Math.toRadians(0)));
        warehousePose = PoseColorNormalizer.calculate(new Pose2d(25, -60.3, Math.toRadians(0)));
        intakePose = PoseColorNormalizer.calculate(new Pose2d(42.5, -60.3, Math.toRadians(0)));
        parkPose = PoseColorNormalizer.calculate(new Pose2d(42, -60.3, Math.toRadians(0)));
        shippingHubReturnPose = PoseColorNormalizer.calculate(new Pose2d(8.4, -39.6, Math.toRadians(180 + 135)));
        secondIntakeIncrementer = PoseColorNormalizer.calculate(new Pose2d(6.5, 0, 0));
        PoseStorage.startPosition = PoseColorNormalizer.calculate(new Pose2d(-36, -60.5, Math.toRadians(90)));

        incrementer = 0;
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
                    Hardware.boxAngle.setPosition(PoseStorage.servoPosition);
                })
                .build();
    }

    public static Trajectory WarehouseTrajectory1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(gapPose)
                .addTemporalMarker(0, () -> {
                    Hardware.intake.setPower(0);
                    Hardware.boxAngle.setPosition(Positions.Box.Up);
                })
                .addTemporalMarker(0.35, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Down; // -15

                    Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Down);
                    Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Down);
                })
                .build();
    }

    public static Trajectory WarehouseTrajectory2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehousePose)
                .addTemporalMarker(0.7, () -> {
                    Hardware.boxAngle.setPosition(Positions.Box.Mid - 0.01);
                })
                .build();
    }

    public static Trajectory IntakeTrajectory(Pose2d pose2d) {
        PoseStorage.isIntakeTrajectory = true;
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(intakePose,
                        SampleMecanumDrive.getVelocityConstraint(60, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(35))
                .addTemporalMarker(0, () -> {
                    Hardware.intake.setPower(-1);
                })
                .addDisplacementMarker(() -> {
                    intakePose = intakePose.plus(secondIntakeIncrementer);
                    PoseStorage.isIntakeTrajectory = false;
                })
                .build();
    }

    public static Trajectory ReturnBackTrajectory1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d, true)
                .lineToLinearHeading(gapPose)
                .addTemporalMarker(0, () -> {
                    Hardware.boxAngle.setPosition(Positions.Box.Up - 0.04);
                })
                .addTemporalMarker(1, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Up - 50;
                    Hardware.boxAngle.setPosition(Positions.Box.Up);
                    Hardware.slider_left.setTargetPosition(250);
                    Hardware.slider_right.setTargetPosition(250);
                })
                .build();
    }

    public static Trajectory ReturnBackTrajectory2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d, true)
                .lineToLinearHeading(shippingHubReturnPose.plus(new Pose2d(1.5 * incrementer, incrementer, 0)))
                .addDisplacementMarker(() -> {
                    Hardware.intake.setPower(0.35);
                })
                .addDisplacementMarker(() -> {
                    incrementer++;
                })
                .build();
    }

    public static Trajectory ParkTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(parkPose)
                .build();
    }


}
