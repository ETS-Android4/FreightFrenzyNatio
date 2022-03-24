package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public class A extends AutoCases {
    public int armPosition = (int)Positions.AutoArm.Down + 30;
    public double servoPosition = Positions.BoxAuto.Up;
    public Pose2d shippingHubPose = new Pose2d(-13.5, -39, java.lang.Math.toRadians(90));
    public Pose2d shippingHubWarehouseSidePose = new Pose2d(-5, -40.5, Math.toRadians(110));
    public Pose2d shippingHubCaruselSidePose = new Pose2d(-17, -40.1, java.lang.Math.toRadians(70));

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

    @Override
    public Pose2d getShippingHubWarehouseSidePose() {
        return  PoseColorNormalizer.calculate(shippingHubWarehouseSidePose);
    }

    @Override
    public Pose2d getShippingHubCaruselSidePose() {
        return PoseColorNormalizer.calculate(shippingHubCaruselSidePose);
    }

}
