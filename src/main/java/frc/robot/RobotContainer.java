/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.AutoConveyorCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LauncherSubsystem;
import frc.robot.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final LiftSubsystem lift = new LiftSubsystem();//This must be before the drive subsystem because it has the gyro that drive uses
  private final DriveSubsystem drive = new DriveSubsystem(lift);
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final ConveyorSubsystem conveyor = new ConveyorSubsystem();
  private final LauncherSubsystem launcher = new LauncherSubsystem();
  private final LEDSubsystem led = new LEDSubsystem();


  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // The driver's controller
  
  Joystick driverController = new Joystick(Constants.DRIVE_JOYSTICK_PORT);
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    // Set the default drive command to split-stick arcade drive
    drive.setDefaultCommand(
    
      new RunCommand(() -> drive
  
       // A split-stick arcade command, with forward/backward controlled by the left
        // hand, and turning controlled by the right.
       
      .teleopDrive(-driverController.getRawAxis(Constants.GP_LEFT_Y_AXIS),
                   driverController.getRawAxis(Constants.GP_RIGHT_X_AXIS)), drive));

    lift.setDefaultCommand(
    
      new RunCommand(() -> lift
  
       // Left Trigger and Right Trigger Drive Lift
       
      .manualLift(-driverController.getRawAxis(Constants.GP_LEFT_TRIGGER) + driverController.getRawAxis(Constants.GP_RIGHT_TRIGGER)
                   ), lift));

    conveyor.setDefaultCommand(new AutoConveyorCommand(conveyor));
    

  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
