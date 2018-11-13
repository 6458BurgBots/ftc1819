package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by WilliamsburgRobotic on 10/31/2017.
 */

public class MoveHelper extends NoOperationHelper {

    private static double MARKER_SERVO_CLOSED = 0;
    private static double MARKER_SERVO_OPEN = .0;

// declares the motors; gives them names we will use to call them later
    protected DcMotor FLMotor;
    protected DcMotor FRMotor;
    protected DcMotor BLMotor;
    protected DcMotor BRMotor;
    protected Servo MarkerServo;
    MoveHelper(Telemetry t, HardwareMap h)
    {
        super(t, h);
    }

    public void init() {
        // links motor names here to the names given in the config on the phones
        FLMotor = hardwareMap.dcMotor.get("LF"); // TODO: Fix the config names to match the variables
        FRMotor = hardwareMap.dcMotor.get("RF");
        BLMotor = hardwareMap.dcMotor.get("LB");
        BRMotor = hardwareMap.dcMotor.get("RB");
        MarkerServo = hardwareMap.servo.get("MarkerServo");

        MarkerServo.setPosition(MARKER_SERVO_CLOSED);


        // setting directions/telling them we are using encoders
        FLMotor.setDirection(DcMotor.Direction.REVERSE);
        BLMotor.setDirection(DcMotor.Direction.REVERSE);
        FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void omniDrive(double lx,double ly, double rx){
        telemetry.addData("Drive input (lx,ly): ", lx + "," + ly);
        // omni-drive math, sets it up to run properly
        double fl = ly - lx - rx;
        double fr = ly + lx + rx;
        double bl = ly + lx - rx;
        double br = ly - lx + rx;


        String output = String.format("%1$.3f,%1$.3f,%1$.3f,%1$.3f",fl,fr,bl,br);
        telemetry.addData("Driving (fl,fr,bl,br): ", output);
        // sets power to the motors
        FLMotor.setPower(fl);
        FRMotor.setPower(fr);
        BLMotor.setPower(bl);
        BRMotor.setPower(br);

    }

    public void turn(double rx){
        // method used to turn the robot
        FLMotor.setPower(rx);
        FRMotor.setPower(-rx);
        BLMotor.setPower(rx);
        BRMotor.setPower(-rx);
    }

    public void moveMarkerServo(double position){MarkerServo.setPosition(position);}

    // actually turns on the motors/sets power??
    public void runFLMotor (double power){
        FLMotor.setPower(power);
    }
    public void runFRMotor (double power){
        FRMotor.setPower(power);
    }
    public void runBLMotor (double power){
        BLMotor.setPower(power);
    }
    public void runBRMotor (double power){
        BRMotor.setPower(power);
    }
    public void driveForward (double power){
        FLMotor.setPower(power);
        FRMotor.setPower(power);
        BLMotor.setPower(power);
        BRMotor.setPower(power);
    }



    public void loop(){
    }
    public void showEncoderValues (){
        telemetry.addData("BR Encoder", BRMotor.getCurrentPosition());
        telemetry.addData("BL Encoder", BLMotor.getCurrentPosition());
        telemetry.addData("FR Encoder", FRMotor.getCurrentPosition());
        telemetry.addData("FL Encoder", FLMotor.getCurrentPosition());

    }
    public void resetEncoders (){
        BRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runUsingEncoders (){
        BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void moveToPosition (int position){
        FLMotor.setTargetPosition(position);
        FLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
}

    public int getEncoderValue(){
        return FLMotor.getCurrentPosition();

    }

    public void checkTeleOp(Gamepad gamepad1,Gamepad gamepad2){
        // alaina is struggling to find a way to describe this
        float LY = -gamepad1.left_stick_y;
        float LX = gamepad1.left_stick_x;
        float RX = gamepad1.right_stick_x;


        //Establishes floating variables linked to the gamepads
        telemetry.addData("Left X", LX);
        telemetry.addData("Left Y", LY);
        telemetry.addData("Right X", RX);
        telemetry.addData("BR Encoder", BRMotor.getCurrentPosition());
        telemetry.addData("BL Encoder", BLMotor.getCurrentPosition());
        telemetry.addData("FR Encoder", FRMotor.getCurrentPosition());
        telemetry.addData("FL Encoder", FLMotor.getCurrentPosition());
        LY = Range.clip(LY, -1, 1);
        LX = Range.clip(LX, -1, 1);
        RX = Range.clip(RX, -1, 1);

        omniDrive(LX, LY, RX*.75);

        if (gamepad1.a) {
            BLMotor.setPower(.3);
        }
        if (gamepad1.b) {
            BRMotor.setPower(.3);
        }
        if (gamepad1.x) {
            FLMotor.setPower(.3);
        }
        if (gamepad1.y) {
            FRMotor.setPower(.3);
        }

        if(gamepad2.b){
            moveMarkerServo(MARKER_SERVO_OPEN);
        }
        if(gamepad2.x){
            moveMarkerServo(MARKER_SERVO_CLOSED);
        }
    }
    public int GetBRMotorPosition(){
        return BRMotor.getCurrentPosition();
    }
    public int GetBLMotorPosition(){
        return BLMotor.getCurrentPosition();
    }
    public int GetFRMotorPosition(){
        return FRMotor.getCurrentPosition();
    }
    public int GetFLMotorPosition(){
        return FLMotor.getCurrentPosition();
    }

}
