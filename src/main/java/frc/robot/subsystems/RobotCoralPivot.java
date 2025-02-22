package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

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

  public Command pivotTo(double speed, double desiredValue) {
    return this.run(
        () -> {
          setPivotSpeed(speed * (-pivotEncoderValue + desiredValue));
        }
      ).withInterruptBehavior(Command.InterruptionBehavior.kCancelSelf);
    }

    public Command setPivotCommand(double speed, double pivotPoint) {
      return this.run(
          () -> {
            SmartDashboard.putNumber("Pivot Speed", speed);
            SmartDashboard.putNumber("Pivot Point", pivotPoint);
          }
        );
      }

    public Command changePivotPointCommand(double pivotPoint) {
      return this.run(
          () -> {
            SmartDashboard.putNumber("Pivot Point", pivotPoint + SmartDashboard.getNumber("Pivot Point", 0));
          }
        );
      }

  public Command pivotUp(double speed) {
    return this.runEnd(
        () -> {
          setPivotSpeed(speed);
        },
        () -> {
          stop();
        }
      );
  }

  public Command pivotDown(double speed) {
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