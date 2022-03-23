package org.firstinspires.ftc.teamcode.TeleOp;


import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class Movement {

    private static SampleMecanumDrive drive;
    private static Telemetry telemetry;
    public static double slowFactor = 1;

    public static void setDrive(SampleMecanumDrive drive) {
        Movement.drive = drive;
    }

    public static void setTelemetry(Telemetry telemetry) {
        Movement.telemetry = telemetry;
    }

    public static void driving() {
        slowMovement(2.5);
//        if (Hardware.potentiometer.getVoltage() * 1000 < Positions.Arm.Up + 300) {
//            if (Math.abs(Hardware.imu.getAngularOrientation().secondAngle) > 4) { // FU 3 DAR NU II PLACE LUI RADU NEGRI GABITZU
//                Arm.armPid.setTarget((int)Positions.Arm.Down - 300);
//                drive.setWeightedDrivePower(new Pose2d(-1, 0, 0));
//                drive.update();
//                return;
//            }
//        } else {
//            if (Math.abs(Hardware.imu.getAngularOrientation().secondAngle) > 15) {
//                drive.setWeightedDrivePower(new Pose2d(-1, 0, 0));
//                drive.update();
//                return;
//            }
//        }
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

    public static void slowMovement(double factor) {
        slowFactor = 1;
        if (Gamepads.slowMovement()) {
            slowFactor = factor;
        }
    }

}