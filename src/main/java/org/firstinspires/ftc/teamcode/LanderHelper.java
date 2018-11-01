package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LanderHelper extends NoOperationHelper {
    DcMotor armMotor;
    LanderHelper(Telemetry t, HardwareMap h)
    {
        super(t, h);
    }

    public void init() {
        armMotor  = hardwareMap.get(DcMotor.class, "arm");
    }
    public void checkTeleOp(Gamepad gamepad1, Gamepad gamepad2){
        // alaina is struggling to find a way to describe this

        if (gamepad1.a) {
            armMotor.setPower(.3);
        }
        if (gamepad1.b) {
            armMotor.setPower(-.3);
        }
        if (!gamepad1.a && !gamepad1.b){
            armMotor.setPower(0);
        }
    }
}
