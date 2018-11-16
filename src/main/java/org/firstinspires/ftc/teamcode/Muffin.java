package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Muffin", group="TeleOp")
public class Muffin extends OpMode{

    MoveHelper moveHelper;
    LanderHelper landerHelper;
    public void init (){
        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
       //something will happen here
        landerHelper = new LanderHelper(telemetry, hardwareMap);
        landerHelper.init();
    }

    public void loop(){
        landerHelper.checkTeleOp(gamepad1, gamepad2);
        moveHelper.checkTeleOp(gamepad1, gamepad2);
        if (gamepad1.right_trigger > 0){
            moveHelper.resetEncoders();
            landerHelper.resetEncoders();

        }
        if (gamepad1.left_trigger > 0){
            moveHelper.runWithoutEncoders();
            landerHelper.runWithoutEncoders();
        }
    }

}
