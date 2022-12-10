package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorMap {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public HardwareMap hardwareMap;

    public MotorMap(HardwareMap hardwareMap) {
        frontLeft   = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft    = hardwareMap.get(DcMotor.class, "backLeft");
        backRight   = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

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
        backLeft.setPower(-power);
        backRight.setPower(power);
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
}
