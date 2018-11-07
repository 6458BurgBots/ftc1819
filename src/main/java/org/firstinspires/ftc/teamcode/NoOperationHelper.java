package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by WilliamsburgRobotic on 10/31/2017.
 */

public class NoOperationHelper {

    protected Telemetry telemetry;  // this is protected because it can be used by derived classes,
                                    // in this case LanderHelper is a derived class from NoOperationHelper
                                    // Telemetry refers to displaying information on the driver phone
                                    // Telemetry is defined through FtcRobotController
    protected HardwareMap hardwareMap;  // hardwareMap is defined through FtcRobot Controller
                                        // hardwareMap is created through Config on Robot Control Phone

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
