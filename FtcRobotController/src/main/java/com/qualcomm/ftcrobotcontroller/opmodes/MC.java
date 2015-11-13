package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Patrick.Brady on 11/5/2015.
 */
public class MC {
    final int DEFAULT_SPEED = 50;

    public void moveForward(int distance, int speed, DcMotor rightMotor, DcMotor leftMotor){
        //move for distance at speed using motors right and left
    }

    public void moveBackward(int distance, int speed, DcMotor rightMotor, DcMotor leftMotor){
        //move backward for distance at speed using motors right and left
    }

    public void turnRight(int distance, int speed, DcMotor rightMotor, DcMotor leftMotor){
        //turn right for distance at speed using motors right and left
    }

    public void turnLeft(int distance, int speed, DcMotor rightMotor, DcMotor leftMotor){
        //turn left for distance at speed using motors right and left
    }

    public void moveForward(int distance, DcMotor rightMotor, DcMotor leftMotor){
        moveForward(distance, DEFAULT_SPEED, rightMotor, leftMotor);
    }
    public void moveBackward(int distance, DcMotor rightMotor, DcMotor leftMotor){
        moveBackward(distance, DEFAULT_SPEED, rightMotor, leftMotor);
    }
    public void turnRight(int distance, DcMotor rightMotor, DcMotor leftMotor){
        turnRight(distance, DEFAULT_SPEED, rightMotor, leftMotor);
    }
    public void turnLeft(int distance, DcMotor rightMotor, DcMotor leftMotor){
        turnLeft(distance, DEFAULT_SPEED, rightMotor, leftMotor);
    }
}