package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.AutoCase;
import org.firstinspires.ftc.teamcode.Autonomous.AutoRun.AutoRun;
import org.firstinspires.ftc.teamcode.Autonomous.AutoRun.AutoRunCollectDuck;
import org.firstinspires.ftc.teamcode.Autonomous.AutoRun.AutoRunHalf;
import org.firstinspires.ftc.teamcode.Autonomous.AutoRun.AutoRunWarehouseSide;
import org.firstinspires.ftc.teamcode.Autonomous.AutoUtils.PoseStorage;
import org.firstinspires.ftc.teamcode.RoadRunner.drive.advanced.SampleMecanumDriveCancelable;
import org.firstinspires.ftc.teamcode.Utilities.OneTap;

@Autonomous(name = "!!SELECT AUTO!!")
public class SelectAuto extends LinearOpMode {

    OneTap oneTapUp = new OneTap();
    OneTap oneTapDown = new OneTap();

    public void selectedCaseDisplay(AutoCase autoCase) {
        if (autoCase == PoseStorage.autoCase) {
            this.telemetry.addLine("*****" + PoseStorage.autoCase.name());
        } else {
            this.telemetry.addLine(autoCase.name());
        }
    }

    public AutoCase getCaseFromOrdinal(int ordinal) {
        for (AutoCase autoCase : AutoCase.values()) {
            if (autoCase.ordinal() == ordinal) {
                return autoCase;
            }
        }
        return AutoCase.FullRed;
    }

    public void changeCase() {
        int increment = 0;
        if (oneTapUp.onPress(this.gamepad1.dpad_up)) {
            increment = -1;
        } else if (oneTapDown.onPress(this.gamepad1.dpad_down)) {
            increment = 1;
        }
        PoseStorage.autoCase = getCaseFromOrdinal(
                (PoseStorage.autoCase.ordinal() == 0 || PoseStorage.autoCase.ordinal() == AutoCase.values().length) ? PoseStorage.autoCase.ordinal() :
                        PoseStorage.autoCase.ordinal() + increment);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        while (!isStopRequested() && opModeIsActive()) {

            changeCase();

            for (AutoCase autoCase : AutoCase.values()) {
                selectedCaseDisplay(autoCase);
            }

            telemetry.update();
        }
    }

    public static Runnable getAutoFromEnum(SampleMecanumDriveCancelable sampleMecanumDrive, LinearOpMode opMode) {
        if (PoseStorage.autoCase == AutoCase.FullRed || PoseStorage.autoCase == AutoCase.FullBlue) {
            return new AutoRun(sampleMecanumDrive, opMode);
        } else if (PoseStorage.autoCase == AutoCase.CollectDuckRed || PoseStorage.autoCase == AutoCase.CollectDuckBlue) {
            return new AutoRunCollectDuck(sampleMecanumDrive, opMode);
        } else if (PoseStorage.autoCase == AutoCase.HalfRed || PoseStorage.autoCase == AutoCase.HalfBlue) {
            return new AutoRunHalf(sampleMecanumDrive, opMode);
        } else {
            return new AutoRunWarehouseSide(sampleMecanumDrive, opMode);
        }
    }
}
