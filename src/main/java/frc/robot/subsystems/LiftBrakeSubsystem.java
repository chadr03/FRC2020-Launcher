/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LiftBrakeSubsystem extends SubsystemBase {
  /**
   * Creates a new LiftBrakeSubsystem.
   */

  Solenoid liftBrake = new Solenoid(Constants.LIFT_BRAKE_PCM_PORT);
  
  public LiftBrakeSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //Smart Dashboard Items
    SmartDashboard.putBoolean("Lift Brake On", isLiftBrakeOn());
  }
  
  public void liftBrakeOn(){
    liftBrake.set(true);
  }

  public void liftBrakeOff(){
    liftBrake.set(false);
  }


  public boolean isLiftBrakeOn(){
    return liftBrake.get();
  }
}
