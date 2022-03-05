package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoRun;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoUtil;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.TestAuto;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.TestAutoRun;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Debug.ControlsDebug;
import org.firstinspires.ftc.teamcode.TeleOp.Debug.PositionsDebug;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Initializations;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Rumble;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.SafetyFeatures;


@TeleOp(name = "MainTeleOp")
public class MAIN extends LinearOpMode {

    // dev init

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Initializations.TeleOpInitialization(drive, telemetry, hardwareMap);
        ControlsDebug.getControls(true);
        while (!isStarted()){
            ((DcMotorEx)Hardware.arm).setVelocity(Arm.armPid.control((int)(Hardware.potentiometer.getVoltage()*1000)), AngleUnit.RADIANS);
        }
        Rumble.timer.reset();
        Rumble.timer.startTime();
        SafetyFeatures.isOk = true;
        SafetyFeatures.isSensorOverriden = false;
        while (!isStopRequested() && opModeIsActive()) {
            if (!SafetyFeatures.isOk){
                Hardware.telemetry.addData("Robotu si-a luat zilele", "");
                Hardware.telemetry.update();
                SafetyFeatures.setZeroPower();
                continue;
            }
            if (Gamepads.resetSlidersPosition()){
                telemetry.addData("pulaaaaa","");
            }
            telemetry.addData("back_right",Hardware.back_right.getCurrentPosition());
            telemetry.addData("back_left",Hardware.back_left.getCurrentPosition());


            telemetry.addData("front_left",Hardware.front_left.getCurrentPosition());
            telemetry.addData("front_right",Hardware.front_right.getCurrentPosition());
            telemetry.addData("ServoRulerBase", Hardware.rulerBase.getPosition());
            telemetry.addData("ServoRulerAngle", Hardware.rulerAngle.getPosition());
            telemetry.addData("BOXANGLE!!!!!", Hardware.boxAngle.getPosition());

            telemetry.addData("TMPosition", AutoRun.TMPosition);
            telemetry.addData("RulerTargetAngle", AutoRun.rulerAngle);
            telemetry.addData("RulerTargetBase", AutoRun.rulerBase);

            PositionsDebug.GetCaruselInfo(false);
            Gamepads.update(gamepad1, gamepad2);
            Carousel.run();
            Movement.driving();
            Sliders.toPosition();
            Arm.toPosition();
            Box.toPosition();
            Box.intake();
            if (!SafetyFeatures.isSensorOverriden)
                IntakeSensor.detectTeleOp();
            Ruler.controlRulerDpad(this);
            Rumble.rumble(gamepad1, gamepad2);
            SafetyFeatures.safety();
            AdjustArmManual.adjust();
            ControlsDebug.getControls(false);
            PositionsDebug.GetBoxPosition();
            PositionsDebug.GetArmPosition();
            PositionsDebug.GetSensors();
            PositionsDebug.GetSlidersPosition(true);
        }
    }
}
