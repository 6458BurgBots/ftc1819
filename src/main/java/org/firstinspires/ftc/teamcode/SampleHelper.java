package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SampleHelper extends NoOperationHelper {
    public static double SAMPLE_SERVO_CLOSED = 1;
    public static double SAMPLE_SERVO_OPEN = 0;
    private boolean isPositionValid;

    protected Servo sampleServo;


    SampleHelper(Telemetry t, HardwareMap h) {
        super(t, h);
    }

    public void init() {
        sampleServo = hardwareMap.servo.get("sampleServo");
        sampleServo.setPosition(SAMPLE_SERVO_CLOSED);
    }

    public void moveSampleServo(double position){
        sampleServo.setPosition(position);
    }

    public void open(){
        sampleServo.setPosition(SAMPLE_SERVO_OPEN);
    }

    public void close(){
        sampleServo.setPosition(SAMPLE_SERVO_CLOSED);
    }
}

