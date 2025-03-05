// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.autos.exampleAuto;
import frc.robot.commands.LimeLightCenterATagCommand;
import frc.robot.commands.GameCommands;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.RobotAlgae;
import frc.robot.subsystems.RobotCamera;
import frc.robot.subsystems.RobotCoral;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.RobotCoralPivot;
import frc.robot.subsystems.RobotAlgaePivot;
import frc.robot.subsystems.RobotElevator;
import frc.robot.subsystems.RobotLights;
import frc.robot.subsystems.RobotCamera;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  /* Controllers */
  private final CommandJoystick driver = new CommandJoystick(0);

  private final CommandXboxController operator = new CommandXboxController(1);

  private final GameCommands m_gameCommands;

  /* Subsystems */
  private final RobotAlgae m_robotAlgae = new RobotAlgae();

  private final RobotAlgaePivot m_robotAlgaePivot = new RobotAlgaePivot();

  private final RobotCoral m_robotCoral = new RobotCoral();

  private final RobotCoralPivot m_robotCoralPivot = new RobotCoralPivot();

  private final RobotElevator m_robotElevator = new RobotElevator();

  private final RobotCamera m_robotCamera = new RobotCamera();

  private final RobotLights m_robotLights = new RobotLights();

  private final Swerve s_Swerve = new Swerve(m_robotCamera);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_gameCommands = new GameCommands(
        m_robotAlgae,
        m_robotAlgaePivot,
        m_robotCamera,
        m_robotCoral,
        m_robotCoralPivot,
        m_robotElevator,
        m_robotLights,
        s_Swerve
      );

    s_Swerve.setDefaultCommand(
        new TeleopSwerve(
            s_Swerve,
            () -> driver.getRawAxis(1) * driver.getRawAxis(1) * driver.getRawAxis(1),
            () -> driver.getRawAxis(0) * driver.getRawAxis(0) * driver.getRawAxis(0),
            () -> driver.getRawAxis(2) * driver.getRawAxis(2) * driver.getRawAxis(2) * .5,
    //        () -> robotCentric.get()));
    () -> false));

    m_robotCoralPivot.setDefaultCommand(
      new RunCommand(
        () -> m_robotCoralPivot.voidPivotMove(SmartDashboard.getNumber("Coral Pivot Point", 0)), 
        m_robotCoralPivot
      )
    );

    m_robotAlgaePivot.setDefaultCommand(
      new RunCommand(
        () -> m_robotAlgaePivot.voidPivotMove(SmartDashboard.getNumber("Algae Pivot Point", 0)), 
        m_robotAlgaePivot
      )
    );

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    /* Driver Buttons */

    //zeroGyro.whenPressed(new InstantCommand(() -> s_Swerve.zeroGyro()));

    // driver.button(3).onTrue(
    //   s_Swerve.zeroGyro()
    // );

    operator.button(1).onTrue(
      m_gameCommands.coralPivotPositionSetCommand(-45)
    );

    operator.button(3).onTrue(
      m_gameCommands.coralPivotPositionSetCommand(-35)
    );

    operator.button(4).onTrue(
      m_gameCommands.coralPivotPositionSetCommand(-15)
    );

    operator.button(2).onTrue(
      m_gameCommands.coralPivotPositionSetCommand(-5)
    );

    // operator.button(7).whileTrue(
    //   m_gameCommands.pivotUpCommand(.1)
    // );

    // operator.button(8).whileTrue(
    //   m_gameCommands.pivotDownCommand(.1)
    // );

    operator.povUp().whileTrue(
      m_gameCommands.elevatorMoveCommand(0)
    );

    operator.povDown().whileTrue(
      m_gameCommands.elevatorMoveCommand(10)
    );

    operator.button(5).whileTrue(
      m_gameCommands.runCoralIntakeCommand(1)
    );

    operator.rightBumper().whileTrue(
      m_gameCommands.runCoralLaunchCommand(1)
    );
    
    operator.button(6).whileTrue(
      m_gameCommands.runAlgaeIntakeCommand(1)
    );

    operator.leftBumper().whileTrue(
      m_gameCommands.runAlgaeLaunchCommand(1)
    );

    driver.button(11).whileTrue(
      m_gameCommands.centerATagCommand()
    );

    driver.button(12).whileTrue(
      m_gameCommands.coralPrepCommand()
    );

    driver.button(5).whileTrue(
      m_gameCommands.lightSetCommand("red", "left")
    );

    driver.button(3).whileTrue(
      m_gameCommands.lightSetCommand("blue", "left")
    );

    driver.button(6).whileTrue(
      m_gameCommands.lightSetCommand("red", "right")
    );

    driver.button(4).whileTrue(
      m_gameCommands.lightSetCommand("blue", "right")
    );

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new exampleAuto(s_Swerve);

  }

}