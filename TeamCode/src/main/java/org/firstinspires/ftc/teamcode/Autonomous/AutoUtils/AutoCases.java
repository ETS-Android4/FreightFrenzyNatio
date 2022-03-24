package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;


import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

public abstract class AutoCases {

    public abstract int getArmPosition();

    public abstract double getServoPosition();

    public abstract Pose2d getShippingHubPose();

    public abstract Pose2d getShippingHubWarehouseSidePose();

    public abstract Pose2d getShippingHubCaruselSidePose();


}
