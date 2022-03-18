package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.WarehouseSide.cycles3;

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

public class WarehouseSideTrajectories3 {
    public static Pose2d shippingHubPose;
    public static Pose2d gapPose;
    public static Pose2d warehousePose;
    public static Pose2d intakePose;
    public static Pose2d parkPose;
    public static Pose2d shippingHubReturnPose;
    public static Pose2d secondIntakeIncrementer;
    public static Pose2d shippingHubWarehouseSidePose;
    public static int incrementer = 0;

    public static void InitTrajectories() {
        shippingHubPose = PoseColorNormalizer.calculate(new Pose2d(-6, -44, java.lang.Math.toRadians(90))); /// suprascris dupa initializare, e in fiecare a b c alta pozitie
        gapPose = PoseColorNormalizer.calculate(new Pose2d(12, -60.7, Math.toRadians(0)));
        warehousePose = PoseColorNormalizer.calculate(new Pose2d(25, -60.7, Math.toRadians(0)));
        intakePose = PoseColorNormalizer.calculate(new Pose2d(40, -60.7, Math.toRadians(0)));
        parkPose = PoseColorNormalizer.calculate(new Pose2d(45, -60.7, Math.toRadians(0)));
        shippingHubReturnPose = PoseColorNormalizer.calculate(new Pose2d(2, -39, Math.toRadians(180 + 135)));
        secondIntakeIncrementer = PoseColorNormalizer.calculate(new Pose2d(5, 0, 0));

        PoseStorage.startPosition = PoseColorNormalizer.calculate(new Pose2d(12, -60.5, Math.toRadians(90)));

        shippingHubWarehouseSidePose = PoseColorNormalizer.calculate(new Pose2d(-5, -40.5, Math.toRadians(110)));


        incrementer = 0;
    }

    private static SampleMecanumDriveCancelable drive;

    public static void setDrive(SampleMecanumDriveCancelable Drive) {
        drive = Drive;
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

    public static Trajectory IntakeTrajectory(Pose2d pose2d) {
        PoseStorage.isIntakeTrajectory = true;
        return drive.trajectoryBuilder(pose2d)
                .lineToSplineHeading(intakePose,
                        SampleMecanumDrive.getVelocityConstraint(60, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(35))
                .addTemporalMarker(0, () -> {
                    Hardware.boxAngle.setPosition(Positions.Box.Mid - 0.01);
                    Hardware.intake.setPower(-1);
                })
                .addDisplacementMarker(() -> {
                    intakePose = intakePose.plus(secondIntakeIncrementer);
                    PoseStorage.isIntakeTrajectory = false;
                })
                .build();
    }

    public static TrajectorySequence IntakeTrajectorySequence(Pose2d pose2d){
        return drive.trajectorySequenceBuilder(pose2d)
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
                .lineToSplineHeading(intakePose,
                        SampleMecanumDrive.getVelocityConstraint(50, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(75))
                .addTemporalMarker(1.5, () -> {
                    PoseStorage.isIntakeTrajectory = true;
                    Hardware.boxAngle.setPosition(Positions.Box.Mid - 0.01);
                    Hardware.intake.setPower(-1);
                })
                .addDisplacementMarker(() -> {
                    intakePose = intakePose.plus(secondIntakeIncrementer);
                    PoseStorage.isIntakeTrajectory = false;
                })
                .build();
    }

    public static TrajectorySequence PlaceTrajectorySequence(Pose2d pose2d){
        return drive.trajectorySequenceBuilder(pose2d)
                .setReversed(true)
                .lineToSplineHeading(gapPose)
                .addTemporalMarker(0, () -> {
                    Hardware.boxAngle.setPosition(Positions.Box.Up - 0.04);
                })
                .addTemporalMarker(0.3, ()->{
                    Hardware.intake.setPower(-0.2);

                })
                .addTemporalMarker(1, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Up;
                    Hardware.boxAngle.setPosition(Positions.Box.Up);
                    Hardware.slider_left.setTargetPosition(250);
                    Hardware.slider_right.setTargetPosition(250);
                })
                .lineToSplineHeading(shippingHubReturnPose.plus(new Pose2d(1.5 * incrementer, incrementer, 0)))
                .addTemporalMarker(2.7, () -> {
                    Hardware.intake.setPower(0.35);
                    incrementer++;
                })
                .setReversed(false)
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
                    Hardware.intake.setPower(0.5);
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


    public static Trajectory ShippingHubTrajectoryWarehouseSide(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(shippingHubWarehouseSidePose)
                .addTemporalMarker(1.2, () -> {
                    Hardware.intake.setPower(1);
                })
                .addTemporalMarker(0.0, () -> {
                    Hardware.boxAngle.setPosition(PoseStorage.servoPosition);
                })
                .build();
    }

}