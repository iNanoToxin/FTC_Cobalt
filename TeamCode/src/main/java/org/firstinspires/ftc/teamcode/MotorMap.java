package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorMap {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public Servo claw;
    public DcMotor linearSlide;
    public HardwareMap hardwareMap;

    public MotorMap(HardwareMap hardwareMap, boolean autonomous) {
        frontLeft   = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft    = hardwareMap.get(DcMotor.class, "backLeft");
        backRight   = hardwareMap.get(DcMotor.class, "backRight");
        claw        = hardwareMap.get(Servo.class, "claw");
        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");

        if (autonomous) {

        } else {
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        claw.setDirection(Servo.Direction.REVERSE);

        this.hardwareMap = hardwareMap;
    }

    public void forward(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(-power);
        backRight.setPower(-power);
    }

    public void backward(double power) {
        forward(-power);
    }

    public void left(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }

    public void right(double power) {
        left(-power);
    }

    public void rotate(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(power);
    }

    public void cclaw(double power) {
        claw.setPosition(power);
    }

    public void raiseLinearSlide(double power) {
        linearSlide.setPower(power);
    }

    public void setMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
        linearSlide.setMode(mode);
    }

    public void waitForMotorEncoders () {
        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {}
        Stop();
    }

    public void setTarget(double inches, Direction direction) {
        int ticks = RobotMath.getTicks(inches);

        switch (direction) {
            
            case BACKWARD:
                frontLeft.setTargetPosition(-ticks);
                frontRight.setTargetPosition(-ticks);
                backLeft.setTargetPosition(ticks);
                backRight.setTargetPosition(ticks);
            case FORWARD:
                frontLeft.setTargetPosition(ticks);
                frontRight.setTargetPosition(ticks);
                backLeft.setTargetPosition(-ticks);
                backRight.setTargetPosition(-ticks);
                break;
            case LEFT:
                frontLeft.setTargetPosition(-ticks);
                frontRight.setTargetPosition(ticks);
                backLeft.setTargetPosition(-ticks);
                backRight.setTargetPosition(ticks);
                break;
            case RIGHT:
                frontLeft.setTargetPosition(ticks);
                frontRight.setTargetPosition(-ticks);
                backLeft.setTargetPosition(ticks);
                backRight.setTargetPosition(-ticks);
                break;
            case DIAG_NW:
                frontLeft.setTargetPosition(ticks);
                frontRight.setTargetPosition(ticks);
                backLeft.setTargetPosition(-ticks);
                backRight.setTargetPosition(-ticks);
                break;
            case ROTATE_C:
                frontLeft.setTargetPosition(ticks);
                frontRight.setTargetPosition(-ticks);
                backLeft.setTargetPosition(ticks);
                backRight.setTargetPosition(-ticks);
            case ROTATE_CC:
                frontLeft.setTargetPosition(-ticks);
                frontRight.setTargetPosition(ticks);
                backLeft.setTargetPosition(-ticks);
                backRight.setTargetPosition(ticks);
        }
    }

    public void Stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void drive(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        waitForMotorEncoders();
    }


    public enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT,
        DIAG_NW,
        DIAG_NE,
        DIAG_SW,
        DIAG_SE,
        ROTATE_C,
        ROTATE_CC
    }
}