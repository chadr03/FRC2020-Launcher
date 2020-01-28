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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLightSubsystem extends SubsystemBase {
  /**
   * Creates a new LimeLightSubsystem.
   */

  NetworkTable table;
  private boolean validTargets; 
  private double horizontalOffset; 
  private double verticalOffset; 
  private double targetArea;
  private double skew;
  private double activePipeline;
  

  public LimeLightSubsystem() {
    table=NetworkTableInstance.getDefault().getTable("limelight");




  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    validTargets = isTargetValid();
    horizontalOffset = getHorizontalOffset();
    verticalOffset = getVerticalOffset();
    targetArea = getTargetArea();
    skew = getSkew();
    activePipeline = getActivePipeline();


    //Smart Dashboard Items
    SmartDashboard.putNumber("LL Horizontal Offset", horizontalOffset);
    SmartDashboard.putNumber("LL Vertical Offset", verticalOffset);
    SmartDashboard.putNumber("LL Target Area", targetArea);
    SmartDashboard.putBoolean("LL Valid Target", validTargets);
    

  }



  public boolean isTargetValid(){
    return (table.getEntry("tv").getDouble(0) == 1);
  }

  public double getHorizontalOffset(){
    return table.getEntry("tx").getDouble(0);
  }

  public double getVerticalOffset(){
    return table.getEntry("ty").getDouble(0);
  }

  public double getTargetArea(){
    return table.getEntry("ta").getDouble(0);
  }

  public double getSkew(){
    return table.getEntry("ts").getDouble(0);
  }

  public double getActivePipeline(){
    return table.getEntry("getpipe").getDouble(0);
  }

  //Forces LED's off
  public void ledOff(){
    table.getEntry("ledMode").setNumber(1);
  }

  //Sets LED's to be in the state of the current pipeline
  public void ledPipeline(){
    table.getEntry("ledMode").setNumber(0);
  }

  public void setPipeline(int pipeline){
    table.getEntry("pipeline").setNumber(pipeline);
  }

}
