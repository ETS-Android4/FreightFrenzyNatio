package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.RoadRunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class Trajectories {
    public static Pose2d caruselPosition;
    public static Pose2d shippingHubPose;
    public static Pose2d gapPose;
    public static Pose2d warehousePose;
    public static Pose2d intakePose;
    public static Pose2d parkPose;
    public static Pose2d shippingHubReturnPose;
    public static Pose2d secondIntakeIncrementer;
    public static Pose2d warehouseParkSharedPose1;
    public static Pose2d warehouseParkSharedPose2;
    public static Pose2d shippingHubCaruselSidePose;
    public static Pose2d duckCollectPose;
    public static Pose2d caruselPositionWithDuckCollect;
    public static Pose2d duckCollectPoseIntermediary;
    public static int incrementer = 0;
    ////
    public static Pose2d goOverPose;
    public static Pose2d warehouseOverPose;

    public static void InitTrajectories() {
        caruselPosition = PoseColorNormalizer.calculate(new Pose2d(-58.6, -53.9, java.lang.Math.toRadians(90)));
        caruselPositionWithDuckCollect = PoseColorNormalizer.calculate(new Pose2d(-54.6, -59, java.lang.Math.toRadians(55.75)));
        if (PoseColorNormalizer.getColorCase()== PoseColorNormalizer.Color.BLUE){
            caruselPosition = PoseColorNormalizer.calculate(new Pose2d(-56, -58, java.lang.Math.toRadians(0)));
        }
        shippingHubPose = PoseColorNormalizer.calculate(new Pose2d(-11, -44, java.lang.Math.toRadians(90))); /// suprascris dupa initializare, e in fiecare a b c alta pozitie
        shippingHubCaruselSidePose = PoseColorNormalizer.calculate(new Pose2d(-18, -42, java.lang.Math.toRadians(70)));
        gapPose = PoseColorNormalizer.calculate(new Pose2d(12, -60.3, Math.toRadians(0)));
        warehousePose = PoseColorNormalizer.calculate(new Pose2d(25, -60.3, Math.toRadians(0)));
        intakePose = PoseColorNormalizer.calculate(new Pose2d(42.5, -60.3, Math.toRadians(0)));
        parkPose = PoseColorNormalizer.calculate(new Pose2d(42, -60.3, Math.toRadians(0)));
        shippingHubReturnPose = PoseColorNormalizer.calculate(new Pose2d(8.4, -39.6, Math.toRadians(180 + 135)));
        secondIntakeIncrementer = PoseColorNormalizer.calculate(new Pose2d(6.5, 0, 0));
        PoseStorage.startPosition = PoseColorNormalizer.calculate(new Pose2d(-36, -60.5,Math.toRadians(90)));
        warehouseParkSharedPose1 = PoseColorNormalizer.calculate(new Pose2d(43, -37, Math.toRadians(0)));
        warehouseParkSharedPose2 = PoseColorNormalizer.calculate(new Pose2d(57, -37, Math.toRadians(270)));
        duckCollectPose = PoseColorNormalizer.calculate(new Pose2d(-55.71, -60, Math.toRadians(226)));
        duckCollectPoseIntermediary = PoseColorNormalizer.calculate(new Pose2d(-47, -47, Math.toRadians(226)));
        /////

        goOverPose = PoseColorNormalizer.calculate(new Pose2d(5, -43, Math.toRadians(0)));
        warehouseOverPose = PoseColorNormalizer.calculate(new Pose2d(53, -43, Math.toRadians(0)));

        ////
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
    public static Trajectory CaruselTrajectoryWithDuckCollect(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d, true)
                .splineToLinearHeading(caruselPositionWithDuckCollect, Math.toRadians(PoseColorNormalizer.calculateAngleDegrees(220)))
                .addTemporalMarker(0, () -> {
                    Hardware.intake.setPower(-1);
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
                    PoseStorage.armPosition = (int) Positions.Arm.Down; // -15

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
                    PoseStorage.armPosition = (int) Positions.Arm.Up - 50;
                    Hardware.boxAngle.setPosition(Positions.Box.Up);
                    Hardware.slider_left.setTargetPosition(250);
                    Hardware.slider_right.setTargetPosition(250);
                })
                .build();
    }

    public static Trajectory ReturnBackTrajectory2(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d, true)
                .lineToLinearHeading(shippingHubReturnPose.plus(new Pose2d(1.5 * incrementer, 1 * incrementer, 0)))
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


    public static Trajectory goToBarriers(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(goOverPose)
                .build();
    }

    public static Trajectory goOverBarriers(Pose2d pose2d){
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseOverPose,SampleMecanumDrive.getVelocityConstraint(50, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(20))
                .build();
    }

    public static Trajectory returnOverBarriers(Pose2d pose2d){
        return drive.trajectoryBuilder(pose2d, true)
                .lineToLinearHeading(goOverPose,SampleMecanumDrive.getVelocityConstraint(50, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(20))
                .build();
    }

    public static Trajectory sharedWarehouseTrajectory1(Pose2d pose2d){
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseParkSharedPose1)
                .build();
    }

    public static Trajectory sharedWarehouseTrajectory2(Pose2d pose2d){
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseParkSharedPose2)
                .build();
    }

    public static Trajectory ShippingHubTrajectoryCaruselSide(Pose2d pose2d){
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(shippingHubCaruselSidePose)
                .addTemporalMarker(1.2, () -> {
                    Hardware.intake.setPower(1);
                })
                .addTemporalMarker(0.0, () -> {
                    Hardware.boxAngle.setPosition(PoseStorage.servoPosition);
                })
                .build();
    }
    public static TrajectorySequence CollectDuckTrajectory(Pose2d pose2d){
        return drive.trajectorySequenceBuilder(pose2d)
                .lineToLinearHeading(duckCollectPose)
                .addTemporalMarker(0.1, () -> {
                    Hardware.boxAngle.setPosition(Positions.Box.Mid);
                    Hardware.intake.setPower(-1);
                })
                .turn(Math.toRadians(-25), 5, 4)
                .turn(Math.toRadians(50), 5, 4)
                .build();
    }
    public static Trajectory PlaceDuckTrajectory(Pose2d pose2d){
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(shippingHubCaruselSidePose)
                .addTemporalMarker(0.3, () -> {
                    PoseStorage.armPosition = (int) Positions.Arm.Down - 510;
                    Hardware.boxAngle.setPosition(Positions.Box.Mid + 0.25);
                })
                .addTemporalMarker(2, () ->{
                    Hardware.intake.setPower(1);
                })
                .build();
    }
    public static Trajectory GoOverBarriers1(Pose2d pose2d){
        return drive.trajectoryBuilder(pose2d)
                .lineToSplineHeading(goOverPose)
                .addDisplacementMarker(()->{
                    PoseStorage.armPosition=(int)Positions.Arm.Down-200;
                    Hardware.boxAngle.setPosition(Positions.Box.Up);
                    Hardware.intake.setPower(0);
                })
                .build();
    }
    public static Trajectory GoOverBarriers2(Pose2d pose2d){
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(warehouseOverPose)
                .addDisplacementMarker(()->{
                    PoseStorage.armPosition=(int)Positions.Arm.Down;
                    Hardware.boxAngle.setPosition(Positions.Box.Mid);
                })
                .build();
    }
}
