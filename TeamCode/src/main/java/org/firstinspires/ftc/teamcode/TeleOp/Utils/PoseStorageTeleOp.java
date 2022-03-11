package org.firstinspires.ftc.teamcode.TeleOp.Utils;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;

public class PoseStorageTeleOp {
    public static int TMPosition;
    public static double rulerAngle;
    public static double rulerBase;

    public static double rulerAnglePlace;
    public static double rulerBasePlace;

    public static void setRulerPositions(int TMPosition) {
        //TODO: POSITIONS FOR BLUE CASE
        if (PoseColorNormalizer.getColorCase()== PoseColorNormalizer.Color.RED) {
            if (TMPosition == 1) {
                rulerBase = 0.52;
                rulerAngle = 0.72;
            } else if (TMPosition == 2) {
                rulerBase = 0.446;
                rulerAngle = 0.693;
            } else {
                rulerBase = 0.40;
                rulerAngle = 0.661;
            }
            rulerAnglePlace = 0.02;
            rulerBasePlace = 0.42;
        }


    }

}
