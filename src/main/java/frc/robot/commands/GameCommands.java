package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.RobotAlgae;
import frc.robot.subsystems.RobotCoral;
import frc.robot.subsystems.RobotCoralPivot;
import frc.robot.subsystems.RobotElevator;
import frc.robot.subsystems.RobotCamera;
import frc.robot.commands.LimeLightCenterATagCommand;
import frc.robot.commands.LimeLightCoralPrepCommand;

public class GameCommands {

  private final RobotAlgae m_robotAlgae;

  private final RobotCoral m_robotCoral;

  private final RobotCoralPivot m_robotCoralPivot;

  private final RobotElevator m_robotElevator;

  private final Swerve s_Swerve;

  private final RobotCamera m_robotCamera;

  double gyro;

  double spinAmount;

  double tagX;

  double tagArea;

  double tagID;

  public GameCommands(
    RobotAlgae robotAlgae,
    RobotCoral robotCoral,
    RobotCoralPivot robotCoralPivot,
    RobotElevator robotElevator,
    Swerve swerve,
    RobotCamera robotCamera
  ) {
    m_robotAlgae = robotAlgae;

    m_robotCoral = robotCoral;

    m_robotCoralPivot = robotCoralPivot;

    m_robotElevator = robotElevator;

    s_Swerve = swerve;

    m_robotCamera = robotCamera;

  }

  public Command runCoralIntakeCommand(double speed) {
    return m_robotCoral.intake(speed);
  }

  public Command runCoralLaunchCommand(double speed) {
    return m_robotCoral.launch(speed);
  }

  public Command runAlgaeIntakeCommand(double speed) {
    return m_robotAlgae.intake(speed);
  }

  public Command runAlgaeLaunchCommand(double speed) {
    return m_robotAlgae.launch(speed);
  }

  public Command moveUpCommand(double speed) {
    return m_robotCoralPivot.moveUp(speed);
  }

  public Command moveDownCommand(double speed) {
    return m_robotCoralPivot.moveDown(speed);
  }

  public Command elevatorUpCommand(double speed) {
    return m_robotElevator.elevatorUp(speed);
  }

  public Command elevatorDownCommand(double speed) {
    return m_robotElevator.elevatorDown(speed);
  }

  public Command driveCommand(double vertical, double horizontal, double rotate) {
    return new TeleopSwerve(
      s_Swerve,
      () -> vertical,
      () -> horizontal,
      () -> rotate,
      () -> true);
  }

  public Command centerATagCommand(){
    return new LimeLightCenterATagCommand(s_Swerve);
  }

  public Command coralPrepCommand(){
    return new LimeLightCoralPrepCommand(s_Swerve);
  }
}