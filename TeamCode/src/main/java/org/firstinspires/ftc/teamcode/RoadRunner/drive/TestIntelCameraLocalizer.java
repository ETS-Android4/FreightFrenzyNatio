package org.firstinspires.ftc.teamcode.RoadRunner.drive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.ChassisSpeeds;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.spartronics4915.lib.T265Camera;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class TestIntelCameraLocalizer implements Localizer { //TODO: asta

    T265Camera camera;
    T265Camera.CameraUpdate cameraUpdate;

    public TestIntelCameraLocalizer(HardwareMap hardwareMap) {
        if (camera == null) {
            camera = new T265Camera(new Transform2d(), 1, hardwareMap.appContext);
        }
        camera.start();
    }

    @NonNull
    @Override
    public Pose2d getPoseEstimate() {
        cameraUpdate = camera.getLastReceivedCameraUpdate();

        // We divide by 0.0254 to convert meters to inches
        Translation2d translation = new Translation2d(cameraUpdate.pose.getTranslation().getX() / 0.0254, cameraUpdate.pose.getTranslation().getY() / 0.0254);
        Rotation2d rotation = cameraUpdate.pose.getRotation();
        return new Pose2d(translation.getX(), translation.getY(), rotation.getRadians()); ///e posibil sa nu se stie
    }

    @Override
    public void setPoseEstimate(@NonNull Pose2d pose2d) {

    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        ChassisSpeeds velocity = cameraUpdate.velocity;
        return new Pose2d(velocity.vxMetersPerSecond / .0254, velocity.vyMetersPerSecond / .0254, velocity.omegaRadiansPerSecond);
    }

    @Override
    public void update() {
        cameraUpdate = camera.getLastReceivedCameraUpdate();
    }
}
