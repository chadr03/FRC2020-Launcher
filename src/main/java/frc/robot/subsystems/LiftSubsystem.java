/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LiftSubsystem extends SubsystemBase {
  /**
   * Creates a new LiftSubsystem.
   * 
   */

  //Set up motor controllers
  WPI_TalonSRX liftLeader = new WPI_TalonSRX(Constants.LIFT_MOTOR_LEAD_PORT);
  WPI_TalonSRX liftFollower = new WPI_TalonSRX(Constants.LIFT_MOTOR_FOLLOW_PORT);

  //Encoder needs to be pugged into liftLeader and pidgeon into liftFollower
  PigeonIMU imu = new PigeonIMU(liftFollower);

 
  

  private int liftSetPoint = 0;
 
  public LiftSubsystem() {
    //Reset factory default to clear any odd settings
    liftLeader.configFactoryDefault();
    liftFollower.configFactoryDefault();

    liftLeader.configPeakCurrentLimit(Constants.TALON_SRX_PEAK_CURRENT_LIMIT);
    liftLeader.configPeakCurrentDuration(Constants.TALON_SRX_PEAK_CURRENT_DURATION);
    liftLeader.configContinuousCurrentLimit(Constants.TALON_SRX_CONT_CURRENT_LIMIT);
    liftLeader.enableCurrentLimit(true);
    


    liftFollower.configPeakCurrentLimit(Constants.TALON_SRX_PEAK_CURRENT_LIMIT);
    liftFollower.configPeakCurrentDuration(Constants.TALON_SRX_PEAK_CURRENT_DURATION);
    liftFollower.configContinuousCurrentLimit(Constants.TALON_SRX_CONT_CURRENT_LIMIT);
    liftFollower.enableCurrentLimit(true);






    //Sets up follower
    liftFollower.follow(liftLeader);

    //Sets up encoder
    liftLeader.setSensorPhase(false);
		liftLeader.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    //Sets up software limit switches
    liftLeader.configForwardSoftLimitEnable(true);
    liftLeader.configForwardSoftLimitThreshold(Constants.LIFT_FORWARD_LIMIT);


    liftLeader.configReverseSoftLimitEnable(true);
    liftLeader.configReverseSoftLimitThreshold(Constants.LIFT_REVERSE_LIMIT);

    //Motion Magic Setup
    /* Set relevant frame periods to be at least as fast as periodic rate */
		liftLeader.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		liftLeader.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* Set the peak and nominal outputs */
		liftLeader.configNominalOutputForward(0, Constants.kTimeoutMs);
		liftLeader.configNominalOutputReverse(0, Constants.kTimeoutMs);
		liftLeader.configPeakOutputForward(1, Constants.kTimeoutMs);
		liftLeader.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Set Motion Magic gains in slot0 - see documentation */
		liftLeader.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		liftLeader.config_kF(Constants.kSlotIdx, Constants.LIFT_KF, Constants.kTimeoutMs);
		liftLeader.config_kP(Constants.kSlotIdx, Constants.LIFT_KP, Constants.kTimeoutMs);
		liftLeader.config_kI(Constants.kSlotIdx, Constants.LIFT_KI, Constants.kTimeoutMs);
		liftLeader.config_kD(Constants.kSlotIdx, Constants.LIFT_KD, Constants.kTimeoutMs);

		/* Set acceleration and vcruise velocity - see documentation */
		liftLeader.configMotionCruiseVelocity(Constants.LIFT_CRUISE_VELOCITY, Constants.kTimeoutMs);
		liftLeader.configMotionAcceleration(Constants.LIFT_ACCELERATION, Constants.kTimeoutMs);

		/* Zero the sensor once on robot boot up */
		liftLeader.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //Smart Dashboard Items
    SmartDashboard.putNumber("Lift Position", getLiftPosition());
    
  }

  public void manualLift(double move){
    liftLeader.set(ControlMode.PercentOutput, move);
  }

  public void motionMagicLift(){
    liftLeader.set(ControlMode.MotionMagic, liftSetPoint);
  }

  public void manualMotionMagicLift(double move){
    double move_constant = 1.0;
    int moveValue = (int)(move * move_constant);
    liftSetPoint = liftSetPoint + moveValue;
  }

  /*




  */
  public double getAngle(){
    double[] ypr_deg = new double[3];
    imu.getYawPitchRoll(ypr_deg);
		return ypr_deg[0];
  }

  public void resetGyro(){
    imu.setYaw(0.0);
  }  



  public int getLiftPosition(){
    return this.liftLeader.getSelectedSensorPosition(0);
  }

  public double getLiftVelocity(){
    return this.liftLeader.getSelectedSensorVelocity(0);
  }

  public void setLiftSetpoint(int setpoint){
    liftSetPoint = setpoint;
  }

  public int getLiftSetpoint(){
    return liftSetPoint;
  }
}
