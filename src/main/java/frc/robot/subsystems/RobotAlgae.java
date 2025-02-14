package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotAlgae extends SubsystemBase {

  SparkMax wheel;

  public RobotAlgae() {
    final int wheelDeviceID = 11;
    wheel = new SparkMax(wheelDeviceID, MotorType.kBrushless);
  }

  public Command launch(double speed) {
    return this.runEnd(
        () -> {
          setWheelSpeed(-speed);
        },
        () -> {
          stop();
        }
    );
  }

  public Command intake(double speed) {
    return this.runEnd(
        () -> {
          setWheelSpeed(speed);
        },
        () -> {
          stop();
        }
    );
  }

  public void setWheelSpeed(double speed) {
    wheel.set(speed);
  }

  public void stop() {
    wheel.set(0);
  }
}