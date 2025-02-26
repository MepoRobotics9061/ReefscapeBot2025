package frc.robot.subsystems;


import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotCoral extends SubsystemBase {

  SparkMax leftWheel;
  SparkMax rightWheel;

  public RobotCoral() {
    final int leftWheelDeviceID = 11;
    final int rightWheelDeviceID = 12;
    leftWheel = new SparkMax(leftWheelDeviceID, MotorType.kBrushless);
    rightWheel = new SparkMax(rightWheelDeviceID, MotorType.kBrushless);
  }

  public Command launch(double speed) {
    return this.runEnd(
        () -> {
          setLauncherSpeed(-speed, -speed);
        },
        () -> {
          stop();
        }
      );
  }

  public Command intake(double speed) {
    return this.runEnd(
        () -> {
          setLauncherSpeed(speed, speed);
        },
        () -> {
          stop();
        }
      );
  }

  public void setLauncherSpeed(double speed1, double speed2) {
    leftWheel.set(speed1);
    rightWheel.set(-speed2);
  }

  public void stop() {
    leftWheel.set(0);
    rightWheel.set(0);
  }
}