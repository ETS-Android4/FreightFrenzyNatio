package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.TeleOp.Debug.ControlsDebug;
import org.firstinspires.ftc.teamcode.TeleOp.Debug.PositionsDebug;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.BoxAngle;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Gamepads;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Initializations;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.PoseStorageTeleOp;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Rumble;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.SafetyFeatures;

// dev init

@TeleOp(name = "MainTeleOp")
public class MAIN extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Initializations.TeleOpInitialization(drive, telemetry, hardwareMap);
        ControlsDebug.getControls(true);
        while (!isStarted()) {
            ((DcMotorEx) Hardware.arm).setVelocity(Arm.armPid.control((int) (Hardware.potentiometer.getVoltage() * 1000)), AngleUnit.RADIANS);
        }
        Rumble.timer.reset();
        Rumble.timer.startTime();
        SafetyFeatures.isOk = true;
        SafetyFeatures.isSensorOverriden = false;
        drive.setPoseEstimate(PoseStorage.startPosition);

        while (!isStopRequested() && opModeIsActive()) {
            Arm.checkUpOrBelow();

            if (!SafetyFeatures.isOk) {
                Hardware.telemetry.addData("Robotu si-a luat zilele", "");
                Hardware.telemetry.update();
                SafetyFeatures.setZeroPower();
                continue;
            }

            //telemetry.addData("pwm coefs low", ((ServoImplEx)Hardware.boxAngle).getPwmRange().usPulseLower);
            //telemetry.addData("pwm coefs up", ((ServoImplEx)Hardware.boxAngle).getPwmRange().usPulseUpper);
            telemetry.addData("boxposition", BoxAngle.getPositionBoxAngle());
            telemetry.addData("boxpositionMirror", BoxAngle.getPositionBoxAngleMirror());
            telemetry.addData("intakePower", Hardware.intake.getPower());
            telemetry.addData("intakePower state variable", Box.power);
            telemetry.addData("rulerPower", Hardware.rulerSpin.getPower());
            if (Gamepads.resetSlidersPosition()) {
                telemetry.addData("pulaaaaa", "");
            }
            telemetry.addData("armGoUpAfterCollectState",Arm.armGoUpAfterColect);
            telemetry.addData("back_right", Hardware.back_right.getCurrentPosition());
            telemetry.addData("back_left", Hardware.back_left.getCurrentPosition());


            telemetry.addData("front_left", Hardware.front_left.getCurrentPosition());
            telemetry.addData("front_right", Hardware.front_right.getCurrentPosition());
            telemetry.addData("ServoRulerBase", Hardware.rulerBase.getPosition());
            telemetry.addData("ServoRulerAngle", Hardware.rulerAngle.getPosition());
            //telemetry.addData("BOXANGLE!!!!!", Hardware.boxAngle.getPosition());

            telemetry.addData("TMPosition", PoseStorageTeleOp.TMPosition);
            telemetry.addData("RulerTargetAngle", PoseStorageTeleOp.rulerAngle);
            telemetry.addData("RulerTargetBase", PoseStorageTeleOp.rulerBase);

            telemetry.addData("BoxANGLE POTENTIOMETER", Hardware.boxPotentiometer.getVoltage());

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
