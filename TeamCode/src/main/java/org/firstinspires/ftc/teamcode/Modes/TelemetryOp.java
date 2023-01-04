package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

// IMU imports.
import com.qualcomm.hardware.bosch.BNO055IMU; // IMU class.
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "Teleop (Cobalt)")
public class TelemetryOp extends OpMode {
    // GoBILDA 5202/3/4: {0: backLeft, 1: frontLeft, 2: frontRight, 3: backRight}

    // Declare OpMode members. These map motors such as frontLeft, frontRight, backLeft, backRight, etc.
    private motorMap motors;

    // Declare our constants.
    private double DRIVE_POWER_INCREMENT    = 0.2;
    private double DRIVE_POWER_MIN          = 0.1;
    private double STICK_DRIFT_MAX          = 0.1;

    // Declare our variables.
    private boolean right_trigger_pressed   = false;
    private boolean left_trigger_pressed    = false;
    private boolean is_buttonA_pressed      = false;
    private boolean is_claw_toggled         = false;
    private double  drivePower              = 0.5;

    private double claw_max_range           = 0.45;
    private double claw_min_range           = 0.20;

    private double diameter = 10; // calculate this
    private double ticksPerRotation = 537.7;
    private double ticksPerInch = Math.PI * diameter / ticksPerRotation;

    private BNO055IMU imu;
    private PIDController pidController;
    private ElapsedTime elapsedTime;

    @Override
    public void init() {
        // Maps motors to variables.
        motors = new motorMap(hardwareMap, false);

        // Reverse the right motors.
        motors.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motors.backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Sets direction of servo to forward.
        motors.claw.setDirection(Servo.Direction.FORWARD);
        motors.claw.setPosition(claw_max_range);

        // Create new Proportional-Integral-Derivative Controller.
        pidController = new PIDController(0.008, 0.001, 0);
        pidController.setSetpoint(0.0);

        elapsedTime = new ElapsedTime();

        // initialize IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode                 = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit            = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit            = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled       = false;
        imu                             = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    @Override
    public void loop() {
        // Gets our values from gamepad one.
        double xAxisMovement = gamepad1.left_stick_x;
        double yAxisMovement = gamepad1.left_stick_y;
        double rAxisMovement = gamepad1.right_stick_x;
        // Gets our values from gamepad two.
        double lAxisMovement = gamepad2.left_stick_y;
        double cAxisMovement = gamepad2.right_stick_x;

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        double currentAngle = angles.firstAngle;


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
            if (right_trigger_pressed == false) {
                // Ensures that drive power does not exceed a value of 1.
                right_trigger_pressed = true;
                drivePower = Math.min(drivePower + DRIVE_POWER_INCREMENT, 1);
            }
        } else {
            right_trigger_pressed = false;
        }

        // Conditional statement that decreases drivepower once per buttonpress.
        if (gamepad1.left_trigger == 1) {
            if (left_trigger_pressed == false) {
                // Ensures that drive power does not go below a value of 0.1.
                left_trigger_pressed = true;
                drivePower = Math.max(drivePower - DRIVE_POWER_INCREMENT, DRIVE_POWER_MIN);
            }
        } else {
            left_trigger_pressed = false;
        }

        double output = 0; // pidController.calculateOutput(currentAngle, elapsedTime.time());
        
        // Calculates power required to make motors move as needed.
        double frontLeftPower   = yAxisMovement + xAxisMovement - rAxisMovement + output;
        double frontRightPower  = yAxisMovement - xAxisMovement + rAxisMovement - output;
        double backLeftPower    = yAxisMovement - xAxisMovement - rAxisMovement + output;
        double backRightPower   = yAxisMovement + xAxisMovement + rAxisMovement - output;

        // Applies drive power multiplier to increase/decrease speed.
        frontLeftPower  *= drivePower;
        frontRightPower *= drivePower;
        backLeftPower   *= drivePower;
        backRightPower  *= drivePower;

        // Send calculated power to wheels.
        motors.frontLeft.setPower(frontLeftPower);
        motors.frontRight.setPower(frontRightPower);
        motors.backLeft.setPower(backLeftPower);
        motors.backRight.setPower(backRightPower);

        double linearSlidePower = lAxisMovement;
        motors.linearSlide.setPower(linearSlidePower * 0.5);
        // motors.claw.setPosition(Math.max(0.2, Math.min(0.5, Math.abs(cAxisMovement))));

        motors.claw.scaleRange(claw_min_range, claw_max_range);
        if (gamepad2.a) {
            if (is_buttonA_pressed == false) {
                is_buttonA_pressed = true;
                
                motors.claw.setPosition(is_claw_toggled ? 0 : 1);
                is_claw_toggled = !is_claw_toggled;
            }
        } else {
            is_buttonA_pressed = false;
        }

        // Outputs out data.
        telemetry.addData("angle        :", currentAngle);
        telemetry.addData("PID          :", output);
        telemetry.addData("getError     :", pidController.getError());
        telemetry.addData("getErrorSum  :", pidController.getErrorSum());
        telemetry.addData("onTarget     :", pidController.onTarget());
        telemetry.addData("elapsed      :", elapsedTime.time());
        telemetry.addLine();
        telemetry.addData("drivePower   :", drivePower);
        telemetry.addData("frontLeft    :", motors.frontLeft.getPower());
        telemetry.addData("frontRight   :", motors.frontRight.getPower());
        telemetry.addData("backLeft     :", motors.backLeft.getPower());
        telemetry.addData("backRight    :", motors.backRight.getPower());
        // telemetry.addLine();
        // telemetry.addData("linearSlide  :", motors.linearSlide.getPower());
        // telemetry.addData("claw         :", motors.claw.getPosition());
        // telemetry.addLine();
        // telemetry.addData("xDistance    :", xAxisMovement);
        // telemetry.addData("yDistance    :", yAxisMovement);
        // telemetry.addData("xDistance2   :", rAxisMovement);
        // telemetry.addData("yDistance2   :", "none");
        telemetry.update();
        elapsedTime.reset();
    }
}