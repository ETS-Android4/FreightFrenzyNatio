package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class A extends AutoCases {
    public int armPosition = (int)Positions.AutoArm.Down + 30;
    public double servoPosition = Positions.Box.Up;
    public Pose2d shippingHubPose = new Pose2d(-11, -43.8, java.lang.Math.toRadians(90));

    @Override
    public int getArmPosition (){
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
