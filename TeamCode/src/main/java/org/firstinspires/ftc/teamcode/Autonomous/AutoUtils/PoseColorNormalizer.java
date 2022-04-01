package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class PoseColorNormalizer {
    public enum Color {
        RED,
        BLUE
    }

    private static Color color;

    public static Pose2d calculate(Pose2d pose2d) {
        if (color == Color.BLUE) {
            return new Pose2d(pose2d.getX(), -pose2d.getY(), 2 * Math.PI - pose2d.getHeading());
        }
        return pose2d;
    }

    public static double calculateAngleDegrees(double angleDeg) {
        if (color == Color.BLUE) {
            return 360 - angleDeg;
        }
        return angleDeg;
    }

    public static double calculateTurnAngleDegrees(double angleDeg) {
        if (color == Color.BLUE) {
            return  - angleDeg;
        }
        return angleDeg;
    }

    public static void setColorCase(Color _color) {
        color = _color;
    }

    public static Color getColorCase() {
        return color;
    }
}
