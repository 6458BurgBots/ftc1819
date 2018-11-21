package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

    @Autonomous(name="Nhawrwahwl", group="Autonomous") // @TeleOp refers to an annotation (attribute) of the Whales class
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

            }
        }
    }

