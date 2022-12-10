package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
public class BasicOpMode_Linear extends LinearOpMode {

    // Declare OpMode members.
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double STICK_DRIFT_MAX = 0.1;
            double ROTATION_DAMPNER = 0.5;

            double xAxisMovement = gamepad1.left_stick_x;
            double yAxisMovement = gamepad1.left_stick_y;
            double rAxisMovement = gamepad1.right_stick_x;
            double drivePower = 0.5;

            if (Math.abs(xAxisMovement) < STICK_DRIFT_MAX) {
                xAxisMovement = 0;
            }
            if (Math.abs(yAxisMovement) < STICK_DRIFT_MAX) {
                yAxisMovement = 0;
            }
            if (Math.abs(rAxisMovement) < STICK_DRIFT_MAX) {
                rAxisMovement = 0;
            }

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double frontLPower = yAxisMovement - xAxisMovement + rAxisMovement * ROTATION_DAMPNER;
            double frontRPower = yAxisMovement + xAxisMovement - rAxisMovement * ROTATION_DAMPNER;
            double backLPower = yAxisMovement + xAxisMovement + rAxisMovement * ROTATION_DAMPNER;
            double backRPower = yAxisMovement - xAxisMovement - rAxisMovement * ROTATION_DAMPNER;

            frontLPower *= drivePower;
            frontRPower *= drivePower;
            backLPower *= drivePower;
            backRPower *= drivePower;

            // Send calculated power to wheels
            frontLeft.setPower(frontLPower);
            frontRight.setPower(frontRPower);
            backLeft.setPower(backLPower);
            backRight.setPower(backRPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("drivePower", drivePower);
            telemetry.addLine();
            telemetry.addData("xDistance", xAxisMovement);
            telemetry.addData("yDistance", yAxisMovement);
            telemetry.addData("xDistance2", rAxisMovement);
            telemetry.addData("yDistance2", "none");
            telemetry.addLine();
            telemetry.addData("frontLeft", frontLeft.getPower());
            telemetry.addData("frontRight", frontRight.getPower());
            telemetry.addData("backLeft", backLeft.getPower());
            telemetry.addData("backRight", backRight.getPower());
            telemetry.update();
        }
    }
}