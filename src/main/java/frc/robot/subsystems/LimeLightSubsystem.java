/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLightSubsystem extends SubsystemBase {
  /**
   * Creates a new LimeLightSubsystem.
   */

  NetworkTable table;
  private double tv; 
  private double tx; 
  private double ty; 
  private double ta;

  public LimeLightSubsystem() {
  table=NetworkTableInstance.getDefault().getTable("limelight");


  tv = table.getEntry("tv").getDouble(0);
  tx = table.getEntry("tx").getDouble(0);
  ty = table.getEntry("ty").getDouble(0);
  ta = table.getEntry("ta").getDouble(0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
