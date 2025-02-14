package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.GameCommands;

public class AutoCommands {

  private final GameCommands m_gameCommands;

  public double tagID;

  public AutoCommands(
    GameCommands gameCommands) {

    m_gameCommands = gameCommands;

  }

  public Command autoCommand1() {
    return Commands
      .sequence(
        m_gameCommands.driveRotateUntil180Command(0).withTimeout(1).andThen(
        m_gameCommands.driveCommand(1, 0, 0))/*.until((tagID == 20))*/
      )
      .withName("autoCommand");
  }

public void robotPeriodic(){
    tagID = SmartDashboard.getNumber("Tag ID", 0);
}

}