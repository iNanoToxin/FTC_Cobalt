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

    // Declare our gamepad constants.
    private final double drive_power_increment    = 0.2;
    private final double drive_min_power          = 0.1;
    private final double joystick_max_drift       = 0.1;
    
    // Declare our robot constants
    public double claw_max_range           = 0.45;
    public double claw_min_range           = 0.20;
    private double drive_power                    = 0.50;

    // Declare our boolean variables.
    private boolean right_trigger_pressed   = false;
    private boolean left_trigger_pressed    = false;
    private boolean is_buttonA_pressed      = false;
    private boolean is_claw_toggled         = false;

    private final double wheel_diameter = 3.625; // calculate this
    private final double ticks_per_rotation = 537.7;
    private final double ticks_per_inch = Math.PI * wheel_diameter / ticks_per_rotation;

    private BNO055IMU imu;
    private PIDController pidController;
    private ElapsedTime elapsedTime;

    @Override
    public void init() {
        // Initializes the motors.
        {   
            // Maps motors to variables.
            motors = new motorMap(hardwareMap, false);

            // Reverse the right motors.
            motors.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
            motors.backRight.setDirection(DcMotorSimple.Direction.REVERSE);

            // Sets direction of servo to forward.
            // motors.claw.setDirection(Servo.Direction.FORWARD);
            // motors.claw.setPosition(claw_max_range);
        }

        // Initialize and create new PID controller.
        {
            // Create new Proportional-Integral-Derivative Controller.
            pidController = new PIDController(0.0, 0.8, 0.001);
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

        // Handle gamepad1 inputs.
        {
            // Removes stick drift.
            if (Math.abs(xAxisMovement) < joystick_max_drift) {
                xAxisMovement = 0;
            }
            if (Math.abs(yAxisMovement) < joystick_max_drift) {
                yAxisMovement = 0;
            }
            if (Math.abs(rAxisMovement) < joystick_max_drift) {
                rAxisMovement = 0;
            }

            // Conditional statement that increases drivepower once per buttonpress.
            if (gamepad1.right_trigger == 1) {
                if (right_trigger_pressed == false) {
                    // Ensures that drive power does not exceed a value of 1.
                    right_trigger_pressed = true;
                    drive_power = Math.min(drive_power + drive_power_increment, 1);
                }
            } else {
                right_trigger_pressed = false;
            }

            // Conditional statement that decreases drivepower once per buttonpress.
            if (gamepad1.left_trigger == 1) {
                if (left_trigger_pressed == false) {
                    // Ensures that drive power does not go below a value of 0.1.
                    left_trigger_pressed = true;
                    drive_power = Math.max(drive_power - drive_power_increment, drive_min_power);
                }
            } else {
                left_trigger_pressed = false;
            }
        }

        {
            double output = 0; // pidController.calculateOutput(currentAngle, elapsedTime.time());
            
            // Calculates power required to make motors move as needed.
            double front_left_power   = yAxisMovement + xAxisMovement - rAxisMovement + output;
            double front_right_power  = yAxisMovement - xAxisMovement + rAxisMovement - output;
            double back_left_power    = yAxisMovement - xAxisMovement - rAxisMovement + output;
            double back_right_power   = yAxisMovement + xAxisMovement + rAxisMovement - output;

            // Applies drive power multiplier to increase/decrease speed.
            front_left_power  *= drive_power;
            front_right_power *= drive_power;
            back_left_power   *= drive_power;
            back_right_power  *= drive_power;

            // Send calculated power to wheels.
            motors.frontLeft.setPower(front_left_power);
            motors.frontRight.setPower(front_right_power);
            motors.backLeft.setPower(back_left_power);
            motors.backRight.setPower(back_right_power);
        }

        double linear_slide_power = lAxisMovement;
        // motors.linearSlide.setTargetPosition(10);
        // motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);    
        motors.linearSlide.setPower(linear_slide_power * 0.5);
        motors.claw.setPosition(cAxisMovement);

        // motors.claw.setPosition(0);
        // Handle gamepad2 inputs.
        // {
        //     motors.claw.scaleRange(claw_min_range, claw_max_range);
        //     if (gamepad2.a) {
        //         if (is_buttonA_pressed == false) {
        //             is_buttonA_pressed = true;
        //             telemetry.addData("pressed A", true);
        //             motors.claw.setPosition(is_claw_toggled ? 0 : 1);
        //             is_claw_toggled = !is_claw_toggled;
        //         }
        //     } else {
        //         is_buttonA_pressed = false;
        //     }
        // }

        // Outputs out data.
        {
            telemetry.addData("linear_slide_position", motors.linearSlide.getCurrentPosition());
            // telemetry.addLine();
            // telemetry.addData("angle        :", currentAngle);
            // telemetry.addData("PID          :", output);
            // telemetry.addData("getError     :", pidController.getError());
            // telemetry.addData("getErrorSum  :", pidController.getErrorSum());
            // telemetry.addData("onTarget     :", pidController.onTarget());
            // telemetry.addData("elapsed      :", elapsedTime.time());
            // telemetry.addLine();
            // telemetry.addData("drive_power   :", drive_power);
            // telemetry.addData("frontLeft    :", motors.frontLeft.getPower());
            // telemetry.addData("frontRight   :", motors.frontRight.getPower());
            // telemetry.addData("backLeft     :", motors.backLeft.getPower());
            // telemetry.addData("backRight    :", motors.backRight.getPower());
            // telemetry.addLine();
            // telemetry.addData("linearSlide  :", motors.linearSlide.getPower());
            telemetry.addData("claw         :", motors.claw.getPosition());
            // telemetry.addLine();
            // telemetry.addData("xDistance    :", xAxisMovement);
            // telemetry.addData("yDistance    :", yAxisMovement);
            // telemetry.addData("xDistance2   :", rAxisMovement);
            // telemetry.addData("yDistance2   :", "none");
            telemetry.update();
            elapsedTime.reset();
        }
    }
}