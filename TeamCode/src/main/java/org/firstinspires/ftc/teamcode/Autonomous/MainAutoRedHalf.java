package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoRun;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoRunHalf;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.ImageDetection;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.TestAutoRun;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.Trajectories;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareUtils;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.IntakeSensor;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.CustomPid;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Initializations;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Positions;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.SafetyFeatures;

@Autonomous(name = "Main Auto Red Half")
public class MainAutoRedHalf extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveCancelable sampleMecanumDrive = new SampleMecanumDriveCancelable(hardwareMap);
        Initializations.AutoInitialization(telemetry, hardwareMap);
        PoseColorNormalizer.setColorCase(PoseColorNormalizer.Color.RED);
        Trajectories.InitTrajectories();
        sampleMecanumDrive.setPoseEstimate(PoseStorage.startPosition);
        Trajectories.setDrive(sampleMecanumDrive);
        ImageDetection.initialize();
        CustomPid armPid = new CustomPid(HardwareUtils.ArmPositionKp, HardwareUtils.ArmPositionKi, HardwareUtils.ArmPositionKd, HardwareUtils.armMaxVelocity);

        Thread linearAuto = new Thread(new AutoRunHalf(sampleMecanumDrive, this));

        waitForStart();

        linearAuto.start();

        SafetyFeatures.isOk = true;

        while (!isStopRequested() && opModeIsActive()) {
            if (!SafetyFeatures.isOk) {
                linearAuto.interrupt();
                SafetyFeatures.setZeroPower();
                continue;
            }
            SafetyFeatures.check();
            armPid.setTarget(PoseStorage.armPosition);
            ((DcMotorEx) Hardware.arm).setVelocity(armPid.control((int) (Hardware.potentiometer.getVoltage() * 1000)), AngleUnit.RADIANS);
            Hardware.telemetry.addData("PID target Pos", armPid.targetVelocity);
            Hardware.telemetry.addData("PID current Pos", ((DcMotorEx) Hardware.arm).getVelocity(AngleUnit.RADIANS));
            Hardware.telemetry.addData("DetectedCase", PoseStorage.detectedCase);
            Hardware.telemetry.update();
//            if (PoseStorage.delayedActionGoUnder.runAction()) {
//                PoseStorage.armPosition = (int) Positions.BasePoseArm1.Below - 150;
//            }
//            if (IntakeSensor.detectAuto() && PoseStorage.isIntakeTrajectory) {
//                sampleMecanumDrive.breakFollowing();
//            }
//            if (PoseStorage.delayedActionReturnSliders.runAction()) {
//                Hardware.intake.setPower(-1);
//                Hardware.slider_left.setTargetPosition((int) Positions.Sliders.Down);
//                Hardware.slider_right.setTargetPosition((int) Positions.Sliders.Down);
//            }
        }
        linearAuto.interrupt();
    }
}
