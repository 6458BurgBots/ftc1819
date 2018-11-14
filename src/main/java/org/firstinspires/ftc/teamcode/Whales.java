package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous(name="Whales", group="Autonomous") // @TeleOp refers to an annotation (attribute) of the Whales class
                                            // name = "Whales" shows up in the driver's station list
                                            // group = "Autonomous" refers to which list in the driver's station
public class Whales extends OpMode{
    private static final int UPPER_LIMIT = 11584;
    private static final int LOWER_LIMIT = 0;
    private static final double SLOW_POWER = 0.5;



    MoveHelper moveHelper;
    LanderHelper landerHelper;
    protected int state;
    protected double lastTime;// double is a data type like float, but allows for more precision (more decimal places)
                                // protected so that we can only use it in other classes that have derived from whales

    public void init (){ // when creating a class derived from OpMode, you must define what happens when init is pressed
                        // you must also define "loop" as part of your framework
        moveHelper = new MoveHelper(telemetry, hardwareMap); // when parameters are purple, that means they are class variables
        moveHelper.init();
        landerHelper = new LanderHelper (telemetry,hardwareMap);
        landerHelper.init();
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
            case 40:
                landerHelper.raiseArm(SLOW_POWER);
                if(landerHelper.getPosition()>UPPER_LIMIT){
                    state = 50;
                }
                break;
            case 50:
                landerHelper.raiseArm(0);
                state = 190;
                break;

            case 190:
                moveHelper.resetEncoders();
                advanceToStateAfterTime(200, .5);
                break;

            case 200:
                moveHelper.runUsingEncoders();
                advanceToStateAfterTime(210, .5);
                break;

            case 210: // move out from landing spot
               moveHelper.driveForward(.5);
                if (moveHelper.getEncoderValue() > 800) {
                    lastTime = getRuntime();
                    state = 220;
               }
                //advanceToStateAfterTime(20, 1);
                break;
            case 220: // stop for .25 seconds
                moveHelper.driveForward(0);
                if (getRuntime() > lastTime + .25) {
                    lastTime = getRuntime();
                    state = 230;
                }
                break;
            case 230: // turn left for .5 seconds
                moveHelper.turn(-.5);
                if (getRuntime() > lastTime + 0.5) {
                    lastTime = getRuntime();
                    state = 240;
                }
                break;
            case 240: // stop turning for .3 seconds
                moveHelper.turn(0);
                if (getRuntime() > lastTime + .30) {
                    lastTime = getRuntime();
                    state = 250;
                }
                break;

            case 250: // forward for 1.5 seconds
                moveHelper.driveForward(1);
                if (moveHelper.getEncoderValue() > 2900)  {
                    lastTime = getRuntime();
                    state = 260;
                }
                break;
            case 260: // stop for .25 seconds
                moveHelper.driveForward(0);
                if (getRuntime() > lastTime + .25) {
                    lastTime = getRuntime();
                    state = 270;
                }
                break;
            case 270: // turn left for .25 seconds
                moveHelper.turn(-.25);
                if (getRuntime() > lastTime + 0.29) {
                    lastTime = getRuntime();
                    state = 280;
                }
                break;
            case 280: // stop for .3
                moveHelper.turn(0);
                if (getRuntime() > lastTime + .30) {
                    lastTime = getRuntime();
                    state = 290;
                }
                break;
            case 290: // forward for 2 seconds
                moveHelper.driveForward(1.5);
                if (getRuntime() > lastTime + 2) {
                    lastTime = getRuntime();
                    state =300;
                }
                break;
            case 300: // stop for .25 seconds
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
