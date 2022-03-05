package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;

public abstract class AutoCases {

    public abstract int getArmPosition();
    public abstract double getServoPosition();
    public abstract Pose2d getShippingHubPose();

    public void spinCarusel(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(Trajectories.CaruselTrajectory(drive.getPoseEstimate()));
        long firstTime = System.currentTimeMillis();
        while (System.currentTimeMillis()-firstTime<300){}
        AutoUtil.spinCarusel();
    }

    public void intake(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(Trajectories.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.WarehouseTrajectory2(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.IntakeTrajectory(drive.getPoseEstimate()));

    }

    public void place(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(Trajectories.ReturnBackTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.ReturnBackTrajectory2(drive.getPoseEstimate()));
    }

    public void goToShippingHub(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(Trajectories.ShippingHubTrajectory(drive.getPoseEstimate()));
    }

    public void park(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(Trajectories.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.ParkTrajectory(drive.getPoseEstimate()));
    }

    public void goToBarriers(SampleMecanumDriveCancelable drive){
        drive.followTrajectory(Trajectories.goToBarriers(drive.getPoseEstimate()));
    }

    public void goOver(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(Trajectories.goOverBarriers(drive.getPoseEstimate()));
    }

    public void goOverBack(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(Trajectories.returnOverBarriers(drive.getPoseEstimate()));
    }

}
