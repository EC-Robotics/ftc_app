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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Teleop OpMode", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class TemplateOpMode_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor leftMotor = null;
    DcMotor rightMotor = null;
    DcMotor pullyMotor = null;
    DcMotor pullyMotor2 = null;
    //DcMotor catapult;

    Servo leftBumper = null;
    Servo rightBumper = null;
    Servo dankServoLeft;
    Servo dankServoRight;
    Servo latch;
   // Servo Switch;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        leftMotor  = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        pullyMotor = hardwareMap.dcMotor.get("pullyMotor");
        pullyMotor2 = hardwareMap.dcMotor.get("pullyMotor2");
        pullyMotor2.setDirection(DcMotor.Direction.REVERSE);

        leftBumper = hardwareMap.servo.get("leftBumper");
        rightBumper = hardwareMap.servo.get("rightBumper");
        dankServoLeft = hardwareMap.servo.get("servoLeft");
        dankServoRight = hardwareMap.servo.get("servoRightr");
        latch = hardwareMap.servo.get("latch");
       // Switch = hardwareMap.servo.get("switch");

        leftBumper.setPosition(Servo.MIN_POSITION);
        rightBumper.setPosition(Servo.MIN_POSITION);
        dankServoLeft.setPosition(Servo.MIN_POSITION);
        dankServoRight.setPosition(Servo.MAX_POSITION);
        latch.setPosition(Servo.MAX_POSITION);
       // Switch.setPosition(Servo.MAX_POSITION);


        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        // rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();



            float rightMotorSpeed = gamepad1.right_stick_y;
            float leftMotorSpeed = gamepad1.left_stick_y;

            rightMotorSpeed = (float)scaleInput(rightMotorSpeed);
            leftMotorSpeed = (float)scaleInput(leftMotorSpeed);

            // write the values to the motors
            rightMotor.setPower(rightMotorSpeed);
            leftMotor.setPower(leftMotorSpeed);

            // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
            //leftMotor.setPower(-gamepad1.left_stick_y);
           //rightMotor.setPower(-gamepad1.right_stick_y);




            if(gamepad2.a) {
                pullyMotor.setPower(1);
                pullyMotor2.setPower(1);
            }else if (gamepad2.y) {
                pullyMotor.setPower(-1);
                pullyMotor2.setPower(-1);
            }else {
                pullyMotor.setPower(0);
                pullyMotor2.setPower(0);
            }
            //Extends the servo arms out
            if(gamepad2.left_bumper)
                leftBumper.setPosition(Servo.MAX_POSITION);

            if(gamepad2.right_bumper)
                rightBumper.setPosition(Servo.MAX_POSITION);


            //Retracts the servo arms
            if(gamepad2.left_trigger > 0)
                leftBumper.setPosition(Servo.MIN_POSITION);

            if(gamepad2.right_trigger > 0)
                rightBumper.setPosition(Servo.MIN_POSITION);

            if(gamepad1.a) {
                dankServoLeft.setPosition(Servo.MAX_POSITION);
                dankServoRight.setPosition(Servo.MIN_POSITION);
            }
            if(gamepad1.y) {
                dankServoLeft.setPosition(Servo.MIN_POSITION);
                dankServoRight.setPosition(Servo.MAX_POSITION);
            }

            if(gamepad2.x) {
                latch.setPosition(Servo.MIN_POSITION);
            }

            /*if(gamepad1.a) {
                catapult.setPower(1);
            }*/




            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }


    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.1, 0.18, 0.25, 0.3, 0.35, 0.4,
                0.45, 0.5, 0.6, 0.65, 0.7, 0.75, 0.8, .9,  1.00,  1.00 };
/*        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };*/

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
