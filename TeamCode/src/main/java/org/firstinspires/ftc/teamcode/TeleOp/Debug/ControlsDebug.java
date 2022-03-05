package org.firstinspires.ftc.teamcode.TeleOp.Debug;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class ControlsDebug {
    ///----------------GAMEPAD1----------------
    private static final String glisiereSus = "DpUp";
    private static final String glisiereMid = "DpRight";
    private static final String glisiereJos = "DpDown";
    private static final String bratBelow = "X";
    private static final String bratColect = "A";
    private static final String bratMid = "B";
    private static final String bratSus = "Y";
    private static final String mersIncet = "TriggerRight";
    private static final String carusel = "LBumper";
    ///----------------GAMEPAD2----------------
    private static final String cutieColect = "A";
    private static final String cutieSus = "X";
    private static final String cutieJos = "B";
    private static final String intakeToggle = "Y";
    private static final String outtake = "LBumper";
    private static final String rulerLateralLeft = "DpLeft";
    private static final String rulerLateralRight = "DpRight";
    private static final String rulerLongitudinalUp = "DpUp";
    private static final String rulerLongitudinalDown = "DpDown";
    private static final String rulerExtend = "TriggerRight";
    private static final String rulerRetract = "TriggerLeft";

    public static void getControls(boolean update) {
        Hardware.telemetry.addData("---------GAMEPAD1---------", "");
        Hardware.telemetry.addData("glisiereSus", glisiereSus);
        Hardware.telemetry.addData("glisiereMid", glisiereMid);
        Hardware.telemetry.addData("glisiereJos", glisiereJos);
        Hardware.telemetry.addData("bratColect", bratColect);
        Hardware.telemetry.addData("bratMid", bratMid);
        Hardware.telemetry.addData("bratSus", bratSus);
        Hardware.telemetry.addData("bratBelow", bratBelow);
        Hardware.telemetry.addData("mersIncet", mersIncet);
        Hardware.telemetry.addData("carusel", carusel);
        Hardware.telemetry.addData("---------GAMEPAD2---------", "");
        Hardware.telemetry.addData("cutieColect", cutieColect);
        Hardware.telemetry.addData("cutieSus", cutieSus);
        Hardware.telemetry.addData("cutieJos", cutieJos);
        Hardware.telemetry.addData("intakeToggle", intakeToggle);
        Hardware.telemetry.addData("outtake", outtake);
        Hardware.telemetry.addData("rulerLateralLeft", rulerLateralLeft);
        Hardware.telemetry.addData("rulerLateralRight", rulerLateralRight);
        Hardware.telemetry.addData("rulerLongitudinalUp", rulerLongitudinalUp);
        Hardware.telemetry.addData("rulerLongitudinalDown", rulerLongitudinalDown);
        Hardware.telemetry.addData("rulerExtend", rulerExtend);
        Hardware.telemetry.addData("rulerRetract", rulerRetract);
        if (update) {
            Hardware.telemetry.update();
        }
    }
}
