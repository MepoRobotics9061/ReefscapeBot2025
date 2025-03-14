package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.RobotAlgae;
import frc.robot.subsystems.RobotAlgaePivot;
import frc.robot.subsystems.RobotCoral;
import frc.robot.subsystems.RobotCoralPivot;
import frc.robot.subsystems.RobotElevator;
import frc.robot.subsystems.RobotHang;
import frc.robot.subsystems.RobotLights;
import frc.robot.subsystems.RobotCamera;
import frc.robot.commands.LimeLightCenterATagCommand;
import frc.robot.commands.LimeLightCoralPrepCommand;
import frc.robot.commands.RotateUntil180Command;


public class GameCommands {

  private final RobotAlgae m_robotAlgae;

  private final RobotAlgaePivot m_robotAlgaePivot;

  private final RobotCamera m_robotCamera;

  private final RobotCoral m_robotCoral;

  private final RobotCoralPivot m_robotCoralPivot;

  private final RobotElevator m_robotElevator;

  private final RobotLights m_robotLights;

  private final RobotHang m_robotHang;

  private final Swerve s_Swerve;

  double gyro;

  double spinAmount;

  double tagX;

  double tagArea;

  double tagID;

  public GameCommands(
    RobotAlgae robotAlgae,
    RobotAlgaePivot robotAlgaePivot,
    RobotCamera robotCamera,
    RobotCoral robotCoral,
    RobotCoralPivot robotCoralPivot,
    RobotElevator robotElevator,
    RobotLights robotLights,
    RobotHang robotHang,
    Swerve swerve
  ) {
    m_robotAlgae = robotAlgae;

    m_robotAlgaePivot = robotAlgaePivot;

    m_robotCamera = robotCamera;

    m_robotCoral = robotCoral;

    m_robotCoralPivot = robotCoralPivot;

    m_robotElevator = robotElevator;

    m_robotLights = robotLights;

    m_robotHang = robotHang;

    s_Swerve = swerve;

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

  public Command manualCoralPivotMove(double desiredValue) {
    return m_robotCoralPivot.manualPivotMove(desiredValue);
  }

  public Command coralPivotPositionSetCommand(double pivotPoint) {
    return m_robotCoralPivot.pivotPositionSet(pivotPoint);
  }

  public Command algaePivotPositionSetCommand(double pivotPoint) {
    return m_robotAlgaePivot.pivotPositionSet(pivotPoint);
  }

  // public Command pivotUpCommand(double speed) {
  //   return m_robotCoralPivot.pivotUp(speed);
  // }

  // public Command pivotDownCommand(double speed) {
  //   return m_robotCoralPivot.pivotDown(speed);
  // }

  public Command elevatorMoveCommand(double desiredValue) {
    return m_robotElevator.manualElevatorMove(desiredValue);
  }

  public Command hangCloseCommand() {
    return m_robotHang.hangClose();
  }

  public Command hangOpenCommand() {
    return m_robotHang.hangOpen();
  }

  public Command driveCommand(double vertical, double horizontal, double rotate) {
    return new TeleopSwerve(
      s_Swerve,
      () -> vertical,
      () -> horizontal,
      () -> rotate,
      () -> true);
  }

  public Command driveRotateUntil180Command(double rotateAmount){
    return new RotateUntil180Command(s_Swerve);
  }

  public Command centerATagCommand(){
    return new LimeLightCenterATagCommand(s_Swerve);
  }

  public Command coralPrepCommand(){
    return new LimeLightCoralPrepCommand(s_Swerve);
  }

  public Command lightSetCommand(String color, String side){
    return m_robotLights.lightArea(color, side);
  }
}