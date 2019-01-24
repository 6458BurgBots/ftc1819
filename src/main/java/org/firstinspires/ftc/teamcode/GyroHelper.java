package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;



@TeleOp(name = "Gyro", group = "Sensor")

public class GyroHelper extends LinearOpMode {

    GyroSensor gyro;             // Declare ModernRobotics Gyro

    int heading = 0;            // Variable used for the heading of the gyro


    @Override
    public void runOpMode() throws InterruptedException {


        gyro = hardwareMap.gyroSensor.get("gyro");


        // Calibrate the gyro
        telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();
        gyro.calibrate();

        // While the gyro is calibrating wait
        while (gyro.isCalibrating())  {
            Thread.sleep(50);
            idle();
        }

        // Tell us when the gyro is done calibrating
        telemetry.addData(">", "Gyro Calibrated.");

        // Wait to hit the start button
        waitForStart();


        while (opModeIsActive()) {
            // Get the current heading of the robot
            heading = gyro.getHeading();

            telemetry.addData("X", heading);
            telemetry.update();
            idle();

        }
    }
}
