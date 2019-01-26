package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;


import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SweepHelper extends NoOperationHelper {

    private DcMotor sweepMotor;

    SweepHelper(Telemetry t, HardwareMap h) {
        super(t, h);
    }

    public void init() {

        sweepMotor = hardwareMap.dcMotor.get("Sweep");

    }

    public void in (double power) {
        sweepMotor.setPower(-power);

    }

}