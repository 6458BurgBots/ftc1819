package org.firstinspires.ftc.teamcode.Old;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Helpers.LanderHelper;
import org.firstinspires.ftc.teamcode.Helpers.Mark;
import org.firstinspires.ftc.teamcode.Helpers.MoveHelper;
import org.firstinspires.ftc.teamcode.Helpers.SampleHelper;

@Autonomous(name="OldSampleCrater", group="Autonomous") // @TeleOp refers to an annotation (attribute) of the Whales class
                                            // name = "Whales" shows up in the driver's station list
                                            // group = "Autonomous" refers to which list in the driver's station
public class GoldieBlocks extends OpMode{
    private static final int UPPER_LIMIT = 11584;
    private static final int LOWER_LIMIT = 0;
    private static final double SLOW_POWER = 0.5;



    MoveHelper moveHelper;
    LanderHelper landerHelper;
    Mark markHelper;
    SampleHelper sampleHelper;
    private GoldAlignDetector detector;
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
        sampleHelper = new SampleHelper(telemetry,hardwareMap);
        sampleHelper.init();
        state = 0;
        lastTime = 0;

        //from GoldAlignExample, DogeCV
        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");

        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!
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
                moveHelper.runMotorsToPosition(1200,1200,1200,1200);
//                moveHelper.runMotorsToPosition(1300,1300,1300,1300);  // Safe mineral behavior
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

            case 240: //move back
                moveHelper.runMotorsToPosition(-1900,-1900,-1900,-1900);
                advanceToStateAfterTime(250, 2.0);
                break;

            case 250: //stop
                moveHelper.resetEncoders();
                //moveHelper.runWithoutEncoders();
                advanceToStateAfterTime(260, 0.1);
                break;

            case 260:
                moveHelper.encoderPowerLevel = 0; // because this is zero, we are just setting the position into the encoders
                moveHelper.runMotorsToPosition(5400,5400,5400,5400); //this is the set position
//                moveHelper.runMotorsToPosition(5600,5600,5600,5600); // safe mineral behavior
                state=270;
                break;

            case 270: //check the 1st mineral
                if(detector.isFound()){
                    state = 280;
                } else {
                    moveHelper.encoderPowerLevel = .3;
                    moveHelper.continueToPosition();  // Now run to the position that was set in step 254
                    advanceToStateAfterTime(400, 5); // time out in case we don't see anything, ever
                }
                //else {
                //    state = 260;
                //}
                break;

            case 280: //lower arm
                sampleHelper.open();
                advanceToStateAfterTime(290, 1);
                telemetry.addData("Arm Status: ", "lowering");
                break;

            case 290: //drive forward
                moveHelper.encoderPowerLevel = .5;
                moveHelper.continueToPosition();   // Now run to the position that was set in step 254
                advanceToStateAfterTime(300, 1);  // for only one second
                telemetry.addData("Arm Status: ", "forward");
                break;

            case 300: //stop
                moveHelper.driveForward(0);  // force a stop
                advanceToStateAfterTime(310, 0.1);
                break;

            case 310: //raise arm
                sampleHelper.close();
                advanceToStateAfterTime(400, 1);
                telemetry.addData("Arm Status: ", "raising");
                break;

            case 400: //Continue to fixed location before turning to depot
                moveHelper.encoderPowerLevel = 1;
                moveHelper.continueToPosition();// Now finish running to the position that was set in step 254
                advanceToStateAfterTime(410, 3);
                telemetry.addData("Arm Status: ", "continuing");
                break;

            case 410: //stop/reset encoders
                moveHelper.resetEncoders();
                moveHelper.driveForward(0);  // force a stop
                advanceToStateAfterTime(420, 0.1);
                break;

            case 420: //turn towards depot
                moveHelper.runMotorsToPosition(-1100,1100,-1100,1100);
                advanceToStateAfterTime(430, 1.75);
                break;

            case 430: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(440, 0.1);
                break;

            case 440://move forward into depot
                moveHelper.runMotorsToPosition(5000,5000,5000,5000);
                advanceToStateAfterTime(450, 3.0);
                break;

            case 450: //stop
                moveHelper.resetEncoders();
                advanceToStateAfterTime(460, 0.1);
                break;

            case 460: // shove marker off
                markHelper.open();
                advanceToStateAfterTime(470, 1);
                break;

            case 470: // close servo arm
                markHelper.close();
                advanceToStateAfterTime(480, .1);
                break;

            case 480: // back up into crater
                moveHelper.runMotorsToPosition(-3000,-3000,-3000,-3000);
                advanceToStateAfterTime(999, 5.5);
                break;


                // Next you need to copy all steps from 260 and on from WHALES.
            // Obviously renumber them starting with 420 here.  KEEP COMMENTING!
            // 260 needs to be adjusted, you probably won't turn quite as far.
            // Just add one step at a time until you're in the depot

            // ALSO, please re-test normal whales and normal nhawrwahwl to make sure we didn't kill those programs accidentally.
            // to make sure the robot doesn't explode or something
        }


        telemetry.addData("State", state);
        moveHelper.showEncoderValues();
        telemetry.addData("IsAligned" , detector.getAligned()); // Is the bot aligned with the gold mineral?
        telemetry.addData("IsFound" , detector.isFound()); // Is the mineral in the screen freame?
        telemetry.addData("X Pos" , detector.getXPosition()); // Gold X position.
        telemetry.update();




    }

}
