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

  public Autos(
    GameCommands gameCommands
  ) {
    m_gameCommands = gameCommands;
  }

  public Command autoCommand1() {
    return Commands
      .sequence(
        m_gameCommands.driveCommand(0, 0, gyroAngle - 120).withTimeout(2),
        m_gameCommands.driveCommand(.5, 0, 0).withTimeout(1),
        m_gameCommands.coralPrepCommand().withTimeout(1),
        m_gameCommands.elevatorMoveCommand(-20).withTimeout(1),
        m_gameCommands.coralPivotPositionSetCommand(-20).withTimeout(1),
        m_gameCommands.runCoralLaunchCommand(1).withTimeout(1),
        m_gameCommands.coralPivotPositionSetCommand(0).withTimeout(1),
        m_gameCommands.elevatorMoveCommand(-10).withTimeout(1),
        m_gameCommands.algaePivotPositionSetCommand(-10).withTimeout(1),
        m_gameCommands.runAlgaeLaunchCommand(1).withTimeout(1)
      )
      .withName("autoCommand");
  }
  public Command autoCommand2() {
    return Commands
      .sequence(
        m_gameCommands.driveCommand(0, 0, gyroAngle - 180).withTimeout(2),
        m_gameCommands.driveCommand(.5, 0, 0).withTimeout(1),
        m_gameCommands.coralPrepCommand().withTimeout(1),
        m_gameCommands.elevatorMoveCommand(-10).withTimeout(1),
        m_gameCommands.coralPivotPositionSetCommand(-20).withTimeout(1),
        m_gameCommands.runCoralLaunchCommand(1).withTimeout(1),
        m_gameCommands.coralPivotPositionSetCommand(0).withTimeout(1),
        m_gameCommands.elevatorMoveCommand(-10).withTimeout(1),
        m_gameCommands.algaePivotPositionSetCommand(-10).withTimeout(1),
        m_gameCommands.runAlgaeLaunchCommand(1).withTimeout(1)
      )
      .withName("autoCommand");
  }

public Command autoCommand3() {
  return Commands
    .sequence(
      m_gameCommands.driveCommand(0, 0, gyroAngle - 240).withTimeout(2),
      m_gameCommands.driveCommand(.5, 0, 0).withTimeout(1),
      m_gameCommands.coralPrepCommand().withTimeout(1),
      m_gameCommands.elevatorMoveCommand(-20).withTimeout(1),
      m_gameCommands.coralPivotPositionSetCommand(-20).withTimeout(1),
      m_gameCommands.runCoralLaunchCommand(1).withTimeout(1),
      m_gameCommands.coralPivotPositionSetCommand(0).withTimeout(1),
      m_gameCommands.elevatorMoveCommand(-10).withTimeout(1),
      m_gameCommands.algaePivotPositionSetCommand(-10).withTimeout(1),
      m_gameCommands.runAlgaeLaunchCommand(1).withTimeout(1)
    )
    .withName("autoCommand");
}

public void robotPeriodic(){
  gyroAngle = SmartDashboard.getNumber("Gyro", 0);
  elevatorPoint = SmartDashboard.getNumber("Elevator Point", 0);
  coralPivotPoint = SmartDashboard.getNumber("Coral Pivot Point", 0);
  algaePivotPoint = SmartDashboard.getNumber("Algae Pivot Point", 0);
  tagArea = SmartDashboard.getNumber("Tag Area", 0);
}

}