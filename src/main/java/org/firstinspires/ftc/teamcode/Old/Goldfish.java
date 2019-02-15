package org.firstinspires.ftc.teamcode.Old;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Goldfish", group="Autonomous")
public class Goldfish extends OpMode{

    //MoveHelper moveHelper;
    private static final double SLOW_POWER = 0.5;
    private static final int UPPER_LIMIT = 11584;
    private static final int LOWER_LIMIT = 0;
    int BRInitialPosition;
    int BRTargetPosition;
    protected DcMotor FLMotor;

    public void init (){
        //moveHelper = new MoveHelper(telemetry, hardwareMap);
        //moveHelper.init();
        FLMotor = hardwareMap.dcMotor.get("test");
        BRInitialPosition=FLMotor.getCurrentPosition();
        //BRTargetPosition=BRInitialPosition+3000;

        //something will happen here
    }

    public void loop(){
//        if (moveHelper.GetBRMotorPosition()<BRTargetPosition){
//            moveHelper.runBRMotor(.5);
//            moveHelper.runBLMotor(.5);
//            moveHelper.runFRMotor(.5);
//            moveHelper.runFLMotor(.5);
//        }
//        else {
//            moveHelper.runBRMotor(0);
//            moveHelper.runBLMotor(0);
//            moveHelper.runFRMotor(0);
//            moveHelper.runFLMotor(0);
//
        if (gamepad1.b) {
            FLMotor.setPower(SLOW_POWER);
        } else if (gamepad1.a){
            FLMotor.setPower(-SLOW_POWER);
        }else {
            FLMotor.setPower(0);
        }
        if (gamepad1.y) {
            if (FLMotor.getCurrentPosition()<UPPER_LIMIT){
                FLMotor.setPower(SLOW_POWER);
            }
        }
        if (gamepad1.x) {
            if (FLMotor.getCurrentPosition()>LOWER_LIMIT){
                FLMotor.setPower(-SLOW_POWER);
            }
        }
        telemetry.addData("Encoder", FLMotor.getCurrentPosition());
        telemetry.update();
    }
}
