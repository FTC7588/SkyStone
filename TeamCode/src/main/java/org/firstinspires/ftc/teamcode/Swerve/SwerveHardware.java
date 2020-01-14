package org.firstinspires.ftc.teamcode.Swerve;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.control.Encoder;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class SwerveHardware {
    //Declare Motors
    public DcMotor rearLeftDrive;
    public DcMotor rearRightDrive;
    public DcMotor frontLeftDrive;
    public DcMotor frontRightDrive;

    //Declare extra Motors to act as encoders
    private DcMotor rearLeftEncoderPort;
    private DcMotor rearRightEncoderPort;
    private DcMotor frontLeftEncoderPort;
    private DcMotor frontRightEncoderPort;

    //Declare Servos
    public CRServo rearLeftTurn;
    public CRServo rearRightTurn;
    public CRServo frontLeftTurn;
    public CRServo frontRightTurn;

    public Encoder rearLeftEncoder;
    public Encoder rearRightEncoder;
    public Encoder frontLeftEncoder;
    public Encoder frontRightEncoder;

    //Declare Sensors
    public BNO055IMU imu;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public SwerveHardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        // Motors
        rearLeftDrive = hwMap.get(DcMotor.class, "rearLeftDrive");
        rearRightDrive = hwMap.get(DcMotor.class, "rearRightDrive");
        frontLeftDrive = hwMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hwMap.get(DcMotor.class, "frontRightDrive");

        // Encoders
        rearLeftEncoderPort = hwMap.get(DcMotor.class, "rearLeftEncoderPort");
        rearRightEncoderPort = hwMap.get(DcMotor.class, "rearRightEncoderPort");
        frontLeftEncoderPort = hwMap.get(DcMotor.class, "frontLeftEncoderPort");
        frontRightEncoderPort = hwMap.get(DcMotor.class, "frontRightEncoderPort");

        // Servos
        rearLeftTurn = hwMap.get(CRServo.class, "rearLeftTurn");
        rearRightTurn = hwMap.get(CRServo.class, "rearRightTurn");
        frontLeftTurn = hwMap.get(CRServo.class, "frontLeftTurn");
        frontRightTurn = hwMap.get(CRServo.class, "frontRightTurn");
        imu = hwMap.get(BNO055IMU.class, "imu");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        rearLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        rearRightDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);

        // The Rev Robotics Expansion Hub need a zero power behavior specified
        rearLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Stop drive motors
        rearLeftDrive.setPower(0);
        rearRightDrive.setPower(0);
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);

        // Stop Encoder ports
        // This isn't really necessary
        rearLeftEncoderPort.setPower(0);
        rearRightEncoderPort.setPower(0);
        frontLeftEncoderPort.setPower(0);
        frontRightEncoderPort.setPower(0);

        // Stop the turning Servos
        rearLeftTurn.setPower(0);
        rearRightTurn.setPower(0);
        frontLeftTurn.setPower(0);
        frontRightTurn.setPower(0);

        rearLeftEncoderPort.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRightEncoderPort.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftEncoderPort.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightEncoderPort.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rearLeftEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rearRightEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightEncoderPort.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rearLeftEncoder = new Encoder(rearLeftEncoderPort, 4096);
        rearRightEncoder = new Encoder(rearRightEncoderPort, 4096);
        frontLeftEncoder = new Encoder(frontLeftEncoderPort, 4096);
        frontRightEncoder = new Encoder(frontRightEncoderPort, 4096);

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Initialize imu parameters
        imu.initialize(parameters);

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }
}