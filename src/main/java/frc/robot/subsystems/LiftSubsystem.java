/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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

  DoubleSolenoid hangerSolenoid = new DoubleSolenoid(Constants.HANGER_OPEN_PCM_PORT, Constants.HANGER_CLOSED_PCM_PORT);
  Solenoid liftBrake = new Solenoid(Constants.LIFT_BRAKE_PCM_PORT);
 
  public LiftSubsystem() {
    //Reset factory default to clear any odd settings
    liftLeader.configFactoryDefault();
    liftFollower.configFactoryDefault();

    //Sets up follower
    liftFollower.follow(liftLeader);

    //Sets up encoder
    liftLeader.setSensorPhase(true);
		liftLeader.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void manualLift(double move){
    liftLeader.set(ControlMode.PercentOutput, move);
  }

  public void closeHangerHooks(){
    hangerSolenoid.set(Value.kForward);
  }

  public void openHangerHooks(){
    hangerSolenoid.set(Value.kReverse);
  }

  public void parkHangerHooks(){
    hangerSolenoid.set(Value.kOff);
  }

  public void liftBrakeOn(){
    liftBrake.set(true);
  }

  public void liftBrakeOff(){
    liftBrake.set(false);
  }


  public double getAngle(){
    double[] ypr_deg = new double[3];
		imu.getYawPitchRoll(ypr_deg);
		return ypr_deg[0];
  }

  public int getLiftPosition(){
    return this.liftLeader.getSelectedSensorPosition(0);
  }

  public double getLiftVelocity(){
    return this.liftLeader.getSelectedSensorVelocity(0);
  }
}
