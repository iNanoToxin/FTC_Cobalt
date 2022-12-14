package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Autonomous Mode (Cobalt)")
public class Autonomous_Mode extends LinearOpMode {

    @Override
    public void runOpMode() {
        MotorMap Motors = new MotorMap(hardwareMap);

        Motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)

        Motors.setTarget(31.5, "right");

        Motors.forward(1);
        sleep(1000);
        Motors.rotate(0.3);
        sleep(1000);
        Motors.right(1);

    }
}

