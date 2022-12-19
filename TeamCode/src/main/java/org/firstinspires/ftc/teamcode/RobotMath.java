package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class RobotMath {
    // final double diameter = 10; // calculate this
    // final double ticksPerRotation = 537.7;
    // double ticksPerInch = Math.PI * diameter / ticksPerRotation;

    public BNO055IMU imu; // keesp track of your rotation
    public Orientation currentOrientation, lastOrientation;

    public double lastHeading = 0.0;
    public double headingOffset;
    private double rawHeading = 0.0;
    private double currentHeading = 0.0;

    public static int getTicks(double inches) {
        double diameter = 10; // calculate this
        double ticksPerRotation = 537.7;
        double ticksPerInch = Math.PI * diameter * ticksPerRotation;

        return (int) Math.floor(inches * ticksPerInch);
    }

    public static int linearSlideTicks(double inches) {
        double diameter = 1.673228;
        double circumference = Math.PI * diameter;
        double ticksInARotation = 537.7;

                    
        double inchesPerTick = circumference / ticksInARotation;
        return (int) Math.floor(inches / inchesPerTick); 
    }
}