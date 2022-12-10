package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "TeleOp Mode (Cobalt)")
public class TeleOp extends OpMode {
    // Declare OpMode members. These map motors such as frontLeft, frontRight, backLeft, backRight, etc.
    DcMotor linearSlide;
    ServoController claw;
    // Custom mapping for our motors.
    MotorMap Motors;

    // Declare our constants.
    double DRIVE_POWER_INCREMENT    = 0.2;
    double DRIVE_POWER_MIN          = 0.1;
    double ROTATION_DAMPNER         = 0.5;
    double STICK_DRIFT_MAX          = 0.1;

    // Declare our variables.
    boolean rightTriggerPressed     = false;
    boolean leftTriggerPressed      = false;
    double drivePower               = 0.5;



    @Override
    public void init() { // Initializes the robot during "INIT".
        // Maps motors to variables.
        Motors = new MotorMap(hardwareMap);
        // Reverse the right motors.
        Motors.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        Motors.backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        // Gets our linear slide.
        // linearSlide = hardwareMap.get(DcMotor.class, "");
    }

    @Override
    public void loop() {
        // Gets our values from gamepad one.
        double xAxisMovement = gamepad1.left_stick_x;
        double yAxisMovement = gamepad1.left_stick_y;
        double rAxisMovement = gamepad1.right_stick_x;
        // Gets our values from gamepad two.
        double lAxisMovement = gamepad2.left_stick_x;

        // Removes stick drift.
        if (Math.abs(xAxisMovement) < STICK_DRIFT_MAX) {
            xAxisMovement = 0;
        }
        if (Math.abs(yAxisMovement) < STICK_DRIFT_MAX) {
            yAxisMovement = 0;
        }
        if (Math.abs(rAxisMovement) < STICK_DRIFT_MAX) {
            rAxisMovement = 0;
        }

        // Conditional statement that increases drivepower once per buttonpress.
        if (gamepad1.right_trigger == 1) {
            if (rightTriggerPressed == false) {
                // Ensures that drive power does not exceed a value of 1.
                rightTriggerPressed = true;
                drivePower = Math.min(drivePower + DRIVE_POWER_INCREMENT, 1);
            }
        } else {
            rightTriggerPressed = false;
        }

        // Conditional statement that decreases drivepower once per buttonpress.
        if (gamepad1.left_trigger == 1) {
            if (leftTriggerPressed == false) {
                // Ensures that drive power does not go below a value of 0.1.
                leftTriggerPressed = true;
                drivePower = Math.max(drivePower - DRIVE_POWER_INCREMENT, DRIVE_POWER_MIN);
            }
        } else {
            leftTriggerPressed = false;
        }
        
        // Calculates power required to make motors move as needed.
        double frontLeftPower   = yAxisMovement - xAxisMovement + rAxisMovement * (1 - ROTATION_DAMPNER);
        double frontRightPower  = yAxisMovement + xAxisMovement - rAxisMovement * (1 - ROTATION_DAMPNER);
        double backLeftPower    = yAxisMovement + xAxisMovement + rAxisMovement * (1 - ROTATION_DAMPNER);
        double backRightPower   = yAxisMovement - xAxisMovement - rAxisMovement * (1 - ROTATION_DAMPNER);

        // Applies drive power multiplier to increase/decrease speed.
        frontLeftPower  *= drivePower;
        frontRightPower *= drivePower;
        backLeftPower   *= drivePower;
        backRightPower  *= drivePower;

        // Send calculated power to wheels.
        Motors.frontLeft.setPower(frontLeftPower);
        Motors.frontRight.setPower(frontRightPower);
        Motors.backLeft.setPower(backLeftPower);
        Motors.backRight.setPower(backRightPower);

        //double linearSlidePower = lAxisMovement;
        //linearSlide.setPower(linearSlidePower);

        // Outputs out data.
        telemetry.addData("drivePower", drivePower);
        telemetry.addLine();
        telemetry.addData("xDistance", xAxisMovement);
        telemetry.addData("yDistance", yAxisMovement);
        telemetry.addData("xDistance2", rAxisMovement);
        telemetry.addData("yDistance2", "none");
        telemetry.addLine();
        telemetry.addData("frontLeft", Motors.frontLeft.getPower());
        telemetry.addData("frontRight", Motors.frontRight.getPower());
        telemetry.addData("backLeft", Motors.backLeft.getPower());
        telemetry.addData("backRight", Motors.backRight.getPower());
        telemetry.update();
    }
}