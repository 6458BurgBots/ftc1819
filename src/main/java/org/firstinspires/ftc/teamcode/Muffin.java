package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Helpers.CombineHelper;
import org.firstinspires.ftc.teamcode.Helpers.GyroHelper;
import org.firstinspires.ftc.teamcode.Helpers.LanderHelper;
import org.firstinspires.ftc.teamcode.Helpers.LiftHelper;
import org.firstinspires.ftc.teamcode.Helpers.Mark;
import org.firstinspires.ftc.teamcode.Helpers.MoveHelper;
import org.firstinspires.ftc.teamcode.Helpers.SampleHelper;
import org.firstinspires.ftc.teamcode.Helpers.SweepHelper;

@TeleOp(name="TeleOp", group="TeleOp")
public class Muffin extends OpMode {

    GyroHelper gyroHelper;
    MoveHelper moveHelper;
    LanderHelper landerHelper;
    Mark mark;
    CombineHelper combineHelper;
    SweepHelper sweepHelper;
    //MineralArmHelper mineralArmHelper;
    SampleHelper sampleHelper;
    LiftHelper liftHelper;

    private DistanceSensor sensorRange;


    public void init() {

        sensorRange = hardwareMap.get(DistanceSensor.class, "range");
        Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange;

        moveHelper = new MoveHelper(telemetry, hardwareMap);
        moveHelper.init();
        //something will happen here
        landerHelper = new LanderHelper(telemetry, hardwareMap);
        landerHelper.init();
        mark = new Mark(telemetry, hardwareMap);
        mark.init();
        //mineralArmHelper = new MineralArmHelper(telemetry,hardwareMap);
        //mineralArmHelper.init();
        sampleHelper = new SampleHelper(telemetry, hardwareMap);
        sampleHelper.init();
        combineHelper = new CombineHelper( telemetry, hardwareMap);
        combineHelper.init();
        sweepHelper = new SweepHelper(telemetry, hardwareMap);
        sweepHelper.init();
        liftHelper = new LiftHelper(telemetry, hardwareMap );
        liftHelper.init();
        gyroHelper= new GyroHelper(telemetry,hardwareMap);
        gyroHelper.init();

    }

    public void loop() {

        int heading = gyroHelper.gyroBoy.getHeading();
        // if robot tilt is greater than 10 degrees, power only the back wheels to
        // autocorrect the robot backward to avoid tilting over
        // remove this behavior if the left bumper on gamepad1 is pressed
        if ( heading >= 10 && heading < 300 && !gamepad1.left_bumper){
            moveHelper.runBLMotor(-1);
            moveHelper.runBRMotor(-1);
            moveHelper.runFLMotor(0);
            moveHelper.runFRMotor(0);
            telemetry.addData("ROBOT TILT DETECTED", "");
        } else {
            moveHelper.checkTeleOp(gamepad1, gamepad2); // Only allow joystrick control if the robot is not tilted
        }


        telemetry.addData("Heading", gyroHelper.gyroBoy.getHeading());

        landerHelper.checkTeleOp(gamepad1, gamepad2);
        //mineralArmHelper.checkTeleOp(gamepad1,gamepad2);
        if (gamepad1.right_trigger > 0) {
            moveHelper.resetEncoders();
            landerHelper.resetEncoders();

        }
        if (gamepad1.left_trigger > 0) {
            moveHelper.runWithoutEncoders();
            landerHelper.runWithoutEncoders();
        }

        if (gamepad2.y) {
            mark.open();
        }
        if (gamepad2.x) {
            mark.close();
        }

        if (gamepad2.dpad_up) {
            combineHelper.intake(-.9);

        } else if (gamepad2.dpad_down) {
            combineHelper.intake(.6);
        } else {
            combineHelper.intake(0);
        }
        if (gamepad2.right_stick_y !=0) {
            liftHelper.raise(-gamepad2.right_stick_y);
        }
        sweepHelper.showTouchStatus(telemetry);
      /*  if (gamepad2.x){
            sampleHelper.close();
        }

        if(gamepad2.y){
            sampleHelper.open();
        }*/
        telemetry.addData("range", String.format("%.01f in", sensorRange.getDistance(DistanceUnit.INCH)));
        double range = sensorRange.getDistance(DistanceUnit.INCH);

        double SHORT_DISTANCE = 1.2;
        double LONG_DISTANCE = 13.5;

        if (gamepad2.left_bumper && range >= SHORT_DISTANCE) {
            sweepHelper.in(1);
        } else if (gamepad2.right_bumper && range <= LONG_DISTANCE) {
            sweepHelper.in(-1);
        } else {
            sweepHelper.in(0);
        }
    }
}
