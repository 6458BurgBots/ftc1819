package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Helpers.GyroHelper;

@TeleOp(name="Gyro Test", group="TeleOp")
public class Gyro  extends OpMode {

    GyroHelper gyroHelper;

    @Override
    public void init() {
        gyroHelper= new GyroHelper(telemetry,hardwareMap);
        gyroHelper.init();
    }

    @Override
    public void init_loop() {
        gyroHelper.init_loop();
    }

    @Override
    public void loop() {
        int state = 0;
        gyroHelper.loop(getRuntime(), state);
    }
}
