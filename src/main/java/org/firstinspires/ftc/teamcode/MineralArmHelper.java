package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MineralArmHelper extends NoOperationHelper {
    private DcMotor elbowMotor;
    private DcMotor shoulderMotor;
    private boolean isPositionValid;
    double shoulderPosition;
    double elbowPosition;
    double servoPosition;
    public static double MINERAL_SERVO_CLOSED = 0;
    public static double MINERAL_SERVO_OPEN = 1;
    protected Servo mineralServo;

    MineralArmHelper(Telemetry t, HardwareMap h)
    {
        super(t, h);
    }

    public void init() {
        elbowMotor  = hardwareMap.dcMotor.get("elbow");
        elbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbowMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elbowPosition = elbowMotor.getCurrentPosition();

        shoulderMotor  = hardwareMap.dcMotor.get("shoulder");
        shoulderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shoulderPosition = shoulderMotor.getCurrentPosition();

        mineralServo = hardwareMap.servo.get("Mineral Servo");
        servoPosition = MINERAL_SERVO_CLOSED;
    }

    public void moveElbow(double power) {
        elbowMotor.setPower(power);
    }

    public void moveMarkerServo(double position){
        mineralServo.setPosition(position);
    }

    public void open(){
        mineralServo.setPosition(MINERAL_SERVO_OPEN);
    }

    public void close(){
        mineralServo.setPosition(MINERAL_SERVO_CLOSED);
    }

    public void resetEncoders() {
        elbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runWithoutEncoders() {
        elbowMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void checkTeleOp(Gamepad gamepad1, Gamepad gamepad2){
       // elbowMotor.setPower(gamepad2.left_stick_y);
        elbowPosition = elbowPosition - gamepad2.left_stick_y * 5;
        int currPosition = elbowMotor.getCurrentPosition();
        elbowPosition = Range.clip(elbowPosition, currPosition - 10,currPosition + 10);
        elbowMotor.setTargetPosition((int)elbowPosition);
        elbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elbowMotor.setPower(1);
        telemetry.addData("Elbow Encoder: ", elbowMotor.getCurrentPosition());
        telemetry.addData("Desired Elbow", elbowPosition);

       // shoulderMotor.setPower(gamepad2.right_stick_y);
        shoulderPosition = shoulderPosition + gamepad2.right_stick_y * 7;
        currPosition = shoulderMotor.getCurrentPosition();
        shoulderPosition = Range.clip(shoulderPosition, currPosition - 50,currPosition + 50);

        shoulderMotor.setTargetPosition((int)shoulderPosition);
        shoulderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shoulderMotor.setPower(1);
        telemetry.addData("Shoulder Encoder: ", shoulderMotor.getCurrentPosition());
        telemetry.addData("Desired Shoulder", shoulderPosition);

        if (gamepad2.left_trigger > 0) {
            servoPosition += gamepad2.left_trigger / 10;
        } else if (gamepad2.right_trigger > 0) {
            servoPosition -= gamepad2.right_trigger / 10;
        } else if (gamepad2.right_bumper) {
            servoPosition = MINERAL_SERVO_CLOSED;
        }/* else if (gamepad2.right_bumper) {
            mineralServo.setPosition(.5);
        }*/
        servoPosition = Range.clip(servoPosition, MINERAL_SERVO_CLOSED,MINERAL_SERVO_OPEN);

        mineralServo.setPosition(servoPosition);
    }
}
