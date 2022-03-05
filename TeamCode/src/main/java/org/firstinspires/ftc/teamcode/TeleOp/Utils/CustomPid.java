package org.firstinspires.ftc.teamcode.TeleOp.Utils;

import android.widget.TableRow;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;

public class CustomPid {
    private double kp;
    private double ki;
    private double kd;
    private int targetPosition;
    private int integralSum = 0;
    private long lastTime;
    private int lastError = 0;
    public double targetVelocity = 0;
    private final double maxVelocity;
    private final double admittedError = 20;
    private final int integralSumLimit = 1500;
    private final int integralLimit = 55;
    private final int minError = 200;//200
    private final int maxVelocityError = 10; //10
    private double initialError;

    public CustomPid(double _kp, double _ki, double _kd, double _maxVelocity) {
        kp = _kp;
        ki = _ki;
        kd = _kd;
        maxVelocity = _maxVelocity;
        targetPosition = 0;
        lastTime = System.currentTimeMillis();
    }

    public void setTarget(int _targetPosition) {
        targetPosition = _targetPosition;
         initialError = targetPosition - (int) (Hardware.potentiometer.getVoltage() * 1000);
        integralSum = 0;
        lastError = 0;
    }

    public double control(int currentPosition) {
        int err = targetPosition - currentPosition;
        int dt = (int) (System.currentTimeMillis() - lastTime);
        if (Math.abs(err) <= integralLimit && Math.abs(err) >= admittedError) {
            integralSum += err;
        }
        Hardware.telemetry.addData("ERROR!!!!!!!!: ", err);
        Hardware.telemetry.addData("INTEGRAL SUM!!!!!!!!: ", integralSum);
        int proportional = err;
        int integral = integralSum * dt;
        if (integral > integralSumLimit)
            integral = integralSumLimit;
        if (integral < -integralSumLimit)
            integral = -integralSumLimit;

        int derivative = (err - lastError) / dt;
        lastError = err;
        lastTime = System.currentTimeMillis();
        Hardware.telemetry.addData("velocity:", proportional * kp + integral * ki + derivative * kd);
        targetVelocity = proportional * kp + integral * ki + derivative * kd;
        targetVelocity = normalizeVelocity(targetVelocity);

        if (Math.abs(err) < admittedError) {
            targetVelocity = 0;
            integralSum = 0;
        }
        if (Math.abs(initialError) > 2 * minError) {
            if (err < minError && err > 0 && targetVelocity > maxVelocityError) {
                targetVelocity = maxVelocityError;
            } else if (err > -minError && err < 0 && targetVelocity < -maxVelocityError) {
                targetVelocity = -maxVelocityError;

            }
        }
        return targetVelocity;
    }

    public void changePIDCoef(double _kp, double _ki, double _kd) {
        kp = _kp;
        ki = _ki;
        kd = _kd;
    }

    private double normalizeVelocity(double velocity) {
        if (velocity > maxVelocity) {
            return maxVelocity;
        }
        if (velocity < -maxVelocity) {
            return -maxVelocity;
        }
        return velocity;
    }

    public double getKp() {
        return kp;
    }

    public double getKi() {
        return ki;
    }

    public double getKd() {
        return kd;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

}