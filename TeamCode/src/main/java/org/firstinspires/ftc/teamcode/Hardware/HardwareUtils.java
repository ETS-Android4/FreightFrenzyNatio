package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions.rulerAngleInit;
import static org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions.rulerBaseInit;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TeleOp.Arm;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;

@Config
public class HardwareUtils {

    public static double ArmVelKp = 25, ArmVelKi = 0.2, ArmVelKd = 6, ArmVelKf = 15;
    public static double ArmPositionKp = 0.2, ArmPositionKi = 0.0035, ArmPositionKd = 0.35, armMaxVelocity = 110;

    public static double CarouselKp = 0.1, CarouselKi = 0.01, CarouselKd = 0, CarouselKf = 1;

    public static double SliderKp = 1.2, SliderKi = 0.12, SliderKd = 0, SliderKf = 12;

    public static DcMotor getDc(String name) {
        return Hardware.hardwareMap.get(DcMotor.class, name);
    }

    public static AnalogInput getAnalogInput(String name) {
        return Hardware.hardwareMap.get(AnalogInput.class, name);
    }

    public static Servo getServo(String name) {
        return Hardware.hardwareMap.get(Servo.class, name);
    }

    public static CRServo getCRServo(String name) {
        return Hardware.hardwareMap.get(CRServo.class, name);
    }

    public static DistanceSensor getDistanceSensor(String name) {
        return Hardware.hardwareMap.get(DistanceSensor.class, name);
    }

    public static void changeDirection(DcMotor... dcMotors) {
        for (DcMotor dcMotor : dcMotors) {
            if (dcMotor.getDirection() == DcMotorSimple.Direction.FORWARD) {
                dcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                dcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            }
        }
    }

    public static void changeDirection(CRServo... servos) {
        for (CRServo servo : servos) {
            if (servo.getDirection() == DcMotorSimple.Direction.FORWARD) {
                servo.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                servo.setDirection(DcMotorSimple.Direction.FORWARD);
            }
        }
    }

    public static void RunningWithEncoders(DcMotor... dcMotors) {
        for (DcMotor dcMotor : dcMotors) {
            dcMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public static void RunningWithoutEncoders(DcMotor... dcMotors) {
        for (DcMotor dcMotor : dcMotors) {
            dcMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public static void ResetEncoders(DcMotor... dcMotors) {
        for (DcMotor dcMotor : dcMotors) {
            dcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public static void SetPIDCoefficients() {
        ((DcMotorEx) Hardware.slider_left).setVelocityPIDFCoefficients(SliderKp, SliderKi, SliderKd, SliderKf);
        ((DcMotorEx) Hardware.slider_right).setVelocityPIDFCoefficients(1.2, 0.12, 0, 12);

        ((DcMotorEx) Hardware.slider_left).setTargetPositionTolerance(10);
        ((DcMotorEx) Hardware.slider_right).setTargetPositionTolerance(10);

        ((DcMotorEx) Hardware.arm).setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(ArmVelKp, ArmVelKi, ArmVelKd, ArmVelKf));
        ((DcMotorEx) Hardware.carusel).setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(CarouselKp, CarouselKi, CarouselKd, CarouselKf));

    }

    public static void InitializePositions() {
        changeDirection(Hardware.slider_right);
        Hardware.slider_right.setTargetPosition(0);
        Hardware.slider_left.setTargetPosition(0);

        Arm.armPid.setTarget((int) Positions.Arm.Down);

        Hardware.slider_left.setPower(1);
        Hardware.slider_right.setPower(1);

        Hardware.boxAngle.setPosition(Positions.Box.Up);

        Hardware.rulerAngle.setPosition(rulerAngleInit);
        long currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime <= 1500) {
        }
        Hardware.rulerBase.setPosition(rulerBaseInit);
    }

    public static void initializePositionsAuto() {
        changeDirection(Hardware.slider_right);
        Hardware.slider_right.setTargetPosition(0);
        Hardware.slider_left.setTargetPosition(0);
        Hardware.slider_left.setPower(1);
        Hardware.slider_right.setPower(1);

        Hardware.boxAngle.setPosition(Positions.Box.Up);
        ((DcMotorEx) (Hardware.arm)).setVelocity(0);

        Hardware.rulerAngle.setPosition(rulerAngleInit);
        long currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime <= 1500) {
        }
        Hardware.rulerBase.setPosition(rulerBaseInit);
    }
}