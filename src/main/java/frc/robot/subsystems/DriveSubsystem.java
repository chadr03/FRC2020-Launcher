/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  /**
   * Creates a new DriveSubsystem.
   * 
   * 
   */

  //Set up motor controllers
  CANSparkMax leftLeader = new CANSparkMax(Constants.LEFT_DRIVE_MOTOR_LEAD_PORT, MotorType.kBrushless);
  CANSparkMax leftFollower = new CANSparkMax(Constants.LEFT_DRIVE_MOTOR_FOLLOW_PORT, MotorType.kBrushless);
  CANSparkMax rightLeader = new CANSparkMax(Constants.RIGHT_DRIVE_MOTOR_LEAD_PORT, MotorType.kBrushless);
  CANSparkMax rightFollower = new CANSparkMax(Constants.RIGHT_DRIVE_MOTOR_FOLLOW_PORT, MotorType.kBrushless);

  //Sets up encoders
  CANEncoder leftEncoder;
  CANEncoder rightEncoder;
  
  //Sets up differental drive
  DifferentialDrive drive = new DifferentialDrive(leftLeader, rightLeader);

  //Sets up a global lift subsystem so the pidgeon that is on the liftFollow talon can be used
  LiftSubsystem drive_LiftSubsystem;
  
  public DriveSubsystem(LiftSubsystem liftSubsystem) {
    //the liftSubsytem passed into the construstor is set into the global lift subsystem
    //drive_LiftSubsystem = liftSubsystem;

    //Resets motor controllers to default conditions
    leftLeader.restoreFactoryDefaults();
    leftFollower.restoreFactoryDefaults();
    rightLeader.restoreFactoryDefaults();
    rightFollower.restoreFactoryDefaults();

    leftLeader.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
    leftFollower.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
    rightLeader.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
    rightFollower.setSmartCurrentLimit(Constants.SPARK_MAX_CURRENT_LIMIT);
  

    //Sets up follower motors
    leftFollower.follow(leftLeader);
    rightFollower.follow(rightLeader);

    //Sets up endcoders
    leftEncoder = leftLeader.getEncoder();
    rightEncoder = rightLeader.getEncoder();
    //if encoder is counting down when going forward ajust this setting
    //rightEncoder.setInverted(false);
    //leftEncoder.setInverted(false);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run


    //Smart Dashboard Items
    SmartDashboard.putNumber("Left Drive Encoder Position", getLeftEncoderPosition());
    SmartDashboard.putNumber("Right Drive Encoder Position", getRightEncoderPosition());
    //SmartDashboard.putNumber("Drive Angle", getAngle());
  }

  public void teleopDrive(double move, double turn) {
    drive.arcadeDrive(move, turn);
  }


  public void resetEncoders(){
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }



  public double getLeftEncoderPosition(){
    return leftEncoder.getPosition();
  }

  public double getRightEncoderPosition(){
    return rightEncoder.getPosition();
  }

  public double getLeftEncoderVelocity(){
    return leftEncoder.getVelocity();
  }

  public double getRightEncoderVelocity(){
    return rightEncoder.getVelocity();
  }

  public double getAngle(){
    return drive_LiftSubsystem.getAngle();
  }
}
