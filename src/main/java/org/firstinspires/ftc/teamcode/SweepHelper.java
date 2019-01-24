package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.annotations.MotorType;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(name = "Sweep", group = "Motor")


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