package org.firstinspires.ftc.teamcode.TeleOp.Utils;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareUtils;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.TeleOp.Movement;

public class Initializations {

    public static void TeleOpInitialization(SampleMecanumDrive drive, Telemetry telemetry, HardwareMap hardwareMap) {
        Movement.setDrive(drive);
        Movement.setTelemetry(telemetry);
        Hardware.init(hardwareMap, telemetry);
        HardwareUtils.InitializePositions();
    }

    public static void AutoInitialization(Telemetry telemetry, HardwareMap hardwareMap) {
        Hardware.autoInit(hardwareMap, telemetry);
        HardwareUtils.initializePositionsAuto();
    }
}
