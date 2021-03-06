/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  /**
   * Creates a new IntakeSubsystem.
   */
  //Set up motor controllers
  Spark intakeMotor = new Spark(Constants.INTAKE_MOTOR_PORT);

  //Set up pneumatic solenoids
  Solenoid intakeSolenoid = new Solenoid(Constants.INTAKE_PCM_PORT);

  public IntakeSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void manualIntake(double speed){
    intakeMotor.set(speed);
  }

  public void intakeUp(){
    intakeSolenoid.set(false);
  }

  public void intakeDown(){
    intakeSolenoid.set(true);
  }


}
