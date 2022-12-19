package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Autonomous Mode (Cobalt)")
public class Autonomous_Mode extends LinearOpMode {

    @Override
    public void runOpMode() {
        MotorMap Motors = new MotorMap(hardwareMap, false);

        // Motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Motors.setTarget(31.5, MotorMap.Direction.FORWARD);
        // Motors.drive(1);




        //autonomous parking-only
    waitForStart();
        //starting on the left side of substation and going to the left terminal
    
        Motors.left(0.5);
        sleep(1000);

/*
        //starting on the left side of substation and going to the substation
        
        Motors.right(0.5);
        sleep(200);


        //starting on the right side of substation and going to the right terminal
        
        Motors.right(0.5);
        sleep(1000);


        //starting on the right side of substation and going to the substation

        Motors.left(0.5);
        sleep(200);
*/


        // Motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Motors.linearSlide.setTargetPosition(RobotMath.linearSlideTicks(10));
        // Motors.linearSlide.setPower(0.75);

        // while (Motors.linearSlide.isBusy()) {}
        // Motors.linearSlide.setPower(0.0);
    }
}

