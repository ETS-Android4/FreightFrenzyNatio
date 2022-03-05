package org.firstinspires.ftc.teamcode.Autonomous.AutoUtils;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareUtils;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.CustomPid;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.Initializations;
import org.firstinspires.ftc.teamcode.TeleOp.Utils.SafetyFeatures;

@Autonomous(name = "TestAuto")
public class TestAuto extends LinearOpMode {
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

        Thread linearAuto = new Thread(new TestAutoRun(sampleMecanumDrive, this));

        IntelCameraWrapper cameraWrapper = new IntelCameraWrapper(hardwareMap);
        cameraWrapper.init();

        waitForStart();
        cameraWrapper.start();

        linearAuto.start();

        SafetyFeatures.isOk = true;

        while (!isStopRequested() && opModeIsActive()) {
            PoseStorage.cameraPosition = cameraWrapper.getCameraPosition();
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
        }
        linearAuto.interrupt();
        cameraWrapper.stop();
    }
}
