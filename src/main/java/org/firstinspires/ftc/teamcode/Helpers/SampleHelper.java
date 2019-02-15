package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.NoOperationHelper;

public class SampleHelper extends NoOperationHelper {
    public static double SAMPLE_SERVO_CLOSED = .93229;
    //public static double SAMPLE_SERVO_OPEN = .3158;
    public static double SAMPLE_SERVO_OPEN = .29;
    private boolean isPositionValid;

    protected Servo sampleServo;


    public SampleHelper(Telemetry t, HardwareMap h) {
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

