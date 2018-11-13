package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name="Whales", group="Autonomous") // @TeleOp refers to an annotation (attribute) of the Whales class
                                            // name = "Whales" shows up in the driver's station list
                                            // group = "Autonomous" refers to which list in the driver's station
public class Whales extends OpMode{

    MoveHelper moveHelper;
    protected int state;
    protected double lastTime; // double is a data type like float, but allows for more precision (more decimal places)
                                // protected so that we can only use it in other classes that have derived from whales

    public void init (){ // when creating a class derived from OpMode, you must define what happens when init is pressed
                        // you must also define "loop" as part of your framework
        moveHelper = new MoveHelper(telemetry, hardwareMap); // when parameters are purple, that means they are class variables
        moveHelper.init();
        state = 0;
        lastTime = 0;
        //something will happen here
    }

    // Noticed that each case was similar, so created a procedure called advancedToStateAfterTime
    // parameters include the newState, which refers to the new value being assigned to state at end of duration
    // and duration, which refers to the amount of time before moving to the new state
    private void advanceToStateAfterTime(int newState, double duration) {

        if (getRuntime() > lastTime + duration) {
            lastTime = getRuntime();
            state = newState;
        }
    }


    public void loop(){

        switch (state) {
            case 0:
                lastTime = getRuntime();
                state = 5;
                break;

            case 5:
                moveHelper.resetEncoders();
                advanceToStateAfterTime(6, .5);
                break;

            case 6:
                moveHelper.runUsingEncoders();
                advanceToStateAfterTime(10, .5);
                break;

            case 10: // move out from landing spot
               moveHelper.driveForward(.5);
                if (moveHelper.getEncoderValue() > 800) {
                    lastTime = getRuntime();
                    state = 20;
               }
                //advanceToStateAfterTime(20, 1);
                break;
            case 20: // stop for .25 seconds
                moveHelper.driveForward(0);
                if (getRuntime() > lastTime + .25) {
                    lastTime = getRuntime();
                    state = 30;
                }
                break;
            case 30: // turn left for .5 seconds
                moveHelper.turn(-.5);
                if (getRuntime() > lastTime + 0.5) {
                    lastTime = getRuntime();
                    state = 40;
                }
                break;
            case 40: // stop turning for .3 seconds
                moveHelper.turn(0);
                if (getRuntime() > lastTime + .30) {
                    lastTime = getRuntime();
                    state = 50;
                }
                break;

            case 50: // forward for 1.5 seconds
                moveHelper.driveForward(1);
                if (moveHelper.getEncoderValue() > 2900)  {
                    lastTime = getRuntime();
                    state = 60;
                }
                break;
            case 60: // stop for .25 seconds
                moveHelper.driveForward(0);
                if (getRuntime() > lastTime + .25) {
                    lastTime = getRuntime();
                    state = 70;
                }
                break;
            case 70: // turn left for .25 seconds
                moveHelper.turn(-.25);
                if (getRuntime() > lastTime + 0.29) {
                    lastTime = getRuntime();
                    state = 80;
                }
                break;
            case 80: // stop for .3
                moveHelper.turn(0);
                if (getRuntime() > lastTime + .30) {
                    lastTime = getRuntime();
                    state = 90;
                }
                break;
            case 90: // forward for 2 seconds
                moveHelper.driveForward(1.5);
                if (getRuntime() > lastTime + 2) {
                    lastTime = getRuntime();
                    state = 100;
                }
                break;
            case 100: // stop for .25 seconds
                moveHelper.driveForward(0);
                if (getRuntime() > lastTime + .25) {
                    lastTime = getRuntime();
                    state = 999;
                }
                break;
        }


        telemetry.addData("State", state);
        moveHelper.showEncoderValues();
        telemetry.update();




    }

}
