package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class B extends AutoCases {
    public int armPosition = (int) Positions.AutoArm.Down - 360;
    public double servoPosition = Positions.Box.Mid + 0.05;
    public Pose2d shippingHubPose = new Pose2d(-13.5, -45, java.lang.Math.toRadians(90));

    @Override
    public int getArmPosition() {
        return armPosition;
    }

    @Override
    public double getServoPosition() {
        return servoPosition;
    }

    @Override
    public Pose2d getShippingHubPose() {
        return PoseColorNormalizer.calculate(shippingHubPose);
    }

}
