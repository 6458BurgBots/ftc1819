package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CombineHelper extends NoOperationHelper {
    private CRServo combineServo;

    CombineHelper(Telemetry t, HardwareMap h)
    {
        super(t, h);
    }


    public void init() {
        combineServo = hardwareMap.crservo.get("combineServo");
    }


    public void intake(double power){
        combineServo.setPower(power);
    }
}





