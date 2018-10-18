package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Whales", group="Autonomous")
public class Whales extends OpMode{

    MoveHelper moveHelper;
    protected int state;
    double lastTime;

    public void init (){
        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
        state = 0;
        lastTime = 0;
        //something will happen here
    }

    public void loop(){

        switch (state) {
            case 0:
                lastTime = getRuntime();
                state = 10;
                break;
            case 10:
                moveHelper.driveForward(.5);
                if (getRuntime() > lastTime + 2) {
                    lastTime = getRuntime();
                    state = 20;
                }
                break;
            case 20:
                moveHelper.driveForward(0);
                if (getRuntime() > lastTime + .25) {
                    lastTime = getRuntime();
                    state = 30;
                }
                break;
            case 30:
                moveHelper.turn(-.5);
                if (getRuntime() > lastTime + 1.5) {
                    lastTime = getRuntime();
                    state = 40;
                }
                break;
            case 40:
                moveHelper.turn(0);
                if (getRuntime() > lastTime + .25) {
                    lastTime = getRuntime();
                    state = 50;
                }
                break;
            case 50:
                moveHelper.driveForward(.5);
                if (getRuntime() > lastTime + 1) {
                    lastTime = getRuntime();
                    state = 60;
                }
                break;
            case 60:
                moveHelper.driveForward(0);
                if (getRuntime() > lastTime + .25) {
                    lastTime = getRuntime();
                    state = 70;
                }
                break;
        }


        telemetry.addData("State", state);
        telemetry.addData("BR Encoder", moveHelper.GetBRMotorPosition());
        telemetry.update();




    }

}
