package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Muffin", group="TeleOp")
public class Muffin extends OpMode{

    MoveHelper moveHelper;

    public void init (){
        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
       //something will happen here
    }

    public void loop(){
        moveHelper.checkTeleOp(gamepad1, gamepad2);

    }

}
