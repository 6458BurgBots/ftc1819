package org.firstinspires.ftc.teamcode.Old;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Helpers.LanderHelper;
import org.firstinspires.ftc.teamcode.Helpers.Mark;
import org.firstinspires.ftc.teamcode.Helpers.MoveHelper;

@Autonomous(name="NonSampleDepot", group="Autonomous") // @TeleOp refers to an annotation (attribute) of the Whales class
    // name = "Nhawrwahwl" shows up in the driver's station list
    // group = "Autonomous" refers to which list in the driver's station
    public class Nhawrwahwl extends OpMode {
        private static final int UPPER_LIMIT = 11584;
        private static final int LOWER_LIMIT = 0;
        private static final double SLOW_POWER = 0.5;

        MoveHelper moveHelper;
        LanderHelper landerHelper;
        Mark markHelper;
        protected int state;
        protected double lastTime;// double is a data type like float, but allows for more precision (more decimal places)
        // protected so that we can only use it in other classes that have derived from whales
        private boolean doCrater=true;

        public void init() { // when creating a class derived from OpMode, you must define what happens when init is pressed
            // you must also define "loop" as part of your framework
            moveHelper = new MoveHelper(telemetry, hardwareMap); // when parameters are purple, that means they are class variables
            moveHelper.init();
            landerHelper = new LanderHelper(telemetry, hardwareMap);
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

        public void init_loop() {
            if (gamepad1.y){
                doCrater=true;
            }
            if (gamepad1.a){
                doCrater=false;
            }
            telemetry.addData("Do Crater",doCrater);
        }


            public void loop() {
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
                    moveHelper.runMotorsToPosition(400, -400, -400, 400);
                    advanceToStateAfterTime(190, .75);
                    break;

                case 190: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(200, 0.1);
                    break;

                case 200://first move out towards depot
                    moveHelper.runMotorsToPosition(1300,1300,1300,1300);
                    advanceToStateAfterTime(210, 1.8);
                    break;

                case 210: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(220, 0.1);
                    break;

                case 220://turn left
                    moveHelper.runMotorsToPosition(-1800,1800,-1800,1800);
                    advanceToStateAfterTime(230, 1.8);
                    break;

                case 230: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(235, 0.1);
                    break;

                case 235:// forward around the minerals
                    moveHelper.runMotorsToPosition(2000,2000,2000,2000);
                    advanceToStateAfterTime(237, 2.0);
                    break;

                case 237: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(240, 0.1);
                    break;

                case 240://turn right towards the wall
                    moveHelper.runMotorsToPosition(900,-900,900,-900);
                    advanceToStateAfterTime(250, 1.8);
                    break;

                case 250: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(260, 0.1);
                    break;

                case 260://move towards the wall
                    moveHelper.runMotorsToPosition(1300,1300,1300,1300);
                    advanceToStateAfterTime(270, 2.0);
                    break;

                case 270: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(280, 0.1);
                    break;

                case 280://turn right towards the depot
                    moveHelper.runMotorsToPosition(1800,-1800,1800,-1800);
                    advanceToStateAfterTime(290, 1.8);
                    break;

                case 290: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(300, 0.1);
                    break;

                case 300:// move towards the depot
                    moveHelper.runMotorsToPosition(3830,3830,3830,3830);
                    advanceToStateAfterTime(310, 2.0);
                    break;

                case 310: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(320, 0.1);
                    break;

                case 320://turn left away from the crater
                    moveHelper.runMotorsToPosition(-2000,2000,-2000,2000);
                    advanceToStateAfterTime(330, 1.8);
                    break;

                case 330: //stop
                    moveHelper.resetEncoders();
                    advanceToStateAfterTime(400, 0.1);
                    break;

                case 400: // shove marker off
                    markHelper.open();
                    advanceToStateAfterTime(410, 1);
                    break;

                case 410: // close servo arm
                    markHelper.close();
                    if(doCrater) {

                        advanceToStateAfterTime(500, .1);
                    }
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
        }
    }

