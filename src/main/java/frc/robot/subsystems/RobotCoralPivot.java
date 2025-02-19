package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class RobotCoralPivot extends SubsystemBase {

  SparkMax pivotWheel;

  private RelativeEncoder pivotEncoder;

  private double pivotEncoderValue;

  public RobotCoralPivot() {
    final int pivotWheelDeviceID = 9;
    pivotWheel = new SparkMax(pivotWheelDeviceID, MotorType.kBrushless);
    pivotEncoder = pivotWheel.getEncoder();
    }

  public Command moveTo(double speed, double desiredValue) {
    return this.run(
        () -> {
          setPivotSpeed(speed * (-pivotEncoderValue + desiredValue));
        }
      ).withInterruptBehavior(Command.InterruptionBehavior.kCancelSelf);
    }

  public Command moveUp(double speed) {
    return this.runEnd(
        () -> {
          setPivotSpeed(speed);
        },
        () -> {
          stop();
        }
      );
  }

  public Command moveDown(double speed) {
    return this.runEnd(
        () -> {
          setPivotSpeed(-speed);
        },
        () -> {
          stop();
        }
      );
  }

  public void setPivotSpeed(double speed) {
    pivotWheel.set(speed);
  }

  public void stop() {
    pivotWheel.set(0);
  }

  @Override public void periodic() {
    pivotEncoderValue = pivotEncoder.getPosition();
    SmartDashboard.putNumber("Pivot Encoder", pivotEncoderValue);
  }
}