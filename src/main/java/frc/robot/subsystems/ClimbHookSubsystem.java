/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbHookSubsystem extends SubsystemBase {
  /**
   * Creates a new ClimbHooksSubsystem.
   */

  DoubleSolenoid hangerSolenoid = new DoubleSolenoid(Constants.HANGER_OPEN_PCM_PORT, Constants.HANGER_CLOSED_PCM_PORT);
  
  private boolean isClimbHookClosed = false;

  public ClimbHookSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //Smart Dashboard Items
    SmartDashboard.putBoolean("Is Climb Hook Closed", isClimbHookClosed);
  }

  public void closeClimbHook(){
    hangerSolenoid.set(Value.kForward);
    isClimbHookClosed = true;
  }

  public void openClimbHook(){
    hangerSolenoid.set(Value.kReverse);
    isClimbHookClosed = false;
  }

  public void parkClimbHook(){
    hangerSolenoid.set(Value.kOff);
    isClimbHookClosed = false;
  }


  public boolean isClimbHookClosed(){
    return isClimbHookClosed;
  }
}
