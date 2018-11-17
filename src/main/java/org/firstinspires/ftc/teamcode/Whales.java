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
    Mark markHelper;
    protected int state;
    protected double lastTime;// double is a data type like float, but allows for more precision (more decimal places)
                                // protected so that we can only use it in other classes that have derived from whales

    public void init (){ // when creating a class derived from OpMode, you must define what happens when init is pressed
                        // you must also define "loop" as part of your framework
        moveHelper = new MoveHelper(telemetry, hardwareMap); // when parameters are purple, that means they are class variables
        moveHelper.init();
        landerHelper = new LanderHelper (telemetry,hardwareMap);
        landerHelper.init();
        markHelper = new Mark(telemetry, hardwareMap);
        markHelper.init();
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
                state = 40;
                break;

            case 40:
                landerHelper.resetEncoders();
                advanceToStateAfterTime(50, 0.1);
                break;

            case 50: //lowers arm/robot
                landerHelper.runMotorsToPosition(9950);
                advanceToStateAfterTime(60, 6);
                break;

            case 60: //reset encoders/stop
                landerHelper.resetEncoders();
                moveHelper.resetEncoders();
                advanceToStateAfterTime(70, 0.1);
                break;

            case 70: //shifts to the robot's right
                moveHelper.runMotorsToPosition(400,-400,-400,400);
                advanceToStateAfterTime(190, .75);
                break;

            case 190: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(200, 0.1);
                break;

            case 200://first move out towards crater
                moveHelper.runMotorsToPosition(1300,1300,1300,1300);
                advanceToStateAfterTime(210, 2.0);
                break;

            case 210: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(220, 0.1);
                break;

            case 220://turn left
                moveHelper.runMotorsToPosition(-1700,1700,-1700,1700);
                advanceToStateAfterTime(230, 2.0);
                break;

            case 230: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(240, 0.1);
                break;

            case 240://forward towards side wall
                moveHelper.runMotorsToPosition(2600,2600,2600,2600);
                advanceToStateAfterTime(250, 1.75);
                break;

            case 250: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(260, 0.1);
                break;

            case 260://turn to face depot
                moveHelper.runMotorsToPosition(-1100,1100,-1100,1100);
                advanceToStateAfterTime(270, 1.75);
                break;

            case 270: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(280, 0.1);
                break;

            case 280://move forward into depot
                moveHelper.runMotorsToPosition(5550,5550,5550,5550);
                advanceToStateAfterTime(290, 3.0);
                break;

            case 290: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(400, 0.1);
                break;

            case 400: // shove marker off
                markHelper.open();
                advanceToStateAfterTime(410, 1);
                break;

            case 410: // close servo arm
                markHelper.close();
                advanceToStateAfterTime(500, .1);
                break;

            case 500: // back up into crater
                moveHelper.runMotorsToPosition(-8000,-8000,-8000,-8000);
                advanceToStateAfterTime(510, 5.5);
                break;
            case 510: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(999, 0.1);
                break;
        }


        telemetry.addData("State", state);
        moveHelper.showEncoderValues();
        telemetry.update();




    }

}
