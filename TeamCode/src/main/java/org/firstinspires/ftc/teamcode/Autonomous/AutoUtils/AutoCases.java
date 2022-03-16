package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import static org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.Trajectories.CollectDuckTrajectory;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

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

    public void spinCaruselWithDuckCollect(SampleMecanumDriveCancelable drive) {
        drive.followTrajectorySequence(Trajectories.CaruselTrajectoryWithDuckCollect1(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.CaruselTrajectoryWithDuckCollect2(drive.getPoseEstimate()));
        PoseStorage.armPosition=(int)Positions.Arm.Down;
        Hardware.boxAngle.setPosition(Positions.Box.Up);
        long firstTime = System.currentTimeMillis();
        while (System.currentTimeMillis()-firstTime<300){}
        AutoUtil.spinCarusel();
    }

    public void spinCaruselDuckCollect(SampleMecanumDriveCancelable drive) {
        drive.followTrajectory(Trajectories.CaruselTrajectory(drive.getPoseEstimate()));
        PoseStorage.armPosition=(int)Positions.Arm.Down;
        long firstTime = System.currentTimeMillis();
        while (System.currentTimeMillis()-firstTime<300){}
        AutoUtil.spinCaruselDuckColect();
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

    public void sharedPark(SampleMecanumDriveCancelable drive){
        drive.followTrajectory(Trajectories.WarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.ParkTrajectory(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.sharedWarehouseTrajectory1(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.sharedWarehouseTrajectory2(drive.getPoseEstimate()));
    }

    public void goToShippingHubCaruselSide(SampleMecanumDriveCancelable drive){
        drive.followTrajectory(Trajectories.ShippingHubTrajectoryCaruselSide(drive.getPoseEstimate()));
    }

    public void collectDuck(SampleMecanumDriveCancelable drive){
        drive.followTrajectory(Trajectories.CollectDuckIntermediatyTrajectory(drive.getPoseEstimate()));
        drive.followTrajectorySequence(CollectDuckTrajectory(drive.getPoseEstimate()));
    }
    public void placeDuck(SampleMecanumDriveCancelable drive){
        drive.followTrajectory(Trajectories.PlaceDuckTrajectory(drive.getPoseEstimate()));
    }

    public void parkAfterDuck(SampleMecanumDriveCancelable drive){
        drive.followTrajectory(Trajectories.GoOverBarriers1(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.GoOverBarriers2(drive.getPoseEstimate()));
        drive.followTrajectory(Trajectories.sharedWarehouseTrajectory2(drive.getPoseEstimate()));
    }

    public void goToShippingHubWarehouseSide(SampleMecanumDriveCancelable drive){
        drive.followTrajectory(Trajectories.ShippingHubTrajectoryWarehouseSide(drive.getPoseEstimate()));
    }

}
