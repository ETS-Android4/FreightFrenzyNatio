package org.firstinspires.ftc.teamcode.TeleOp.Utils;

public class GenericPosition {
    public GenericPosition(double up, double mid, double down) {
        Up = up;
        Mid = mid;
        Down = down;
    }
    public GenericPosition(double up, double mid, double down, double below) {
        Up = up;
        Mid = mid;
        Down = down;
        Below = below;
    }

    public GenericPosition(double up, double mid, double down, double below, double shared) {
        Up = up;
        Mid = mid;
        Down = down;
        Below = below;
        Shared = shared;
    }

    public double Up;
    public double Mid;
    public double Down;
    public double Below;
    public double Shared;

}

