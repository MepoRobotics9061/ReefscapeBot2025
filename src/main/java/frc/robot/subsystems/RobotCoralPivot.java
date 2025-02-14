package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class RobotCoralPivot extends SubsystemBase {

  SparkMax leftPivotWheel;
  SparkMax rightPivotWheel;

  public RobotCoralPivot() {
    final int leftPivotWheelDeviceID = 13;
    final int rightPivotWheelDeviceID = 14;
    leftPivotWheel = new SparkMax(leftPivotWheelDeviceID, MotorType.kBrushless);
    rightPivotWheel = new SparkMax(rightPivotWheelDeviceID, MotorType.kBrushless);
    }

  public Command moveUp(double speed) {
    return this.runEnd(
        () -> {
          setPivotSpeed(-speed, -speed);
        },
        () -> {
          stop();
        }
      );
  }

  public Command moveDown(double speed) {
    return this.runEnd(
        () -> {
          setPivotSpeed(speed, speed);
        },
        () -> {
          stop();
        }
      );
  }

  public void setPivotSpeed(double speed1, double speed2) {
    leftPivotWheel.set(speed1);
    rightPivotWheel.set(speed2);
  }

  public void stop() {
    leftPivotWheel.set(0);
    rightPivotWheel.set(0);
  }
}