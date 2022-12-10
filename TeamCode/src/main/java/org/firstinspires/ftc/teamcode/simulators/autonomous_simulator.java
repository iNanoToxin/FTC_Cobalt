package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous Mode (Cobalt)")
public class BlankLinearOpMode extends LinearOpMode {
    // Declare OpMode members.
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private ElapsedTime runtime;

    @Override
    public void runOpMode() {
        frontLeft   = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft    = hardwareMap.get(DcMotor.class, "backLeft");
        backRight   = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime = new ElapsedTime();



        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double pow = 0.1;
            frontLeft.setPower(pow);
            frontRight.setPower(pow);
            backLeft.setPower(pow);
            backRight.setPower(pow);
            
            telemetry.addData("Status", "Running");
            telemetry.addData("Time", runtime.toString());
            telemetry.update();

        }
    }
}