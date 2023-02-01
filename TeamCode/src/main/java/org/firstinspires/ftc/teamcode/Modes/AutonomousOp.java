package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Autonomous (Cobalt)")
public class AutonomousOp extends LinearOpMode {

	@Override
	public void runOpMode() {
		motorMap motors = new motorMap(hardwareMap, false);

		// motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		// motors.setTarget(31.5, motorMap.Direction.FORWARD);
		// motors.drive(1);
		waitForStart();

		// starting from the right side

		//moves to the left (change this depending on which side we are on)
		motors.setTarget(36.5, motorMap.Direction.LEFT);
		
		telemetry.addData("target", motors.frontLeft.getTargetPosition());
		telemetry.update();

		sleep(1000);


		// //moves forward and the linear slide up at the same time; stops a few inches away from the junction
		// motors.setTarget(32, motorMap.Direction.FORWARD);
		// motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(33));
		// motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		// motors.linearSlide.setPower(0.75);
		// sleep(5000);

		//moves the robot forward 3 inches once the linear slide is all the way up
		motors.setTarget(3, motorMap.Direction.FORWARD);
		sleep(500);

		//opens claw
		motors.setPower(1);
		sleep(1000);

		/*
			//moves backward and turns 180 degrees to face the substation while moving the linear slide back down and parks
			motors.setTarget(23, BACKWARD);
			motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
			motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(33));
			motors.linearSlide.setPower(-0.75);
			motors.setTarget(19, ROTATE_C);
			sleep(10000);
		*/

		motors.setTarget(12, motorMap.Direction.RIGHT);
		sleep(1000);

		motors.setTarget(9, motorMap.Direction.FORWARD);
		motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(10));
		motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		motors.linearSlide.setPower(-0.75);
		sleep(1000);

		motors.setTarget(9, motorMap.Direction.ROTATE_C);
		sleep(5000);

		motors.setTarget(43, motorMap.Direction.FORWARD);
		sleep(2000);

		motors.claw.setPosition(1);
		motors.setTarget(3, motorMap.Direction.FORWARD);
		sleep(1000);

		// motors.linearSlide.setTargetPosition(robotMath.linearSlideTicks(23));
		// motors.linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		// motors.linearSlide.setPower(0.75);
		// motors.setTarget(18, motorMap.Direction.ROTATE_CC);
		// sleep(5000);

		motors.setTarget(6, motorMap.Direction.RIGHT);
		motors.setTarget(30, motorMap.Direction.FORWARD);
		sleep(2000);



		/*
			motors.rotate(-0.8);
			sleep(200);
			motors.forward(0.8);
			sleep(1000);


			sleep(2000);

			motors.forward(0.2);
			sleep(100);

			motors.claw.setPower(0.75);
			sleep(1000);

			motors.backward(0.2);
			sleep(100);

			motors.rotate(0.8);
			sleep(200);

			motors.linearSlide.setPower(0.75);
			sleep(2000);

			motors.forward.setPower(0.8);
			sleep(1000);


			motors.claw.setPower
			// while (motors.linearSlide.isBusy()) {}
			// motors.linearSlide.setPower(0.0);
		*/
	}
}

