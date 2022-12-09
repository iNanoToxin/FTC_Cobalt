
 Blocks  OnBotJava  New Program  Save as...  
-Load Program-
  Save  Delete  Download  Start Program  Stop Program  Reset Field 
How to Use
 
74
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
75
        backRight = hardwareMap.get(DcMotor.class, "backRight");
76
​
77
        // Most robots need the motor on one side to be reversed to drive forward
78
        // Reverse the motor that runs backwards when connected directly to the battery
79
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
80
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
81
​
82
        // Wait for the game to start (driver presses PLAY)
83
        waitForStart();
84
​
85
        // run until the end of the match (driver presses STOP)
86
        while (opModeIsActive()) {
87
          double xAxisMovement = -gamepad1.left_stick_x;
88
          double yAxisMovement = gamepad1.left_stick_y;
89
​
90
          double rAxisMovement = gamepad1.right_stick_x;
91
          double y2 = gamepad1.right_stick_y;
92
​
93
          double y3 = gamepad2.left_stick_y;
94
​
95
​
96
            // Setup a variable for each drive wheel to save power level for telemetry
97
​
98
            // Choose to drive using either Tank Mode, or POV Mode
99
            // Comment out the method that's not used.  The default below is POV.
100
​
101
            // POV Mode uses left stick to go forward, and right stick to turn.
102
            // - This uses basic math to combine motions and is easier to drive straight.
103
            // double frontLPower = (x1 + y1 - x2);
104
            // double frontRPower = -(x1 - y1 - x2);
105
            // double backLPower = -(x1 - y1 + x2);
106
            // double backRPower = (x1 + y1 + x2);
107
            
108
            double frontLPower = yAxisMovement - xAxisMovement + rAxisMovement;
109
            double frontRPower = yAxisMovement + xAxisMovement - rAxisMovement;
110
            double backLPower = yAxisMovement + xAxisMovement + rAxisMovement;
111
            double backRPower = yAxisMovement - xAxisMovement - rAxisMovement;
112
​
113
​
114
            // Tank Mode uses one stick to control each wheel.
115
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
116
            // leftPower  = -gamepad1.left_stick_y ;
117
            // rightPower = -gamepad1.right_stick_y ;
118
​
119
            // Send calculated power to wheels
120
            frontLPower *= drivePower;
121
            frontRPower *= drivePower;
122
            backLPower *= drivePower;
123
            backRPower *= drivePower;
124
​
125
            frontLeft.setPower(frontLPower);
126
            frontRight.setPower(frontRPower);
127
            backLeft.setPower(backLPower);
128
            backRight.setPower(backRPower);
129
​
130
​
131
            // Show the elapsed game time and wheel power.
132
            telemetry.addData("drivePower", drivePower);
 Telemetry  Field  Novice Lessons  Advanced Lessons 
drivePower: 0.5
frontLeft: 0
frontRight: 0
backLeft: 0
backRight: 0

Ultimate Goal Autonomous Actions:
Park Robot on White Line in the middle

Rings Scored in Tower Goal
    Low
    Mid
    High

Ring knocks over a center Powershot

Wobble Goal Delivered to Correct Square
    0 Rings in front of Robot
    1 Ring in front of Robot
    4 Rings in front of Robot

-


-
-
-

-

-


Points:
5


3
6
12

15 per Powershot

15
Closest Square
Center Square
Furtherest Square
