package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.spartronics4915.lib.T265Camera;

public class IntelCameraWrapper {
    private T265Camera t265Camera = null;
    private final HardwareMap hardwareMap;

    public IntelCameraWrapper(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init() {
        if (t265Camera == null) {
            t265Camera = new T265Camera(new Transform2d(), 1, hardwareMap.appContext);
        }
    }

    public void start() {
        t265Camera.setPose(new Pose2d(PoseStorage.startPosition.getX()*0.0254, PoseStorage.startPosition.getY()*0.0254, new Rotation2d(PoseStorage.startPosition.getHeading())));
        t265Camera.start();
    }

    public void stop() {
        t265Camera.stop();
    }

    public Vector2d getCameraPosition() {
        T265Camera.CameraUpdate up = t265Camera.getLastReceivedCameraUpdate();
        // We divide by 0.0254 to convert meters to inches
        Translation2d translation = new Translation2d(up.pose.getTranslation().getX() / 0.0254, up.pose.getTranslation().getY() / 0.0254);

        return new Vector2d(translation.getX(), translation.getY());
    }
}
