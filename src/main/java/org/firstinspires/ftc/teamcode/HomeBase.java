/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

/**
 * This OpMode illustrates the basics of using the Vuforia localizer to determine
 * positioning and orientation of robot on the FTC field.
 * The code is structured as a LinearOpMode
 *
 * Vuforia uses the phone's camera to inspect it's surroundings, and attempt to locate target images.
 *
 * When images are located, Vuforia is able to determine the position and orientation of the
 * image relative to the camera.  This sample code than combines that information with a
 * knowledge of where the target images are on the field, to determine the location of the camera.
 *
 * This example assumes a "diamond" field configuration where the red and blue alliance stations
 * are adjacent on the corner of the field furthest from the audience.
 * From the Audience perspective, the Red driver station is on the right.
 * The two vision target are located on the two walls closest to the audience, facing in.
 * The Stones are on the RED side of the field, and the Chips are on the Blue side.
 *
 * A final calculation then uses the location of the camera on the robot to determine the
 * robot's location and orientation on the field.
 *
 * @see VuforiaLocalizer
 * @see VuforiaTrackableDefaultListener
 * see  ftc_app/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
public abstract class HomeBase extends OpMode {
    // this class declares all of our variables that we will use in almost every program so we don't
    // have to put them in each program we create

    public final static double JAILER_MIN_RANGE = .5;
    public final static double JAILER_MAX_RANGE = 2;
    public final static double JAILER_HOME = JAILER_MIN_RANGE;
    public final static double JAILER_SPEED = .01;

    protected DcMotor FLMotor;
    protected DcMotor FRMotor;
    protected DcMotor BLMotor;
    protected DcMotor BRMotor;
 //   protected DcMotor Chastity;
   // protected Servo Jailer;
   // protected DcMotor Flipper;
   // protected DcMotor Sweep;
    protected int state;

    private double jailerPosition = JAILER_HOME;  // Servo safe position


    public HomeBase() {
    msStuckDetectInit=10000;
    }
    @Override
    public void init()
    {
        FLMotor = hardwareMap.dcMotor.get("LF");
        FRMotor = hardwareMap.dcMotor.get("RF");
        BLMotor = hardwareMap.dcMotor.get("LB");
        BRMotor = hardwareMap.dcMotor.get("RB");
      //  Chastity = hardwareMap.dcMotor.get("Conveyor");
        //Jailer = hardwareMap.servo.get("Blocker");
        //Flipper = hardwareMap.dcMotor.get("Flipper");
        //Sweep = hardwareMap.dcMotor.get("Sweep");

        FRMotor.setDirection(DcMotor.Direction.REVERSE);
        BRMotor.setDirection(DcMotor.Direction.REVERSE);
        FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
     //   Chastity.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       // Flipper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       // Sweep.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

   /* protected void closeBlockArm(double direction) {
        double speed = JAILER_SPEED;
        if (direction < 0) {
            speed = -JAILER_SPEED;
        }
        jailerPosition += speed;
        if (jailerPosition > JAILER_MAX_RANGE) {
            jailerPosition = JAILER_MAX_RANGE;
        }
        if (jailerPosition < JAILER_MIN_RANGE) {
            jailerPosition = JAILER_MIN_RANGE;
        }
        Jailer.setPosition(jailerPosition);
        telemetry.addData("Jailer position: ", jailerPosition);
    }*/

    protected void drive(double lx,double ly){
        //telemetry.addData("Drive input (lx,ly): ", lx + "," + ly);

        double fl = ly + lx;
        double fr = ly - lx;
        double bl = ly - lx;
        double br = ly + lx;

        //String output = String.format("%1$.3f,%1$.3f,%1$.3f,%1$.3f",fl,fr,bl,br);
        //telemetry.addData("Driving (fl,fr,bl,br): ", output);

        FLMotor.setPower(fl);
        FRMotor.setPower(fr);
        BLMotor.setPower(bl);
        BRMotor.setPower(br);

    }

    protected void turn(double rx){

        FLMotor.setPower(rx);
        FRMotor.setPower(-rx);
        BLMotor.setPower(rx);
        BRMotor.setPower(-rx);
    }

    protected void setState(int s) {
        state = s;
        telemetry.addData("state set to ", state);
    }
    /**
     * A simple utility that extracts positioning information from a transformation matrix
     * and formats it in a form palatable to a human being.
     */
    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

    protected void complicatedDrive(double x, double y, double angle){
        double tx;
        double ty;

        ty = Math.sin(angle)*y+Math.cos(angle)*x;
        tx = (-Math.cos(angle)*y)+Math.sin(angle)*x;
        //telemetry.addData("input x y: ", x+","+y);
        //telemetry.addData("input rot: ", angle);
        //telemetry.addData("drive x,y: ", tx+","+ty);
        FLMotor.setPower(ty+tx);
        FRMotor.setPower(ty-tx);
        BLMotor.setPower(ty-tx);
        BRMotor.setPower(ty+tx);

    }


}
