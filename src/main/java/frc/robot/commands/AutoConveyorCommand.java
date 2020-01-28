/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ConveyorSubsystem;

public class AutoConveyorCommand extends CommandBase {
  /**
   * Creates a new AutoConveyorCommand.
   */
  ConveyorSubsystem cmd_conveyor;
  
  public AutoConveyorCommand(ConveyorSubsystem conveyor) {
    // Use addRequirements() here to declare subsystem dependencies.
    cmd_conveyor = conveyor;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(cmd_conveyor.intakeSensorState() && !cmd_conveyor.launcherSensorState()){
      cmd_conveyor.manualMoveConveyor(Constants.CONVEYOR_SPEED);
    }
    else{
      cmd_conveyor.manualMoveConveyor(0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    cmd_conveyor.manualMoveConveyor(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
