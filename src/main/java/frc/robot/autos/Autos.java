package frc.robot.autos;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.GameCommands;

public class Autos {

  private final GameCommands m_gameCommands;

  double gyroAngle;

  public Autos(
    GameCommands gameCommands
  ) {
    m_gameCommands = gameCommands;
  }

  public Command autoCommand1() {
    return Commands
      .sequence(
        m_gameCommands.moveArmLP().withTimeout(2),
        m_gameCommands
          .moveArmLP()
          .alongWith(m_gameCommands.runLaunchCommand())
          .withTimeout(2.15),
        m_gameCommands.moveArmIP().alongWith(m_gameCommands.turnLAngle()).withTimeout(.9),
        m_gameCommands
          .runIntakeCommand()
          .alongWith(m_gameCommands.moveArmIP())
          .alongWith(m_gameCommands.runBDrive())
          .withTimeout(1.75),
        m_gameCommands
          .moveArmLP()
          .alongWith(m_gameCommands.runFDrive())
          .withTimeout(2),
        m_gameCommands.turnCenter().withTimeout(.60),
        m_gameCommands
          .moveArmLP()
          .alongWith(m_gameCommands.runLaunchCommand())
          .withTimeout(2.15)
      )
      .withName("autoCommand");
  }
  public Command autoCommand2() {
    return Commands
      .sequence(
        // m_gameCommands.runRelayOn(),
        m_gameCommands.moveArmLP().withTimeout(2),
        m_gameCommands
          .moveArmLP()
          .alongWith(m_gameCommands.runLaunchCommand())
          .withTimeout(2.15),
        m_gameCommands.moveArmIP().withTimeout(1),
        m_gameCommands
          .runIntakeCommand()
          .alongWith(m_gameCommands.runBDrive())
          .withTimeout(1.25),
        m_gameCommands
          .moveArmLP()
          .alongWith(m_gameCommands.runFDrive())
          .withTimeout(1.25),
        m_gameCommands
          .moveArmLP()
          .alongWith(m_gameCommands.runLaunchCommand())
          .withTimeout(2.15)
      )
      .withName("autoCommand");
  }

public Command autoCommand3() {
  return Commands
    .sequence(
      // m_gameCommands.runRelayOn(),
      m_gameCommands.moveArmLP().withTimeout(2),
      m_gameCommands
        .moveArmLP()
        .alongWith(m_gameCommands.runLaunchCommand())
        .withTimeout(2.15),
      m_gameCommands.moveArmIP().alongWith(m_gameCommands.turnRAngle()).withTimeout(1.5),
      m_gameCommands
        .runIntakeCommand()
        .alongWith(m_gameCommands.runBDrive())
        .withTimeout(2),
      m_gameCommands
        .moveArmLP()
        .alongWith(m_gameCommands.runFDrive())
        .withTimeout(2),
      m_gameCommands.turnCenter().alongWith(m_gameCommands.moveArmLP()).withTimeout(.65),
      m_gameCommands
        .moveArmLP()
        .alongWith(m_gameCommands.runLaunchCommand())
        .withTimeout(2.15)
    )
    .withName("autoCommand");
}

public void robotPeriodic(){
  gyroAngle = SmartDashboard.getNumber("Gyro", 0);
}

}