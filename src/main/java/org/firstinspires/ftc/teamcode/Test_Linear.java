/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;

/**
 * This OpMode uses the common HardwareK9bot class to define the devices on the robot.
 * All device access is managed through the HardwareK9bot class. (See this class for device names)
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a basic Tank Drive Teleop for the K9 bot
 * It raises and lowers the arm using the Gampad Y and A buttons respectively.
 * It also opens and closes the claw slowly using the X and B buttons.
 *
 * Note: the configuration of the servos is such that
 * as the arm servo approaches 0, the arm position moves up (away from the floor).
 * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Test", group="Test")
public class Test_Linear extends LinearOpMode {

    MoveHelper moveHelper;
    SampleHelper sampleHelper;
    Mark markHelper;
    LanderHelper landerHelper;
    CombineHelper combineHelper;
    SweepHelper sweepHelper;
    //TouchHelper touchHelper;
    LiftHelper liftHelper;
    double sampleArmPosition = SampleHelper.SAMPLE_SERVO_CLOSED;
    public final static double SAMPLE_ARM_SCALE = .1;



    @Override
    public void runOpMode() {
        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
        sampleHelper = new SampleHelper(telemetry,hardwareMap);
        sampleHelper.init();
        markHelper = new Mark(telemetry, hardwareMap);
        markHelper.init();
        landerHelper = new LanderHelper (telemetry,hardwareMap);
        landerHelper.init();
        combineHelper = new CombineHelper(telemetry, hardwareMap );
        combineHelper.init();
        sweepHelper = new SweepHelper(telemetry, hardwareMap );
        sweepHelper.init();
        //touchHelper = new TouchHelper(telemetry, hardwareMap );
        //touchHelper.init();
        liftHelper = new LiftHelper(telemetry, hardwareMap );
        liftHelper.init();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if(gamepad1.x){
                moveHelper.runFLMotor(.3);

            }else {
                moveHelper.runFLMotor(0);
            }

            if(gamepad1.a){
                moveHelper.runBLMotor(.3);

            }else {
                moveHelper.runBLMotor(0);
            }

            if(gamepad1.y){
                moveHelper.runFRMotor(.3);

            }else {
                moveHelper.runFRMotor(0);
            }

            if(gamepad1.b){
                moveHelper.runBRMotor(.3);

            }else {
                moveHelper.runBRMotor(0);
            }
            if (gamepad1.right_trigger > 0) {           //Sample Arm
                sampleHelper.open();
            }
            if (gamepad1.left_trigger > 0) {
                sampleHelper.close();
            }

            if (gamepad1.right_bumper) {           //Mark Arm
                markHelper.open();
            }
            if (gamepad1.left_bumper) {
                markHelper.close();
            }
            if (gamepad1.left_stick_button){

                        moveHelper.resetEncoders();
            }
            if (gamepad1.right_stick_button){

                landerHelper.resetEncoders();
            }

            if (gamepad2.a) {
                landerHelper.raiseArm(.5);
            } else if (gamepad2.b) {
                landerHelper.raiseArm(-.5);
            } else {
                landerHelper.raiseArm(0);
            }

            if(gamepad2.x){
                combineHelper.intake(.9);
                telemetry.addData("CRSERVO", ".9");
            }else if(gamepad2.y){
                combineHelper.intake(-.9);
                telemetry.addData("CRSERVO", "-.9");

            }else if(gamepad2.left_stick_button){
                combineHelper.intake(-.9);
                telemetry.addData("CRSERVO", "-.3");

            }else{
                combineHelper.intake(0);
                telemetry.addData("CRSERVO", "0");
            }
            if (gamepad2.left_trigger > 0) {
                liftHelper.setPower(gamepad2.left_trigger);
            } else if (gamepad2.right_trigger > 0) {
                liftHelper.setPower(-gamepad2.right_trigger);
            } else {
                liftHelper.setPower(0);
            }



            sampleArmPosition += gamepad2.left_stick_y*SAMPLE_ARM_SCALE;
            sampleHelper.moveSampleServo(sampleArmPosition);





            telemetry.addData("backLeft", moveHelper.GetBLMotorPosition());
            telemetry.addData("backRight", moveHelper.GetBRMotorPosition());
            telemetry.addData("frontLeft", moveHelper.GetFLMotorPosition());
            telemetry.addData("frontRight", moveHelper.GetFRMotorPosition());
            telemetry.addData( "arm",landerHelper.getPosition()); //Fix Fix Fix
            telemetry.addData("sample arm", sampleArmPosition);
            telemetry.addData("lift arm", liftHelper.getPosition());
            telemetry.update();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
