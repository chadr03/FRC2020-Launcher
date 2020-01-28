/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LauncherSubsystem extends SubsystemBase {
  /**
   * Creates a new LauncherSubsystem.
   */

  //Set up motor controllers
  CANSparkMax launcherLeader = new CANSparkMax(Constants.LAUNCHER_MOTOR_LEAD_PORT, MotorType.kBrushless);
  CANSparkMax launcherFollower = new CANSparkMax(Constants.LAUNCHER_MOTOR_FOLLOW_PORT, MotorType.kBrushless);

  
  //Sets up encoders
  CANEncoder launcherEncoder;

  //Sets up PID Controller
  private CANPIDController pidController;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

  private double velocitySetpoint = 0.0;

  public LauncherSubsystem() {
        //Resets motor controllers to default conditions
        launcherLeader.restoreFactoryDefaults();
        launcherFollower.restoreFactoryDefaults();

        //sets up follower and makes the direction inverted to the leader
        launcherFollower.follow(launcherLeader, true);

        //Sets up endcoders
        launcherEncoder = launcherLeader.getEncoder();
        //if encoder is counting down when going forward ajust this setting
        launcherEncoder.setInverted(false);

        pidController = launcherLeader.getPIDController();

        // PID coefficients these will need to be tuned
        kP = 5e-5; 
        kI = 1e-6;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;
        maxRPM = 5700;

        // set PID coefficients
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setIZone(kIz);
        pidController.setFF(kFF);
        pidController.setOutputRange(kMinOutput, kMaxOutput);




  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void manualLanuch(double speed){
    launcherLeader.set(speed);
  }

  public void velocityClosedLoopLaunch(){
    pidController.setReference(velocitySetpoint, ControlType.kVelocity);
  }

  public void setVelocitySetpoint(double velocity){
    velocitySetpoint = velocity;
  }

  public double getVelocitySetpoint(){
    return velocitySetpoint;
  }


  public double getLauncherVelocity(){
    return launcherEncoder.getVelocity();
  }
}
