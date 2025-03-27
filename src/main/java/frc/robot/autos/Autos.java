package frc.robot.autos;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.GameCommands;
import frc.robot.Constants;

public class Autos {

  private final GameCommands m_gameCommands; 

  public Autos(
    GameCommands gameCommands
  ) {
    m_gameCommands = gameCommands;
  }

  public Command autoCommand1() {
    return Commands
      .sequence(
        m_gameCommands.elevatorMoveCommand(-7).withTimeout(1).asProxy(),
        m_gameCommands.driveRotateUntilCommand(300).withTimeout(Constants.AutoConstants.timeSpinning),
        m_gameCommands.driveCommand(() -> Constants.AutoConstants.speedDriving, () -> 0, () -> 0).withTimeout(Constants.AutoConstants.timeForward),
        m_gameCommands.coralPrepCommand().withTimeout(Constants.AutoConstants.timeCentering),
        m_gameCommands.driveCommand(() -> 0, () -> 0, () -> 0).withTimeout(0),
        m_gameCommands.elevatorMoveCommand(Constants.AutoConstants.positionEleTop).withTimeout(Constants.AutoConstants.timeEleMoving).asProxy(),
        m_gameCommands.coralPivotPositionSetCommand(Constants.AutoConstants.positionCoralPivot).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
        m_gameCommands.runCoralLaunchCommand(Constants.AutoConstants.speedLaunching).withTimeout(Constants.AutoConstants.timeLaunching),
        m_gameCommands.coralPivotPositionSetCommand(-50).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
        m_gameCommands.elevatorMoveCommand(Constants.AutoConstants.positionEleBottom).withTimeout(Constants.AutoConstants.timeEleMoving).asProxy(),
        m_gameCommands.algaePivotPositionSetCommand(Constants.AutoConstants.positionAlgaePivot).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
        m_gameCommands.runAlgaeIntakeCommand(Constants.AutoConstants.speedIntaking).withTimeout(Constants.AutoConstants.timeIntaking)
      )
      .withName("autoCommand");
  }
  public Command autoCommand2() {
    return Commands
      .sequence(
        m_gameCommands.elevatorMoveCommand(-7).withTimeout(1).asProxy(),
        m_gameCommands.driveRotateUntilCommand(0).withTimeout(Constants.AutoConstants.timeSpinning),
        m_gameCommands.driveCommand(() -> Constants.AutoConstants.speedDriving, () -> 0, () -> 0).withTimeout(Constants.AutoConstants.timeForward),
        m_gameCommands.coralPrepCommand().withTimeout(Constants.AutoConstants.timeCentering),
        m_gameCommands.driveCommand(() -> 0, () -> 0, () -> 0).withTimeout(0),
        m_gameCommands.elevatorMoveCommand(Constants.AutoConstants.positionEleTop).withTimeout(Constants.AutoConstants.timeEleMoving).asProxy(),
        m_gameCommands.coralPivotPositionSetCommand(Constants.AutoConstants.positionCoralPivot).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
        m_gameCommands.runCoralLaunchCommand(Constants.AutoConstants.speedLaunching).withTimeout(Constants.AutoConstants.timeLaunching),
        m_gameCommands.coralPivotPositionSetCommand(-50).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
        m_gameCommands.elevatorMoveCommand(Constants.AutoConstants.positionEleBottom).withTimeout(Constants.AutoConstants.timeEleMoving).asProxy(),
        m_gameCommands.algaePivotPositionSetCommand(Constants.AutoConstants.positionAlgaePivot).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
        m_gameCommands.runAlgaeIntakeCommand(Constants.AutoConstants.speedIntaking).withTimeout(Constants.AutoConstants.timeIntaking)
      )
      .withName("autoCommand");
  }

public Command autoCommand3() {
  return Commands
    .sequence(
      m_gameCommands.elevatorMoveCommand(-7).withTimeout(1).asProxy(),
      m_gameCommands.driveRotateUntilCommand(60).withTimeout(Constants.AutoConstants.timeSpinning),
      m_gameCommands.driveCommand(() -> Constants.AutoConstants.speedDriving, () -> 0, () -> 0).withTimeout(Constants.AutoConstants.timeForward),
      m_gameCommands.coralPrepCommand().withTimeout(Constants.AutoConstants.timeCentering),
      m_gameCommands.driveCommand(() -> 0, () -> 0, () -> 0).withTimeout(0),
      m_gameCommands.elevatorMoveCommand(Constants.AutoConstants.positionEleTop).withTimeout(Constants.AutoConstants.timeEleMoving).asProxy(),
      m_gameCommands.coralPivotPositionSetCommand(Constants.AutoConstants.positionCoralPivot).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
      m_gameCommands.runCoralLaunchCommand(Constants.AutoConstants.speedLaunching).withTimeout(Constants.AutoConstants.timeLaunching),
      m_gameCommands.coralPivotPositionSetCommand(-50).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
      m_gameCommands.elevatorMoveCommand(Constants.AutoConstants.positionEleBottom).withTimeout(Constants.AutoConstants.timeEleMoving).asProxy(),
      m_gameCommands.algaePivotPositionSetCommand(Constants.AutoConstants.positionAlgaePivot).withTimeout(Constants.AutoConstants.timePivoting).asProxy(),
      m_gameCommands.runAlgaeIntakeCommand(Constants.AutoConstants.speedIntaking).withTimeout(Constants.AutoConstants.timeIntaking)
    )
    .withName("autoCommand");
}

public Command autoCommandTEST() {
  return Commands
    .sequence(
      m_gameCommands.driveRotateUntilCommand(240).withTimeout(4),
      m_gameCommands.driveCommand(() -> -.2, () -> 0, () -> 0).withTimeout(0.5),
      m_gameCommands.driveCommand(() -> 0, () -> 0, () -> 0).withTimeout(0),
      m_gameCommands.elevatorMoveCommand(-10).withTimeout(3).asProxy()
    )
    .withName("autoCommand");
}

}