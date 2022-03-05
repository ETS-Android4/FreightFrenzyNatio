package org.firstinspires.ftc.teamcode.Utilities;

public class OneTapTimer {
    private long firstTime = 0;
    private int duration;
    private OneTap firstConditionTrue = new OneTap();
    private OneTap secondConditionTrue = new OneTap();
    public OneTapTimer(int durationMs){
        duration = durationMs;
    }

    public boolean onActionRun(boolean condition){
        if (firstConditionTrue.onPress(condition)){
            firstTime = System.currentTimeMillis();
        }
        return secondConditionTrue.onPress(System.currentTimeMillis() - firstTime > duration && condition);
    }
}
