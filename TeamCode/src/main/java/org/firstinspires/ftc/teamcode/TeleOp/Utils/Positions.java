package org.firstinspires.ftc.teamcode.TeleOp.Utils;

public class Positions {

    ///---------------ARM----------------------------------
    //Arm is used to store the actual positions and BasePoseArm1 is used to generate Arm
    public static final double armCorrectionOffset = -20;

    public static final int basePoseCollect = 1410;

    public static final GenericPosition BasePoseArm1 = new GenericPosition(basePoseCollect - 1240, basePoseCollect - 1000, basePoseCollect, basePoseCollect + 960, basePoseCollect + 700);

    public static GenericPosition Arm = new GenericPosition(BasePoseArm1.Up + armCorrectionOffset + 150, BasePoseArm1.Mid + armCorrectionOffset, BasePoseArm1.Down + armCorrectionOffset, BasePoseArm1.Below + armCorrectionOffset, BasePoseArm1.Shared + armCorrectionOffset);
    ///-----------------------------------------------------

    ///---------------SLIDERS----------------------------------
    public static GenericPosition Sliders = new GenericPosition(600, 280, 0);
    ///-----------------------------------------------------

    ///---------------BOX----------------------------------
    public static GenericPosition Box = new GenericPosition(0.07, 0.25, 0.68, 0.4, 0.29);
    ///-----------------------------------------------------

    ///---------------RULER----------------------------------
    public static double rulerBaseInit = 0.95;
    public static double rulerAngleInit = 0.5;

}