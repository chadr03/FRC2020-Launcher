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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoConveyorCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ClimbHookSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.LauncherSubsystem;
import frc.robot.subsystems.LiftBrakeSubsystem;
import frc.robot.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
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
  private final LiftBrakeSubsystem liftBrake = new LiftBrakeSubsystem();
  private final ClimbHookSubsystem climbHook = new ClimbHookSubsystem();
  private final LEDSubsystem led = new LEDSubsystem();


  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // The driver's controller
  
  Joystick driverController = new Joystick(Constants.DRIVE_JOYSTICK_PORT);
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Put Some buttons on the SmartDashboard

    SmartDashboard.putData("Green LED", new RunCommand(() -> led.green(),led));
    SmartDashboard.putData("Red LED", new RunCommand(() -> led.red(),led));
    SmartDashboard.putData("Blue LED", new RunCommand(() -> led.blue(),led));

    SmartDashboard.putData("Close Climb Hook", new InstantCommand(climbHook::closeClimbHook, climbHook));
    SmartDashboard.putData("Open Climb Hook", new InstantCommand(climbHook::openClimbHook, climbHook));

    SmartDashboard.putData("Conveyor In", new RunCommand(() -> conveyor.manualMoveConveyor(Constants.CONVEYOR_SPEED), conveyor));
    SmartDashboard.putData("Conveyor Out", new RunCommand(() -> conveyor.manualMoveConveyor(-Constants.CONVEYOR_SPEED), conveyor));

    SmartDashboard.putData("Intake In", new RunCommand(() -> intake.manualIntake(Constants.INTAKE_SPEED), intake));
    SmartDashboard.putData("Intake Out", new RunCommand(() -> intake.manualIntake(-Constants.INTAKE_SPEED), intake));


    led.purple(); // Turns on Purple LED's even when disabled ---- There may be a better place 
    //for this I think this is a periodic call and this would be best to be a one time call but it seems to work
    

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
      .manualLift(-driverController.getRawAxis(Constants.GP_LEFT_TRIGGER) + driverController.getRawAxis(Constants.GP_RIGHT_TRIGGER)), lift));

    conveyor.setDefaultCommand(new AutoConveyorCommand(conveyor));
    
 

    launcher.setDefaultCommand(new RunCommand(launcher::velocityClosedLoopLaunch, launcher));

    liftBrake.setDefaultCommand(new RunCommand(liftBrake::liftBrakeOn, liftBrake));

    led.setDefaultCommand(new RunCommand(led::rainbow, led));

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
