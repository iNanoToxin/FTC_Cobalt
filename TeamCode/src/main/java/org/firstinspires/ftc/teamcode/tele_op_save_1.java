package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOP Mode")
public class TeleOP extends OpMode {

    //frontLeft, frontRight, backLeft, backRight
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    Servo spiny;

    DcMotor linearSlide;


    boolean rightTriggerPressed = false;
    boolean leftTriggerPressed = false;
    double drivePower = 0.5;


    @Override
    // runs at the beginning
    public void init() {
        // set value to motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        //spiny = hardwareMap.get(Servo.class, "Spiny");
        // reverse the right motors
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        //spiny.setDirection(Servo.Direction.FORWARD);


        linearSlide = hardwareMap.get(DcMotor.class, "");
    }

    @Override
    public void loop() {
        double x1 = -gamepad1.left_stick_x;
        double y1 = gamepad1.left_stick_y;

        double x2 = gamepad1.right_stick_x;
        double y2 = gamepad1.right_stick_y;

        double y3 = gamepad2.left_stick_y;

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

        frontLeft.setPower(frontLPower);
        frontRight.setPower(frontRPower);
        backLeft.setPower(backLPower);
        backRight.setPower(backRPower);

       /*if (gamepad1.dpad_up == true) {
            spiny.setPosition(0.8);
       } else {
            spiny.setPosition(0);
       }*/

        linearSlide.setPower(linearSlidePower);

        telemetry.addData("drivePower", drivePower);
        telemetry.addData("frontLeft", frontLeft.getPower());
        telemetry.addData("frontRight", frontRight.getPower());
        telemetry.addData("backLeft", backLeft.getPower());
        telemetry.addData("backRight", backRight.getPower());
        telemetry.update();
    }
}




