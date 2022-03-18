package org.firstinspires.ftc.teamcode.Autonomous.AutoRun.WarehouseSide.cycles4;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.BoxAngle;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

import java.util.ArrayList;

public class WarehouseSideTrajectories4 {
    public static Pose2d gapPose;
    public static Pose2d intakePose;
    public static Pose2d parkPose;
    public static Pose2d shippingHubReturnPose;
    public static ArrayList<Pose2d> intakeIncrement = new ArrayList<>();
    public static Pose2d shippingHubPreloadPose;
    public static int incrementer = 0;

    public static void InitTrajectories() {
        gapPose = PoseColorNormalizer.calculate(new Pose2d(10, -63, Math.toRadians(0)));
        intakePose = PoseColorNormalizer.calculate(new Pose2d(42.5, -63, Math.toRadians(0)));
        parkPose = PoseColorNormalizer.calculate(new Pose2d(42, -66, Math.toRadians(0)));
        shippingHubReturnPose = PoseColorNormalizer.calculate(new Pose2d(6, -43, Math.toRadians(180 + 135)));

        intakeIncrement.add(PoseColorNormalizer.calculate(new Pose2d(0, -1, 0)));
        intakeIncrement.add(PoseColorNormalizer.calculate(new Pose2d(0, -2, 0)));
        intakeIncrement.add(PoseColorNormalizer.calculate(new Pose2d(0, -2, 0)));
        intakeIncrement.add(PoseColorNormalizer.calculate(new Pose2d(0, -2, 0)));

        PoseStorage.startPosition = PoseColorNormalizer.calculate(new Pose2d(12, -60.5, Math.toRadians(90)));
        shippingHubPreloadPose = PoseColorNormalizer.calculate(new Pose2d(1, -43, Math.toRadians(110)));


        incrementer = 0;
    }

    private static SampleMecanumDriveCancelable drive;

    public static void setDrive(SampleMecanumDriveCancelable Drive) {
        drive = Drive;
    }


    public static Trajectory WarehouseTrajectory1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(gapPose,SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(90))
                .addTemporalMarker(0, () -> {
                    Hardware.intake.setPower(0);
                    BoxAngle.setPosition(Positions.Box.Up);
                })
                .addTemporalMarker(0.3, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Down; // -15

                    Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Down);
                    Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Down);
                })
                .addDisplacementMarker(() -> {
                    gapPose = gapPose.plus(new Pose2d(0,-2.5,0));
                })
                .build();
    }



    public static Trajectory IntakeTrajectory(Pose2d pose2d) {
        PoseStorage.isIntakeTrajectory = true;
        return drive.trajectoryBuilder(pose2d)

                .lineToLinearHeading(intakePose,
                        SampleMecanumDrive.getVelocityConstraint(85, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(85))
                .addTemporalMarker(0, () -> {
                    BoxAngle.setPosition(Positions.Box.Mid - 0.01);
                })
                .addTemporalMarker(0, () -> {
                    Hardware.intake.setPower(-1);
                })
                .addDisplacementMarker(() -> {
                    intakePose = intakePose.plus(intakeIncrement.get(0));
                    intakeIncrement.remove(0);
                    PoseStorage.isIntakeTrajectory = false;
                })
                .build();
    }

    public static Trajectory ReturnBackTrajectory1(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d, true)
                .lineToLinearHeading(gapPose, SampleMecanumDrive.getVelocityConstraint(75, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(70))
                .addTemporalMarker(0, () -> {
                    BoxAngle.setPosition(Positions.Box.Up - 0.04);
                })
                .addTemporalMarker(1, () -> {
                    PoseStorage.armPosition = (int) Positions.AutoArm.Up - 50;
                    BoxAngle.setPosition(Positions.Box.Up);
                    Hardware.slider_left.setTargetPosition(250);
                    Hardware.slider_right.setTargetPosition(250);
                })
                .build();
    }

    public static Trajectory ReturnBackTrajectory2(Pose2d pose2d) { //asta pune cicluri
        return drive.trajectoryBuilder(pose2d, true)
//                .lineToLinearHeading(shippingHubReturnPose.plus(new Pose2d(1.5 * incrementer, incrementer, 0)), SampleMecanumDrive.getVelocityConstraint(75, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(75))
                .lineToLinearHeading(shippingHubReturnPose, SampleMecanumDrive.getVelocityConstraint(75, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(75))
                .addTemporalMarker(0.4, () -> {
                    Hardware.intake.setPower(0.4);
                })
                .addDisplacementMarker(() -> {
                    incrementer++;
                    shippingHubReturnPose = shippingHubReturnPose.plus(new Pose2d(0,-2,0));

                })
                .build();
    }

    public static Trajectory ParkTrajectory(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(parkPose, SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(100))
                .build();
    }


    public static Trajectory ShippingHubTrajectoryWarehouseSidePreload(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d)
                .lineToLinearHeading(shippingHubPreloadPose, SampleMecanumDrive.getVelocityConstraint(75, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(75))
                .addTemporalMarker(1.2, () -> {
                    Hardware.intake.setPower(1);
                })
                .addTemporalMarker(0.0, () -> {
                    BoxAngle.setPosition(PoseStorage.servoPosition);
                })
                .build();
    }


}
