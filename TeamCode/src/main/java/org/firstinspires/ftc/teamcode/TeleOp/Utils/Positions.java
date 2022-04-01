package org.firstinspires.ftc.teamcode.TeleOp.Utils;

public class Positions {

    ///---------------ARM----------------------------------
    //Arm is used to store the actual positions and BasePoseArm1 is used to generate Arm
    public static final double armCorrectionOffset = 0;//-200

    public static final int basePoseCollect = 1560; //1640

    public static final GenericPosition AutoArm = new GenericPosition(basePoseCollect - 1360, basePoseCollect - 1000, basePoseCollect - 200);

    public static final GenericPosition BasePoseArm1 = new GenericPosition(basePoseCollect - 1350, basePoseCollect - 1400, basePoseCollect - 30, basePoseCollect + 960, basePoseCollect + 900);

    public static final GenericPosition Arm = new GenericPosition(BasePoseArm1.Up + armCorrectionOffset + 150, BasePoseArm1.Mid + armCorrectionOffset, BasePoseArm1.Down + armCorrectionOffset, BasePoseArm1.Below + armCorrectionOffset, BasePoseArm1.Shared + armCorrectionOffset);
    ///-----------------------------------------------------

    ///---------------SLIDERS----------------------------------
    public static GenericPosition Sliders = new GenericPosition(570, 180, 0);
    ///-----------------------------------------------------

    ///---------------BOX----------------------------------
    public static final double basePoseShared = 0.2;
    public static GenericPosition Box = new GenericPosition(0, 0.1, 0.5, 0.4, basePoseShared);
    public static GenericPosition BoxAuto = new GenericPosition(0, 0.18, 0.5, 0.4, basePoseShared);
    public static GenericPosition PotentiometerBox = new GenericPosition(0.48, 0.5, 1.516, 1.4, 0.31);
    ///------------------------------------------------------

    ///---------------RULER----------------------------------
    public static double rulerBaseInit = 0.791; // 0.95
    public static double rulerAngleInit = 0.5;

}