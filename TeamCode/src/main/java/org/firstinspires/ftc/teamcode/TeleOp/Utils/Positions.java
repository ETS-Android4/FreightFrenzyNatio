package org.firstinspires.ftc.teamcode.TeleOp.Utils;

public class Positions {
    public static final double armCorrectionOffset = -20;

    public static final int basePoseCollect = 1410;

    public static final GenericPosition BasePoseArm1 = new GenericPosition(basePoseCollect - 1240, basePoseCollect - 1000, basePoseCollect, basePoseCollect + 960, basePoseCollect + 700);

    public static GenericPosition Sliders = new GenericPosition(600, 280, 0);

    public static GenericPosition Arm = new GenericPosition(BasePoseArm1.Up + armCorrectionOffset + 150, BasePoseArm1.Mid + armCorrectionOffset, BasePoseArm1.Down + armCorrectionOffset, BasePoseArm1.Below + armCorrectionOffset, BasePoseArm1.Shared + armCorrectionOffset);
    /// +20 // + 150 // !!!!!!!! + 150 !!!!!!!!!!
    public static GenericPosition Box = new GenericPosition(0.07, 0.25, 0.68, 0.4, 0.29);

    public static void update() {
        GenericPosition Sliders = new GenericPosition(600, 280, 0);

        GenericPosition Arm = new GenericPosition(BasePoseArm1.Up + armCorrectionOffset, BasePoseArm1.Mid + armCorrectionOffset, BasePoseArm1.Down + armCorrectionOffset, BasePoseArm1.Below + armCorrectionOffset);

        GenericPosition Box = new GenericPosition(0, 0.16, 0.64, 0.4);
    }

}