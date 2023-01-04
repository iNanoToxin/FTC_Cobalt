package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

public class PIDController {
    // Constants for proportional, integral, and derivative terms
    private double kP;
    private double kI;
    private double kD;

    // Variables for tracking the accumulated error and the error at the last time step
    private double errorSum;
    private double lastError;
    private double error;
    private double setPoint;

    // Constructor for initializing the constants
    public PIDController(double kP, double kI, double kD) {
        this.kP         = kP;
        this.kI         = kI;
        this.kD         = kD;
        this.errorSum   = 0.0;
        this.lastError  = 0.0;
        this.error      = 0.0;
        this.setPoint   = 0.0;
    }

    public void setSetpoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public void reset() {
        errorSum = 0;
        lastError = 0;
    }

    // Method for calculating the output of the PID controller
    public double calculateOutput(double measuredValue, double dt) {
        // Calculate the error as the difference between the setpoint and the measured value
        error = setPoint - measuredValue;

        // Update the error sum
        errorSum += error * dt;

        // Calculate the derivative of the error
        double dError = (error - lastError) / dt;

        // Update the value of the error at the last time step
        lastError = error;

        // Return the output of the PID controller using the formula:
        double output = kP * error + kI * errorSum + kD * dError;
        return Range.clip(output, -1, 1);
    }

    public double getError() {
        return error;
    }

    public double getErrorSum() {
        return errorSum;
    }

    public boolean onTarget() {
        return Math.abs(error) < 0.05;
    }
}