package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.NoOperationHelper;

public class SweepHelper extends NoOperationHelper {
    DigitalChannel digitalTouch;  // Hardware Device Object

    private DcMotor sweepMotor;

    public SweepHelper(Telemetry t, HardwareMap h) {
        super(t, h);
    }

    public void init() {
        digitalTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);
        sweepMotor = hardwareMap.dcMotor.get("sweep");

    }

    public void in (double power) {
        if ( power > 0 && !digitalTouch.getState()) {
            sweepMotor.setPower(0);
        } else {
            sweepMotor.setPower(power);
        }
    }

    public void showTouchStatus(Telemetry telemetry) {
        telemetry.addData("Touch Status: ", digitalTouch.getState());
    }

}