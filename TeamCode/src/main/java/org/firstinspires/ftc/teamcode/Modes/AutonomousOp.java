package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Autonomous (Cobalt)")
public class AutonomousOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        motorMap Motors = new motorMap(hardwareMap, false);

        // Motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Motors.setTarget(31.5, motorMap.Direction.FORWARD);
        // Motors.drive(1);
        waitForStart();

        Motors.right(0.8);
        sleep(1000);

        // Motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(10));
        // Motors.linearSlide.setPower(0.75);

        // while (Motors.linearSlide.isBusy()) {}
        // Motors.linearSlide.setPower(0.0);
    }
}

