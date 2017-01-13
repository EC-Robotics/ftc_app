/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Description:
 * This opmode is used to read in the values from both the line following sensor at the base
 * of the robot and the color sensor attached to the button pusher.  The resultant min and max values
 * for the line following sensor can be used as the min and max for any line following code, in
 * particular to calculate what the 'average' light value is along the edge of a white line.
 *
 * The red and blue values from the beacon sensor can be used to set 'detection' thresholds for a
 * blue or red beacon.
 *
 * Usage:  Set robot next to white line at approximate distance and angle to how it will approach
 * the line during autonomous.  Run the opmode and it will move the robot forward for 2 seconds
 * which should be enough to run over the line.  The telemetry will output the current sensor value
 * as well as the min and max values.  Robot can also be moved manually to read light values.
 *
 * After running the robot to read in the line following sensor values, the robot can manually be
 * moved in front of the beacon light(s) to display current red and blue values from the beacon
 * sensor on top of the button pusher.
 *
 */
@Autonomous(name = "Sensor Calibrate", group = "Sensor")
//@Disabled
public class SensorCalibrate extends LinearOpMode {
  final String beaconString = "beaconSensor";
  ColorSensor colorSensor;    // Hardware Device Object
  ColorSensor beaconSensor;
  DcMotor leftMotor = null;
  DcMotor rightMotor = null;
  private ElapsedTime runtime = new ElapsedTime();

  @Override
  public void runOpMode() throws InterruptedException {
    leftMotor  = hardwareMap.dcMotor.get("leftMotor");
    rightMotor = hardwareMap.dcMotor.get("rightMotor");
    leftMotor.setDirection(DcMotor.Direction.FORWARD);
    rightMotor.setDirection(DcMotor.Direction.REVERSE);
    colorSensor = hardwareMap.colorSensor.get("colorSensor");
    beaconSensor = getBeacon(hardwareMap);
    int minBrightness = 999;
    int maxBrightness = -999;

    // Set colorSensor ON for line following
    colorSensor.enableLed(true);

    // wait for the start button to be pressed.
    waitForStart();
    runtime.reset();

    // while the op mode is active, loop and read the RGB data.
    // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
    while (opModeIsActive()) {
      // send the info back to driver station using telemetry function.
        int currentBrightness = colorSensor.alpha();

        if (currentBrightness < minBrightness)
            minBrightness = currentBrightness;

        if (currentBrightness > maxBrightness)
            maxBrightness = currentBrightness;

        telemetry.addData("LineFollow: Current ", currentBrightness);
        telemetry.addData("LineFollow: Min ", minBrightness);
        telemetry.addData("LineFollow: Max ", maxBrightness);

        if (beaconSensor != null) {
            telemetry.addData("Beacon: Red  ", beaconSensor.red());
            telemetry.addData("Beacon: Blue ", beaconSensor.blue());
        } else {
            telemetry.addData("Beacon: \"" + beaconString + "\" not found", 0);
        }

        if (runtime.seconds() < 2.0) {
            leftMotor.setPower(.6);
            rightMotor.setPower(.6);
        } else {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }

        telemetry.update();
        idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
    }
  }

    private ColorSensor getBeacon(HardwareMap hardwareMap) {
        ColorSensor beaconSensor;
        try {
            beaconSensor = hardwareMap.colorSensor.get(beaconString);
        } catch (IllegalArgumentException e) {
            beaconSensor = null;
        }

        if (beaconSensor != null) {
            beaconSensor.enableLed(false);
        }
        return beaconSensor;
    }
}
