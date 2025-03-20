// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.autos.Autos;
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
import frc.robot.subsystems.RobotHang;
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

  /* Subsystems */
  private final RobotAlgae m_robotAlgae = new RobotAlgae();

  private final RobotAlgaePivot m_robotAlgaePivot = new RobotAlgaePivot();

  private final RobotCoral m_robotCoral = new RobotCoral();

  private final RobotCoralPivot m_robotCoralPivot = new RobotCoralPivot();

  private final RobotElevator m_robotElevator = new RobotElevator();

  private final RobotCamera m_robotCamera = new RobotCamera();

  private final RobotHang m_robotHang = new RobotHang();

  private final RobotLights m_robotLights = new RobotLights();

  private final Swerve s_Swerve = new Swerve(m_robotCamera); 
  
  /* Commands */
  
  private final GameCommands m_gameCommands;
  private final Autos m_autos;
  private final SendableChooser<Command> m_autoChooser;

  String controllerMode;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_gameCommands = new GameCommands(
      m_robotAlgae,
      m_robotAlgaePivot,
      m_robotCamera,
      m_robotCoral,
      m_robotCoralPivot,
      m_robotElevator,
      m_robotHang,
      m_robotLights,
      s_Swerve
    );

    m_autos = new Autos(
      m_gameCommands
    );

    m_autoChooser = new SendableChooser<Command>();

    s_Swerve.setDefaultCommand(
      new TeleopSwerve(
        s_Swerve,
        () -> driver.getRawAxis(1) * driver.getRawAxis(1) * driver.getRawAxis(1),
        () -> driver.getRawAxis(0) * driver.getRawAxis(0) * driver.getRawAxis(0),
        () -> driver.getRawAxis(2) * driver.getRawAxis(2) * driver.getRawAxis(2) * .5,
        () -> false));

    m_robotCoralPivot.setDefaultCommand(
      new RunCommand(
        () -> m_robotCoralPivot.voidPivotMove(SmartDashboard.getNumber("Coral Pivot Point", -100)), 
        m_robotCoralPivot
      )
    );


    m_robotAlgaePivot.setDefaultCommand(
      new RunCommand(
        () -> m_robotAlgaePivot.voidPivotMove(SmartDashboard.getNumber("Algae Pivot Point", -1)), 
        m_robotAlgaePivot
      )
    );

    m_robotElevator.setDefaultCommand(
      new RunCommand(
        () -> m_robotElevator.voidElevatorMove(SmartDashboard.getNumber("Elevator Point", 0)), 
        m_robotElevator
      )
    );

    // Configure the button bindings
    configureButtonBindings();
    configureAutos();
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

    /*  XBox:
          5, 6 [Bumpers] = Intake
          Trigger = Fire
          1, 2, 3, 4 [A, B, X, Y] = (Coral)/(Algae) Pivots
          9 [Left Stick]= Toggle Pivots
          POV [D-Pad] = (Elevator)/(Half Elevator)
          10 [Right Stick] = Toggle Elevator
          7, 8 [Select and Start]= Lights

        Joystick:
          7 = Center A Tag
          9 = Reef A Tag
          11 = Processor A Tag
        */

    driver.button(7).whileTrue(
      m_gameCommands.centerATagCommand()
    );

    driver.button(9).whileTrue(
      m_gameCommands.coralPrepCommand()
    );

    operator.button(7).whileTrue(
      m_gameCommands.lightSetCommand("blue", "left")
    );

    operator.button(8).whileTrue(
      m_gameCommands.lightSetCommand("red", "left")
    );

    // operator.button(9).onTrue(
    //   m_gameCommands.setControllerModeCommand("Coral")
    // );

    // operator.button(10).onTrue(
    //   m_gameCommands.setControllerModeCommand("Algae")
    // );

    //if(controllerMode == "Coral"){
if (true) {
      operator.button(1).whileTrue(
        m_gameCommands.coralPivotPositionSetCommand(-800)
        //m_gameCommands.manualCoralPivotMove(-700)
        //m_robotCoralPivot.testingSpeed(-.1)
      );

      operator.button(3).whileTrue(
        m_gameCommands.coralPivotPositionSetCommand(-500)
         //m_gameCommands.manualCoralPivotMove(-500)
        // m_robotCoralPivot.testingSpeed(.1)
      );

      operator.button(4).whileTrue(
        m_gameCommands.coralPivotPositionSetCommand(-100)
         //m_gameCommands.manualCoralPivotMove(-100)
      );

      // operator.button(1).onTrue(
      //   m_gameCommands.algaePivotPositionSetCommand(-1)
      //   //m_robotAlgaePivot.testingSpeed(-.1)
      // );

      operator.povDown().and(operator.button(10).negate()).onTrue(
        m_gameCommands.elevatorMoveCommand(-5)
      );

      operator.povLeft().and(operator.button(10).negate()).onTrue(
        m_gameCommands.elevatorMoveCommand(-40)
      );

      operator.povUp().and(operator.button(10).negate()).onTrue(
        m_gameCommands.elevatorMoveCommand(-100)
      );

      operator.leftTrigger().whileTrue(
        m_gameCommands.runCoralIntakeCommand(.5)
      );

      operator.rightTrigger().whileTrue(
        m_gameCommands.runCoralLaunchCommand(.5)
    );
    } else {

      operator.button(1).whileTrue(
        m_gameCommands.algaePivotPositionSetCommand(-1)
        //m_robotAlgaePivot.testingSpeed(-.1)
      );

      operator.button(3).whileTrue(
        m_gameCommands.algaePivotPositionSetCommand(-3)
      );

      operator.button(4).whileTrue(
        m_gameCommands.algaePivotPositionSetCommand(-4)
        //m_robotAlgaePivot.testingSpeed(.1)
      );

      operator.button(2).whileTrue(
        m_gameCommands.algaePivotPositionSetCommand(-5)
      );

      operator.povDown().onTrue(
        m_gameCommands.elevatorMoveCommand(-5)
      );

      operator.povLeft().onTrue(
        m_gameCommands.elevatorMoveCommand(-50)
      );

      operator.povUp().onTrue(
        m_gameCommands.elevatorMoveCommand(-70)
      );

      operator.leftTrigger().whileTrue(
        m_gameCommands.runAlgaeIntakeCommand(1)
      );

      operator.rightTrigger().whileTrue(
        m_gameCommands.runAlgaeLaunchCommand(1)
      );
    }
  }

  private void configureAutos() {
    m_autoChooser.addOption("1", m_autos.autoCommand1());
    m_autoChooser.addOption("2", m_autos.autoCommand2());
    m_autoChooser.addOption("3", m_autos.autoCommand3());
    m_autoChooser.setDefaultOption("2", m_autos.autoCommand2());

    SmartDashboard.putData("AutoCommand", m_autoChooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected();
  }

}