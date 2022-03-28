package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Autonomous.AutoRun.WarehouseSide.cycles3.WarehouseSideTrajectories3;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCase;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCases;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.ImageDetection;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseColorNormalizer;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareUtils;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.CustomPid;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Initializations;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.SafetyFeatures;

@Autonomous(name = "Main Auto")
public class MainAuto extends LinearOpMode {
    public static long firstTime;
    public static double duration = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDriveCancelable sampleMecanumDrive;
        if(PoseStorage.autoCase==AutoCase.Warehouse4Blue||PoseStorage.autoCase==AutoCase.Warehouse4Red){
            sampleMecanumDrive = new SampleMecanumDriveCancelable(hardwareMap,0.01);
        }else{
            sampleMecanumDrive = new SampleMecanumDriveCancelable(hardwareMap);
        }

        if (PoseStorage.autoCase.name().contains("Red")) {
            PoseColorNormalizer.setColorCase(PoseColorNormalizer.Color.RED);
        } else {
            PoseColorNormalizer.setColorCase(PoseColorNormalizer.Color.BLUE);
        }

        Initializations.AutoInitialization(telemetry, hardwareMap);

        ImageDetection.initialize();
        CustomPid armPid = new CustomPid(HardwareUtils.ArmPositionKp, HardwareUtils.ArmPositionKi, HardwareUtils.ArmPositionKd, HardwareUtils.armMaxVelocity);

        Thread linearAuto = new Thread(SelectAuto.getAutoFromEnum(sampleMecanumDrive, this));


        waitForStart();
        sampleMecanumDrive.setPoseEstimate(PoseStorage.startPosition);

        linearAuto.start();
        firstTime = System.currentTimeMillis();

        SafetyFeatures.isOk = true;

        while (!isStopRequested() && opModeIsActive()) {
            if (!SafetyFeatures.isOk) {
                linearAuto.interrupt();
                SafetyFeatures.setZeroPower();
                continue;
            }

            if (!linearAuto.isAlive()) {
                if (duration == 0) {
                    duration = (System.currentTimeMillis() - firstTime) / (1000.0);
                }
                Hardware.telemetry.addData("----AUTO LASTED----", duration);
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
