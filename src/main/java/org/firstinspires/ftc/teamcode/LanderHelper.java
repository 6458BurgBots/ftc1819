package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LanderHelper extends NoOperationHelper {
    private DcMotor armMotor;

    LanderHelper(Telemetry t, HardwareMap h)
    {
        super(t, h);
    }

    public void init() {
        //armMotor  = hardwareMap.get(DcMotor.class, "arm");
        armMotor  = hardwareMap.dcMotor.get("arm");
    }

    public void raiseArm(double power){
        armMotor.setPower(power);
    }

    public int getPosition(){
        return armMotor.getCurrentPosition();
    }

    public void checkTeleOp(Gamepad gamepad1, Gamepad gamepad2){
        // alaina is struggling to find a way to describe this
        // Maybe Ms. Stehno can help...
        // public refers to a procedure that can be used by all classes, it is not specific to this class
        // the checkTeleOp procedure expects gamepad1 and gamepad2 as parameters
        // gamepad1 and gamepad2 refer to the two different controllers that drivers use

        if (gamepad1.a && !gamepad1.b) { // if a is pressed on gamepad1, the armMotor power will be set to .3 (forward/up)
            armMotor.setPower(.3);
        }
        if (gamepad1.b && !gamepad1.a) { // if b is pressed on gamepad1, the armMotor power will be set to -.3 (backwards/down)
            armMotor.setPower(-.3);
        }
        if (!gamepad1.a && !gamepad1.b) { // if neither a nor b are pressed on the gamepad1, the power will be set to 0
            armMotor.setPower(0);
        }
        if (gamepad1.a && gamepad1.b) { // if both a and b are push on the gamepad1, the power will be set to 0
            armMotor.setPower(0);
        }

        // an alternate way to program the series of ifs.
//        if (gamepad1.a) {
//            armMotor.setPower(.3);
//        } else if (gamepad1.b) {
//            armMotor.setPower(-.3);
//        } else {
//            armMotor.setPower(0);
//        }
    }
}
