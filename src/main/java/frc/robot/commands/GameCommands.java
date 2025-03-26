package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;
import java.util.function.DoubleSupplier;
import frc.robot.subsystems.RobotAlgae;
import frc.robot.subsystems.RobotAlgaePivot;
import frc.robot.subsystems.RobotCoral;
import frc.robot.subsystems.RobotCoralPivot;
import frc.robot.subsystems.RobotElevator;
import frc.robot.subsystems.RobotLights;
import frc.robot.subsystems.RobotCamera;
import frc.robot.commands.LimeLightCenterATagCommand;
import frc.robot.commands.LimeLightCoralPrepCommand;
import frc.robot.commands.LimeLightAlgaePrepCommand;
import frc.robot.commands.RotateUntilCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GameCommands /* extends SubsystemBase */{

  private final RobotAlgae m_robotAlgae;

  private final RobotAlgaePivot m_robotAlgaePivot;

  private final RobotCamera m_robotCamera;

  private final RobotCoral m_robotCoral;

  private final RobotCoralPivot m_robotCoralPivot;

  private final RobotElevator m_robotElevator;

  private final RobotLights m_robotLights;

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
      Swerve swerve) {
    m_robotAlgae = robotAlgae;

    m_robotAlgaePivot = robotAlgaePivot;

    m_robotCamera = robotCamera;

    m_robotCoral = robotCoral;

    m_robotCoralPivot = robotCoralPivot;

    m_robotElevator = robotElevator;

    m_robotLights = robotLights;

    s_Swerve = swerve;

  }

  public Command runCoralIntakeCommand(double speed) {
    System.out.println("runCoralIntakeCommand: RUNNING");
    return m_robotCoral.intake(speed);
  }

  public Command runCoralLaunchCommand(double speed) {
    System.out.println("runCoralLaunchCommand: RUNNING");
    return m_robotCoral.launch(speed);
  }

  public Command runAlgaeIntakeCommand(double speed) {
    System.out.println("runAlgaeIntakeCommand: RUNNING");
    return m_robotAlgae.intake(speed);
  }

  public Command runAlgaeLaunchCommand(double speed) {
    System.out.println("runAlgaeLaunchCommand: RUNNING");
    return m_robotAlgae.launch(speed);
  }

  public Command manualCoralPivotMove(double desiredValue) {
    System.out.println("manualCoralPivotMove: RUNNING");
    return m_robotCoralPivot.manualPivotMove(desiredValue);
  }

  public Command manualAlgaePivotMove(double desiredValue) {
    System.out.println("manualAlgaePivotMove: RUNNING");
    return m_robotAlgaePivot.manualPivotMove(desiredValue);
  }

  public Command coralPivotPositionSetCommand(double pivotPoint) {
    System.out.println("coralPivotPositionSetCommand: RUNNING");
    return m_robotCoralPivot.pivotPositionSet(pivotPoint);
  }

  public Command algaePivotPositionSetCommand(double pivotPoint) {
    System.out.println("algaePivotPositionSetCommand: RUNNING");
    return m_robotAlgaePivot.pivotPositionSet(pivotPoint);
  }

  // public Command pivotUpCommand(double speed) {
  // return m_robotCoralPivot.pivotUp(speed);
  // }

  // public Command pivotDownCommand(double speed) {
  // return m_robotCoralPivot.pivotDown(speed);
  // }

  public Command elevatorMoveCommand(double desiredValue) {
    System.out.println("elevatorMoveCommand: RUNNING");
    return m_robotElevator.manualElevatorMove(desiredValue);
  }

  public Command elevatorMoveCommandSet(double desiredValue) {
    System.out.println("elevatorMoveCommandSet: RUNNING");
    return m_robotElevator.manualElevatorMoveSet(desiredValue);
  }

  public Command driveCommand(DoubleSupplier vertical, DoubleSupplier horizontal, DoubleSupplier rotate) {
    System.out.println("driveCommand: RUNNING");
    return new TeleopSwerve(
        s_Swerve,
        () -> vertical.getAsDouble(),
        () -> horizontal.getAsDouble(),
        () -> rotate.getAsDouble(),
        () -> false);
  }

  // public Command autoDriveCommand(double vertical, double horizontal, double rotate) {
  //   return this.runEnd(
  //     () -> {
  //     driveCommand(vertical, horizontal, rotate);
  //   }, () -> {
  //     driveCommand(0, 0, 0);
  //   });
  // }

  public Command driveRotateUntilCommand(double angle) {
    return new RotateUntilCommand(s_Swerve, () -> angle);
  }

  public Command centerATagCommand() {
    return new LimeLightCenterATagCommand(s_Swerve);
  }

  public Command coralPrepCommand() {
    return new LimeLightCoralPrepCommand(s_Swerve);
  }

  public Command algaePrepCommand() {
    return new LimeLightAlgaePrepCommand(s_Swerve);
  }

  public Command lightSetCommand(String color, String side) {
    return m_robotLights.lightArea(color, side);
  }
}