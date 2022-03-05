package org.firstinspires.ftc.teamcode.TeleOp.Utils;

import android.media.FaceDetector;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Gamepads {
    // ------------ GAMEPAD 1 ------------
    private static boolean gp1A = false;
    private static boolean gp1B = false;
    private static boolean gp1X = false;
    private static boolean gp1Y = false;
    private static boolean gp1DpUp = false;
    private static boolean gp1DpDown = false;
    private static boolean gp1DpLeft = false;
    private static boolean gp1DpRight = false;
    private static boolean gp1BmpRight = false;
    private static boolean gp1BmpLeft = false;
    private static double gp1TrgRight = 0.0;
    private static double gp1TrgLeft = 0.0;
    private static boolean gp1Start = false;
    private static boolean gp1Back = false;
    private static double gp1StickRightY = 0.0;
    private static double gp1StickRightX = 0.0;
    private static double gp1StickLeftX = 0.0;
    private static double gp1StickLeftY = 0.0;
    private static boolean gp1RightStickDown = false;
    private static boolean gp1LeftStickDown = false;
    private static boolean gp1Touchpad = false;
    // ------------ GAMEPAD 2 ------------
    private static boolean gp2A = false;
    private static boolean gp2B = false;
    private static boolean gp2X = false;
    private static boolean gp2Y = false;
    private static boolean gp2DpUp = false;
    private static boolean gp2DpDown = false;
    private static boolean gp2DpLeft = false;
    private static boolean gp2DpRight = false;
    private static boolean gp2BmpRight = false;
    private static boolean gp2BmpLeft = false;
    private static double gp2TrgRight = 0.0;
    private static double gp2TrgLeft = 0.0;
    private static boolean gp2Start = false;
    private static boolean gp2Back = false;
    private static double gp2StickRightY = 0.0;
    private static double gp2StickRightX = 0.0;
    private static double gp2StickLeftX = 0.0;
    private static double gp2StickLeftY = 0.0;
    private static boolean gp2RightStickDown = false;
    private static boolean gp2LeftStickDown = false;
    private static boolean gp2Touchpad = false;


    public static void update(Gamepad gamepad1, Gamepad gamepad2) {
        gp1A = gamepad1.a;
        gp1B = gamepad1.b;
        gp1X = gamepad1.x;
        gp1Y = gamepad1.y;
        gp1DpUp = gamepad1.dpad_up;
        gp1DpDown = gamepad1.dpad_down;
        gp1DpRight = gamepad1.dpad_right;
        gp1DpLeft = gamepad1.dpad_left;
        gp1BmpLeft = gamepad1.left_bumper;
        gp1BmpRight = gamepad1.right_bumper;
        gp1TrgLeft = gamepad1.left_trigger;
        gp1TrgRight = gamepad1.right_trigger;
        gp1Start = gamepad1.start;
        gp1Back = gamepad1.back;
        gp1StickRightX = gamepad1.right_stick_x;
        gp1StickRightY = gamepad1.right_stick_y;
        gp1StickLeftX = gamepad1.left_stick_x;
        gp1StickLeftY = gamepad1.left_stick_y;
        gp1RightStickDown = gamepad1.right_stick_button;
        gp1LeftStickDown = gamepad1.left_stick_button;
        gp1Touchpad = gamepad1.touchpad;

        gp2A = gamepad2.a;
        gp2B = gamepad2.b;
        gp2X = gamepad2.x;
        gp2Y = gamepad2.y;
        gp2DpUp = gamepad2.dpad_up;
        gp2DpDown = gamepad2.dpad_down;
        gp2DpRight = gamepad2.dpad_right;
        gp2DpLeft = gamepad2.dpad_left;
        gp2BmpLeft = gamepad2.left_bumper;
        gp2BmpRight = gamepad2.right_bumper;
        gp2TrgLeft = gamepad2.left_trigger;
        gp2TrgRight = gamepad2.right_trigger;
        gp2Start = gamepad2.start;
        gp2Back = gamepad2.back;
        gp2StickRightX = gamepad2.right_stick_x;
        gp2StickRightY = gamepad2.right_stick_y;
        gp2StickLeftX = gamepad2.left_stick_x;
        gp2StickLeftY = gamepad2.left_stick_y;
        gp2RightStickDown = gamepad2.right_stick_button;
        gp2LeftStickDown = gamepad2.left_stick_button;
        gp2Touchpad = gamepad2.touchpad;
    }

    public static boolean slidersUp() {
        return false;
    }

    public static boolean slidersMid() {
        return false;
    }

    public static boolean slidersDown() {
        return false;
    }

    public static boolean armUp() {
        return gp1Y;
    }

    public static boolean armShared() {return gp1B && !gp1Start;}

    public static boolean armDown() {
        return gp1A && !gp1Start;
    }

    public static boolean armBelow() {
        return gp1X;
    }

    public static boolean boxUp() {
        return gp2X || gp1DpDown;
    }

    public static boolean boxMid() {
        return gp2A && !gp2Start;
    }

    public static boolean boxDown() {
        return gp2B && !gp2Start;
    }

    public static boolean boxIntake() {
        return gp2Y;
    }

    public static boolean boxOutake() {
        return gp2BmpLeft;
    }

    public static boolean carusel() {
        return gp1DpLeft;
    }

    public static boolean slowMovement() {
        return gp1TrgRight > 0.01;
    }

    public static double getGp1StickRightY() {
        return gp1StickRightY;
    }

    public static double getGp1StickRightX() {
        return gp1StickRightX;
    }

    public static double getGp1StickLeftX() {
        return gp1StickLeftX;
    }

    public static double getGp1StickLeftY() {
        return gp1StickLeftY;
    }

    public static boolean rulerBaseLeft() {
        return gp2DpLeft;
    }

    public static boolean rulerBaseRight() {
        return gp2DpRight;
    }

    public static boolean rulerAngleUp() {
        return gp2DpUp;

    }

    public static boolean rulerAngleRight() {
        return gp2DpDown;

    }

    public static double rulerExtend() {
        return gp2TrgLeft;
    }

    public static double rulerRetract() {
        return gp2TrgRight;

    }

    public static boolean deliverTM() {
        return gp2BmpRight;
    }

    public static boolean rulerSlow() {
        return false; //return gp2rightBmp;
    }

    public static double joystickXRulerControl(){
        return gp2StickLeftX;
    }
    public static double joystickYRulerControl(){
        return gp2StickLeftY;
    }

    public static boolean manualIncrement(){
        return gp1BmpLeft;
    }
    public static boolean manualDecrement(){
        return gp1BmpRight;
    }
    public static boolean overrideDistanceSensor(){
        return gp1Back;
    }

    public static boolean resetSlidersPosition(){
        return gp2Back;
    }

    public static boolean rulerAngleColectPose(){
        return gp2LeftStickDown;
    }
    public static boolean rulerAngleScorePose(){
        return gp2RightStickDown;
    }
    public static boolean reInitRuler(){
        return gp2Touchpad;
    }

    public static boolean boxSharedAdjustPlus(){return gp1RightStickDown;}
    public static boolean boxSharedAdjustMinus(){return gp1LeftStickDown;}
}

