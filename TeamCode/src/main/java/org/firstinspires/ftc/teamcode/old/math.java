public class math {
    double diameter = 10; // calculate this
    double ticksPerRotation = 537.7;
    // double ticksPerInch = Math.PI * diameter / ticksPerRotation;
    double ticksPerInch = Math.PI * diameter * ticksPerRotation;

    public BNO055IMU imu; // keesp track of your rotation
    public Orientation currentOrientation, lastOrientation;

    public double lastHeading = 0.0;
    public double headingOffset;
    private double rawHeading = 0.0;
    private double currentHeading = 0.0;

    public void getTicks(double inches) {
        return inches * ticksPerInch;
    }




}