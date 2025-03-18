package frc.robot.autos;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.GameCommands;

public class Autos {

  private final GameCommands m_gameCommands;

  double gyroAngle;
  double elevatorPoint;
  double coralPivotPoint;
  double algaePivotPoint;
  double tagArea;
  double timeForward;
  double timeSpinning;
  double timeCentering;
  double timeEleMoving;
  double timePivoting;
  double timeLaunching;
  double timeIntaking;
  double speedLaunching;
  double speedIntaking;
  double speedDriving;
  double positionEleTop;
  double positionEleBottom;
  double positionCoralPivot;
  double positionAlgaePivot;

  public Autos(
    GameCommands gameCommands
  ) {
    m_gameCommands = gameCommands;
  }

  public Command autoCommand1() {
    return Commands
      .sequence(
        m_gameCommands.driveCommand(0, 0, gyroAngle - 120).withTimeout(timeSpinning),
        m_gameCommands.driveCommand(speedDriving, 0, 0).withTimeout(timeForward),
        m_gameCommands.coralPrepCommand().withTimeout(timeCentering),
        m_gameCommands.elevatorMoveCommand(positionEleTop).withTimeout(timeEleMoving),
        m_gameCommands.coralPivotPositionSetCommand(positionCoralPivot).withTimeout(timePivoting),
        m_gameCommands.runCoralLaunchCommand(speedLaunching).withTimeout(timeLaunching),
        m_gameCommands.coralPivotPositionSetCommand(0).withTimeout(timePivoting),
        m_gameCommands.elevatorMoveCommand(positionEleBottom).withTimeout(timeEleMoving),
        m_gameCommands.algaePivotPositionSetCommand(positionAlgaePivot).withTimeout(timePivoting),
        m_gameCommands.runAlgaeIntakeCommand(speedIntaking).withTimeout(timeIntaking)
      )
      .withName("autoCommand");
  }
  public Command autoCommand2() {
    return Commands
      .sequence(
        m_gameCommands.driveCommand(0, 0, gyroAngle - 180).withTimeout(timeSpinning),
        m_gameCommands.driveCommand(speedDriving, 0, 0).withTimeout(timeForward),
        m_gameCommands.coralPrepCommand().withTimeout(timeCentering),
        m_gameCommands.elevatorMoveCommand(positionEleTop).withTimeout(timeEleMoving),
        m_gameCommands.coralPivotPositionSetCommand(positionCoralPivot).withTimeout(timePivoting),
        m_gameCommands.runCoralLaunchCommand(speedLaunching).withTimeout(timeLaunching),
        m_gameCommands.coralPivotPositionSetCommand(0).withTimeout(timePivoting),
        m_gameCommands.elevatorMoveCommand(positionEleBottom).withTimeout(timeEleMoving),
        m_gameCommands.algaePivotPositionSetCommand(positionAlgaePivot).withTimeout(timePivoting),
        m_gameCommands.runAlgaeIntakeCommand(speedIntaking).withTimeout(timeIntaking)
      )
      .withName("autoCommand");
  }

public Command autoCommand3() {
  return Commands
    .sequence(
      m_gameCommands.driveCommand(0, 0, gyroAngle - 240).withTimeout(timeSpinning),
      m_gameCommands.driveCommand(speedDriving, 0, 0).withTimeout(timeForward),
      m_gameCommands.coralPrepCommand().withTimeout(timeCentering),
      m_gameCommands.elevatorMoveCommand(positionEleTop).withTimeout(timeEleMoving),
      m_gameCommands.coralPivotPositionSetCommand(positionCoralPivot).withTimeout(timePivoting),
      m_gameCommands.runCoralLaunchCommand(speedLaunching).withTimeout(timeLaunching),
      m_gameCommands.coralPivotPositionSetCommand(0).withTimeout(timePivoting),
      m_gameCommands.elevatorMoveCommand(positionEleBottom).withTimeout(timeEleMoving),
      m_gameCommands.algaePivotPositionSetCommand(positionAlgaePivot).withTimeout(timePivoting),
      m_gameCommands.runAlgaeIntakeCommand(speedIntaking).withTimeout(timeIntaking)
    )
    .withName("autoCommand");
}

public void robotPeriodic(){
  gyroAngle = SmartDashboard.getNumber("Gyro", 0);
  elevatorPoint = SmartDashboard.getNumber("Elevator Point", 0);
  coralPivotPoint = SmartDashboard.getNumber("Coral Pivot Point", 0);
  algaePivotPoint = SmartDashboard.getNumber("Algae Pivot Point", 0);
  timeSpinning = SmartDashboard.getNumber("Autonomous Time Spinning", 2);
  timeForward = SmartDashboard.getNumber("Autonomous Time Forward", 1);
  timeCentering = SmartDashboard.getNumber("Autonomous Time Centering", 1);
  timeEleMoving = SmartDashboard.getNumber("Autonomous Time Moving Elevator", 1);
  timePivoting = SmartDashboard.getNumber("Autonomous Time Pivoting", 1);
  timeLaunching = SmartDashboard.getNumber("Autonomous Time Launching", 1);
  timeIntaking = SmartDashboard.getNumber("Autonomous Time Intaking", 1);
  speedDriving = SmartDashboard.getNumber("Autonomous Speed Driving", .5);
  speedLaunching = SmartDashboard.getNumber("Autonomous Speed Launching", .5);
  speedIntaking = SmartDashboard.getNumber("Autonomous Speed Intaking", .5);
  positionEleTop = SmartDashboard.getNumber("Autonomous Position Elevator Top", -95);
  positionEleBottom = SmartDashboard.getNumber("Autonomous Position Elevator Bottom", -5);
  positionCoralPivot = SmartDashboard.getNumber("Autonomous Position Coral Pivot", -700);
  positionAlgaePivot = SmartDashboard.getNumber("Autonomous Position Algae Pivot", -4);
}
}