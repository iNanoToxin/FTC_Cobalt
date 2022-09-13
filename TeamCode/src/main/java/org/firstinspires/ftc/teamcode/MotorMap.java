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
        this.hardwareMap = hardwareMap;
        frontLeft = this.hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = this.hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = this.hardwareMap.get(DcMotor.class, "backLeft");
        backRight = this.hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
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
