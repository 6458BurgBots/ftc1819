package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CombineHelper extends NoOperationHelper {
    private CRServo combineServo;

    public CombineHelper(Telemetry t, HardwareMap h)
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





