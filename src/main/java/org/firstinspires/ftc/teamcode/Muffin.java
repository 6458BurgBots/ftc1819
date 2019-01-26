package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOp", group="TeleOp")
public class Muffin extends OpMode {

    MoveHelper moveHelper;
    LanderHelper landerHelper;
    Mark mark;
    CombineHelper combineHelper;
    SweepHelper sweepHelper;
    //MineralArmHelper mineralArmHelper;
    SampleHelper sampleHelper;

    public void init() {
        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
        //something will happen here
        landerHelper = new LanderHelper(telemetry, hardwareMap);
        landerHelper.init();
        mark = new Mark(telemetry, hardwareMap);
        mark.init();
        //mineralArmHelper = new MineralArmHelper(telemetry,hardwareMap);
        //mineralArmHelper.init();
        sampleHelper = new SampleHelper(telemetry, hardwareMap);
        sampleHelper.init();
        combineHelper = new CombineHelper(telemetry, hardwareMap);
        combineHelper.init();
        sweepHelper = new SweepHelper(telemetry, hardwareMap);
        sweepHelper.init();


    }

    public void loop() {
        landerHelper.checkTeleOp(gamepad1, gamepad2);
        moveHelper.checkTeleOp(gamepad1, gamepad2);
        //mineralArmHelper.checkTeleOp(gamepad1,gamepad2);
        if (gamepad1.right_trigger > 0) {
            moveHelper.resetEncoders();
            landerHelper.resetEncoders();

        }
        if (gamepad1.left_trigger > 0) {
            moveHelper.runWithoutEncoders();
            landerHelper.runWithoutEncoders();
        }

        if (gamepad2.y) {
            mark.open();
        }
        if (gamepad2.x) {
            mark.close();
        }
        if (gamepad2.left_bumper) {
            sweepHelper.in(-1);
        } else if (gamepad2.right_bumper) {
            sweepHelper.in(1);
        } else {
            sweepHelper.in(0);
        }
        if (gamepad2.dpad_up) {
            combineHelper.intake(-.9);

        } else if (gamepad2.dpad_down) {
            combineHelper.intake(.6);
        } else {
            combineHelper.intake(0);



      /*  if (gamepad2.x){
            sampleHelper.close();
        }

        if(gamepad2.y){
            sampleHelper.open();
        }*/
        }

    }
}
