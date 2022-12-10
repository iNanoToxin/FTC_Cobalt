package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
// IMU imports
import com.qualcomm.hardware.bosch.BNO055IMU; // IMU class
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "TeleOp Mode (Cobalt)")
public class TeleOp_Mode extends OpMode {
    // Declare OpMode members. These map motors such as frontLeft, frontRight, backLeft, backRight, etc.
    // Custom mapping for our motors.
    MotorMap Motors;
    // GoBILDA 5202/3/4 series motors configuration.
    // 0: backLeft
    // 1: frontLeft
    // 2: frontRight
    // 3: backRight

    // Declare our constants.
    double DRIVE_POWER_INCREMENT    = 0.2;
    double DRIVE_POWER_MIN          = 0.1;
    double ROTATION_DAMPNER         = 0.25;
    double STICK_DRIFT_MAX          = 0.1;

    // Declare our variables.
    boolean rightTriggerPressed     = false;
    boolean leftTriggerPressed      = false;
    double  drivePower              = 1.0;

    double diameter = 10; // calculate this
    double ticksPerRotation = 537.7;
    double ticksPerInch = Math.PI * diameter / ticksPerRotation;

    public BNO055IMU imu; // keesp track of your rotation
    Orientation currentOrientation, lastOrientation;

    public double lastHeading = 0.0;
    public double headingOffset;
    private double rawHeading = 0.0;
    private double currentHeading = 0.0;

    @Override
    public void init() { // Initializes the robot during "INIT".
        // Maps motors to variables.
        Motors = new MotorMap(hardwareMap);
        // Reverse the right motors.
        Motors.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        Motors.backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        // Sets direction of servo to forward.
        Motors.claw.setDirection(Servo.Direction.FORWARD);

        // initialize IMU
        // BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        // parameters.mode                = BNO055IMU.SensorMode.IMU;
        // parameters.angleUnit           = BNO055IMU.AngleUnit.RADIANS;
        // parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        // parameters.loggingEnabled      = false;

        // imu.initialize(parameters);
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
        double frontLeftPower   = yAxisMovement + xAxisMovement - rAxisMovement * (1 - ROTATION_DAMPNER);
        double frontRightPower  = yAxisMovement - xAxisMovement + rAxisMovement * (1 - ROTATION_DAMPNER);
        double backLeftPower    = yAxisMovement - xAxisMovement - rAxisMovement * (1 - ROTATION_DAMPNER);
        double backRightPower   = yAxisMovement + xAxisMovement + rAxisMovement * (1 - ROTATION_DAMPNER);

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

        double linearSlidePower = lAxisMovement;
        Motors.linearSlide.setPower(linearSlidePower * 0.5);
        // Motors.claw.setPosition(Math.max(0.2, Math.min(0.5, Math.abs(cAxisMovement))));
        Motors.claw.setPosition(Math.abs(cAxisMovement));

        // Outputs out data.
        telemetry.addData("drivePower", drivePower);
        telemetry.addLine();
        telemetry.addData("linearSlide", Motors.linearSlide.getPower());
        telemetry.addData("claw", Motors.claw.getPosition());
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