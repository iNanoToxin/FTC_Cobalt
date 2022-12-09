package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
f
@TeleOp(name = "TeleOP Mode")
public class TeleOP extends OpMode {

    //frontLeft, frontRight, backLeft, backRight
    DcMotor linearSlide;
    ServoController claw;

    MotorMap Motors;


    boolean rightTriggerPressed = false;
    boolean leftTriggerPressed = false;
    double drivePower = 0.5;
    long lastTime = 0;


    @Override
    // runs at the beginning
    public void init() {
        // set value to motors
        Motors = new MotorMap(hardwareMap);
        // reverse the right motors
        Motors.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        Motors.backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        linearSlide = hardwareMap.get(DcMotor.class, "");
    }

    @Override
    public void loop() {
        double x1 = -gamepad1.left_stick_x;
        double y1 = gamepad1.left_stick_y;

        double x2 = gamepad1.right_stick_x;
        double y2 = gamepad1.right_stick_y;

        double y3 = gamepad2.left_stick_y;
        double x4 = gamepad2.right_stick_x;

        if (gamepad1.right_trigger == 1) {
            if (!rightTriggerPressed) {
                drivePower = Math.min(drivePower + 0.2, 1);
                rightTriggerPressed = true;
            }
        } else {
            rightTriggerPressed = false;
        }

        if (gamepad1.left_trigger == 1) {
            if (!leftTriggerPressed) {
                drivePower = Math.max(drivePower - 0.2, 0.1);
                leftTriggerPressed = true;
            }
        } else {
            leftTriggerPressed = false;
        }


        /*
            // Forward and backward
            frontLeft.setPower(-y1);
            frontRight.setPower(-y1);
            backLeft.setPower(y1);
            backRight.setPower(y1);

            // Left and right
            frontLeft.setPower(-x1);
            frontRight.setPower(x1);
            backLeft.setPower(-x1);
            backRight.setPower(x1);

            // Turn left or right
            frontLeft.setPower(x2);
            frontRight.setPower(-x2);
            backLeft.setPower(-x2);
            backRight.setPower(x2);
        */

        double frontLPower = (x1 + y1 - x2);
        double frontRPower = (x1 - y1 - x2);
        double backLPower = -(x1 - y1 + x2);
        double backRPower = -(x1 + y1 + x2);

        frontLPower *= drivePower;
        frontRPower *= drivePower;
        backLPower *= drivePower;
        backRPower *= drivePower;

        Motors.frontLeft.setPower(frontLPower);
        Motors.frontRight.setPower(frontRPower);
        Motors.backLeft.setPower(backLPower);
        Motors.backRight.setPower(backRPower);

        //double linearSlidePower = y3;
        //linearSlide.setPower(linearSlidePower);

        telemetry.addData("drivePower", drivePower);
        telemetry.addData("frontLeft", Motors.frontLeft.getPower());
        telemetry.addData("frontRight", Motors.frontRight.getPower());
        telemetry.addData("backLeft", Motors.backLeft.getPower());
        telemetry.addData("backRight", Motors.backRight.getPower());
        telemetry.addData("backRight", Motors.backRight.getPower());
        telemetry.update();
    }
}




