package org.firstinspires.ftc.teamcode.Helpers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helpers.NoOperationHelper;

public class LiftHelper extends NoOperationHelper {
    private DcMotor liftMotor;
    private static final int UPPER_LIMIT = 3600;
    private double desiredPosition = 0;

    public LiftHelper(Telemetry t, HardwareMap h) {
        super(t, h);
    }


    public void init() {
        liftMotor = hardwareMap.dcMotor.get("Lift");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    public int getPosition() {
        return liftMotor.getCurrentPosition();
    }
    public int desiredPosition() {
        return liftMotor.getCurrentPosition();
    }

    public void setPower(double power) {
        liftMotor.setPower(power);
    }

    public void raise(double raiseAmount){
        desiredPosition += raiseAmount * 150;
        liftMotor.setTargetPosition((int)desiredPosition);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(1);
    }

   public void holdPosition() {

       liftMotor.setTargetPosition(liftMotor.getCurrentPosition());
       liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       liftMotor.setPower(1);
   }

   public void resetHold () {
       liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
   }
}






