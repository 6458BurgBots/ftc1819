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
                state = 190;
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

            case 190: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(200, 0.5);
                break;

            case 200://first move out towards crater
                moveHelper.runMotorsToPosition(1600,1600,1600,1600);
                advanceToStateAfterTime(210, 2.0);
                break;

            case 210: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(220, 0.5);
                break;

            case 220://turn left
                moveHelper.runMotorsToPosition(-1700,1700,-1700,1700);
                advanceToStateAfterTime(230, 2.0);
                break;

            case 230: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(240, 0.5);
                break;

            case 240://forward towards side wall
                moveHelper.runMotorsToPosition(2600,2600,2600,2600);
                advanceToStateAfterTime(250, 2.0);
                break;

            case 250: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(260, 0.5);
                break;

            case 260://turn to face depot
                moveHelper.runMotorsToPosition(-1300,1300,-1300,1300);
                advanceToStateAfterTime(270, 2.0);
                break;

            case 270: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(280, 0.5);
                break;

            case 280://move forward into depot
                moveHelper.runMotorsToPosition(5550,5550,5550,5550);
                advanceToStateAfterTime(290, 3.5);
                break;

            case 290: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(666, 0.5);
                break;
    }


        telemetry.addData("State", state);
        moveHelper.showEncoderValues();
        telemetry.update();




    }

}
