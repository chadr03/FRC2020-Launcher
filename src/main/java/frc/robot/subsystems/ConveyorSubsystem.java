/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {
  /**
   * Creates a new ConveyorSubsystem.
   */

  //Set up motor controllers
  Spark conveyorTopMotor = new Spark(Constants.CONVEYOR_TOP_MOTOR_PORT);
  Spark conveyorBottomMotor = new Spark(Constants.CONVEYOR_BOTTOM_MOTOR_PORT);

  //Sets up group to make both motors always run together
  SpeedControllerGroup conveyorMotors = new SpeedControllerGroup(conveyorTopMotor, conveyorBottomMotor);

  //Set up ball sensors
  DigitalInput intakeSensor = new DigitalInput(Constants.INTAKE_SENSOR);
  DigitalInput launcherSensor = new DigitalInput(Constants.LAUNCHER_SENSOR);

  public ConveyorSubsystem() {
  //invert bottom motor
  conveyorBottomMotor.setInverted(true);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run


    //Smart Dashboard Items
    SmartDashboard.putBoolean("Intake Sensor", intakeSensorState());
    SmartDashboard.putBoolean("Launcher Sensor", launcherSensorState());
  }

  public void manualMoveConveyor(double speed){
    conveyorMotors.set(speed);
  }


  public boolean intakeSensorState(){
    return intakeSensor.get();
  }

  public boolean launcherSensorState(){
    return launcherSensor.get();
  }
}
