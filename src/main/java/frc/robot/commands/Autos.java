package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.RobotArm;
import frc.robot.subsystems.RobotDrive;
import frc.robot.subsystems.RobotLaunch;

public final class Autos {

  public static Command autoDrive(RobotDrive m_robotDrive) {
    return new RunCommand(() -> m_robotDrive.driveForward(), m_robotDrive)
      .withTimeout(5)
      .andThen(new RunCommand(() -> m_robotDrive.driveStop(), m_robotDrive));
  }

  public static Command autoIntake(
    RobotArm m_robotArm,
    RobotDrive m_robotDrive,
    RobotLaunch m_robotLaunch
  ) {
    return new RunCommand(() -> m_robotArm.manualArmMove(.16), m_robotArm)
      .withTimeout(1.0)
      .andThen(
        new RunCommand(() -> m_robotDrive.driveBackwards(), m_robotDrive)
      )
      .alongWith(new RunCommand(() -> m_robotLaunch.intake(), m_robotLaunch))
      .withTimeout(1.0);
  }

  public static Command autoLaunch(RobotLaunch m_robotLaunch) {
    return new RunCommand(() -> m_robotLaunch.launchPrep(), m_robotLaunch)
      .withTimeout(.15)
      .andThen(new RunCommand(() -> m_robotLaunch.prepStop(), m_robotLaunch))
      .withTimeout(0.5)
      .andThen(new RunCommand(() -> m_robotLaunch.launch(), m_robotLaunch))
      .withTimeout(2.5)
      .andThen(new RunCommand(() -> m_robotLaunch.stop()));
  }

  public static Command autoCenter(RobotDrive m_robotDrive) {
    return new RunCommand(() -> m_robotDrive.aCenterATag(), m_robotDrive);
  }

  public static Command autoMoveAim(
    RobotArm m_robotArm,
    RobotDrive m_robotDrive
  ) {
    return new RunCommand(() -> m_robotDrive.autoMove(), m_robotDrive)
      .alongWith(new RunCommand(() -> m_robotArm.autoAim(), m_robotArm));
  }

  public static Command autoBrakeRelease(RobotArm m_robotRelay) {
    return new RunCommand(() -> m_robotRelay.relayOn(), m_robotRelay);
  }

  public void robotPeriodic() {}

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}