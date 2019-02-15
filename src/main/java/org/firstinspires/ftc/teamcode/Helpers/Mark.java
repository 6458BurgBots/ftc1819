package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.NoOperationHelper;

public class Mark extends NoOperationHelper {
    public static double MARKER_SERVO_CLOSED = .25;
    public static double MARKER_SERVO_OPEN = 1;
    private boolean isPositionValid;

    protected Servo markerServo;


    public Mark(Telemetry t, HardwareMap h) {
        super(t, h);
    }

    public void init() {
        markerServo = hardwareMap.servo.get("MarkerServo");
        markerServo.setPosition(MARKER_SERVO_CLOSED);
    }

    public void moveMarkerServo(double position){
        markerServo.setPosition(position);
    }

    public void open(){
        markerServo.setPosition(MARKER_SERVO_OPEN);
    }

    public void close(){
        markerServo.setPosition(MARKER_SERVO_CLOSED);
    }
}

