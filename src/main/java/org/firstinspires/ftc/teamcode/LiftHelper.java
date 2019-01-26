package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LiftHelper extends NoOperationHelper {
    private DcMotor liftMotor;

    LiftHelper(Telemetry t, HardwareMap h)
    {
        super(t, h);
    }


    public void init() {
        liftMotor = hardwareMap.dcMotor.get("Lift");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public int getPosition(){
        return liftMotor.getCurrentPosition();
    }
    public void setPower(double power){
        liftMotor.setPower(power);
    }
}





