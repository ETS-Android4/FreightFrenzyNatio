package org.firstinspires.ftc.teamcode.TeleOp;


import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;

public class Movement {

    private static SampleMecanumDrive drive;
    private static Telemetry telemetry;
    public static int slowFactor = 1;

    public static void setDrive(SampleMecanumDrive drive) {
        Movement.drive = drive;
    }

    public static void setTelemetry(Telemetry telemetry) {
        Movement.telemetry = telemetry;
    }

    public static void driving() {
        slowMovement(4);
        drive.setWeightedDrivePower(
                new Pose2d(
                        -Gamepads.getGp1StickRightY() / slowFactor,
                        -Gamepads.getGp1StickRightX() / slowFactor,
                        -Gamepads.getGp1StickLeftX() / slowFactor
                )
        );
        drive.update();
    }

    public static void localize(boolean update) {
        // NormalizeImuAngle.setDrive(drive);
        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("x", poseEstimate.getX());
        telemetry.addData("y", poseEstimate.getY());
        telemetry.addData("heading", poseEstimate.getHeading());
        telemetry.addData("heading in degrees", Math.toDegrees(drive.getPoseEstimate().getHeading()));
        if (update) {
            telemetry.update();
        }
    }

    public static void slowMovement(int factor) {
        slowFactor = 1;
        if (Gamepads.slowMovement()) {
            slowFactor = factor;
        }
    }

}