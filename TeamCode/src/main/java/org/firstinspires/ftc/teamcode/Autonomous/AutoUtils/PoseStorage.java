package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.Utilities.DelayedAction;

public class PoseStorage {
    public static AutoCase autoCase = AutoCase.FullRed;
    public static Pose2d startPosition =  PoseColorNormalizer.calculate(new Pose2d(-36, -60.5,Math.toRadians(90)));
    public static Vector2d cameraPosition = new Vector2d(0,0);
    public static int armPosition = (int) Positions.AutoArm.Down;
    public static double servoPosition = Positions.BoxAuto.Mid;
    public static final DelayedAction delayedActionGoUnder = new DelayedAction(500);
    public static final DelayedAction delayedActionReturnSliders = new DelayedAction(1500);
    public static boolean isIntakeTrajectory = false;
    public static String detectedCase = "";
}