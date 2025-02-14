package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;

public class RotateUntil180Command extends Command {
  private Swerve s_Swerve;

  public RotateUntil180Command(
  Swerve s_Swerve
  ){
    this.s_Swerve = s_Swerve;
    addRequirements(s_Swerve);
  }

  @Override
  public void execute() {
    s_Swerve.rotateUntilVoid(180);
  }

}