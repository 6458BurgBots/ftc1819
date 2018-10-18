package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Goldfish", group="Autonomous")
public class Goldfish extends OpMode{

    MoveHelper moveHelper;
    int BRInitialPosition;
    int BRTargetPosition;

    public void init (){
        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
        BRInitialPosition=moveHelper.GetBRMotorPosition();
        BRTargetPosition=BRInitialPosition+3000;

        //something will happen here
    }

    public void loop(){
        if (moveHelper.GetBRMotorPosition()<BRTargetPosition){
            moveHelper.runBRMotor(.5);
            moveHelper.runBLMotor(.5);
            moveHelper.runFRMotor(.5);
            moveHelper.runFLMotor(.5);
        }
        else {
            moveHelper.runBRMotor(0);
            moveHelper.runBLMotor(0);
            moveHelper.runFRMotor(0);
            moveHelper.runFLMotor(0);
        }
        telemetry.addData("BR Encoder", moveHelper.GetBRMotorPosition());
        telemetry.update();




    }

}
