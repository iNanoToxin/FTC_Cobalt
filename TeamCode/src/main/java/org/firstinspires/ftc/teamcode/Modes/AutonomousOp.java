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

// starting from the right side

        //moves to the left (change this depending on which side we are on)
        Motors.setTarget(36.5, LEFT);
        sleep(1000);


        //moves forward and the linear slide up at the same time; stops a few inches away from the junction
        Motors.setTarget(32, FORWARD);
        Motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(33));
        Motors.linearSlide.setPower(0.75);
        sleep(5000);

        //moves the robot forward 3 inches once the linear slide is all the way up
        Motors.setTarget(3, FORWARD);
        sleep(500);

        //opens claw
        Motors.claw.setPower(1);
        sleep(1000);

/*
        //moves backward and turns 180 degrees to face the substation while moving the linear slide back down and parks
        Motors.setTarget(23, BACKWARD);
        Motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(33));
        Motors.linearSlide.setPower(-0.75);
        Motors.setTarget(19, ROTATE_C);
        sleep(10000);
    */

        Motors.setTarget(12, RIGHT);
        sleep(1000);

        Motors.setTarget(9, FOWARD);
        Motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(10));
        Motors.linearSlide.setPower(-0.75);
        sleep(1000);

        Motors.setTarget(9, ROTATE_C);
        sleep(5000);

        Motors.setTarget(43, FORWARD);
        sleep(2000);

        Motors.claw.setPower(1);
        Motors.setTarget(3, FORWARD);
        sleep(1000);

        Motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(23));
        Motors.linearSlide.setPower(0.75);
        Motors.setTarget(18, ROTATE_CC);
        sleep(5000);

        Motors.setTarget(6, RIGHT);
        Motors.setTarget(30, FORWARD);
        sleep(2000);

        

/*
        Motors.rotate(-0.8);
        sleep(200);
        Motors.forward(0.8);
        sleep(1000);


        sleep(2000);

        Motors.forward(0.2);
        sleep(100);

        Motors.claw.setPower(0.75);
        sleep(1000);

        Motors.backward(0.2);
        sleep(100);

        Motors.rotate(0.8);
        sleep(200);
        
        Motors.linearSlide.setPower(0.75);
        sleep(2000);

        Motors.forward.setPower(0.8);
        sleep(1000);


        Motors.claw.setPower
        // while (Motors.linearSlide.isBusy()) {}
        // Motors.linearSlide.setPower(0.0);
        */
    }
}

