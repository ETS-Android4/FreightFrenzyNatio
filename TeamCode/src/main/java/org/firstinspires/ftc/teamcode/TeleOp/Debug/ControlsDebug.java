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
        if (update) {
            Hardware.telemetry.update();
        }
    }
}
