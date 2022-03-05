package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Hardware.Hardware;
import org.firstinspires.ftc.teamcode.Hardware.HardwareDeclarations;
import org.firstinspires.ftc.teamcode.Hardware.HardwareUtils;

@TeleOp(name = "Test")
public class Test1 extends LinearOpMode {
    @Override
    public void runOpMode(){

        DcMotorEx glisiera, brat;
        glisiera = hardwareMap.get(DcMotorEx.class, "glisiera");
        brat = hardwareMap.get(DcMotorEx.class, "brat");

        glisiera.setTargetPosition(0);
        brat.setTargetPosition(0);

        HardwareUtils.ResetEncoders(glisiera, brat);
        HardwareUtils.RunningWithEncoders(glisiera, brat);

        //glisiera.setVelocityPIDFCoefficients(1.0, 0.1, 0, 10);
        brat.setVelocityPIDFCoefficients(1.0, 0.1, 0, 10);

        //glisiera.setPositionPIDFCoefficients(5);
        brat.setPositionPIDFCoefficients(5);



        glisiera.setPower(1);
        brat.setPower(0);

        waitForStart();
        while (!isStopRequested()&&opModeIsActive()){
            if(gamepad1.dpad_left){
                glisiera.setTargetPosition(0);
            }
            if(gamepad1.dpad_down){
                glisiera.setTargetPosition(-80);
            }
            if(gamepad1.dpad_right){
                glisiera.setTargetPosition(-400);
            }
            telemetry.addData("Glisiera position: ", glisiera.getCurrentPosition());
            telemetry.addData("Brat position: ", brat.getCurrentPosition());
            telemetry.addData("Glisiera Target Pos:", glisiera.getTargetPosition());
            telemetry.update();
        }
    }
}
