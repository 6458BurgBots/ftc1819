package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by WilliamsburgRobotic on 10/31/2017.
 */

public class NoOperationHelper {

    Telemetry telemetry;
    HardwareMap hardwareMap;

    NoOperationHelper(Telemetry t, HardwareMap h)
    {
        telemetry = t;
        hardwareMap = h;
    }

    public void init() {}

    public int loop(double runTime, int state){
        return state;
    }
}
