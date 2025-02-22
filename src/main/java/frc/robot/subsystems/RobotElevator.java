package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class RobotElevator extends SubsystemBase {

  SparkMax elevatorWheel;

  private RelativeEncoder elevatorEncoder;

  private double elevatorEncoderValue;

  public RobotElevator() {
    final int pivotWheelDeviceID = 9;
    elevatorWheel = new SparkMax(pivotWheelDeviceID, MotorType.kBrushless);
    elevatorEncoder = elevatorWheel.getEncoder();
    }

  public Command moveElevatorTo(double speed, double desiredValue) {
    return this.run(
        () -> {
          setElevatorSpeed(speed * (-elevatorEncoderValue + desiredValue));
        }
      ).withInterruptBehavior(Command.InterruptionBehavior.kCancelSelf);
    }

    public Command setPivotCommand(double speed, double elevatorPoint) {
      return this.run(
          () -> {
            SmartDashboard.putNumber("Elevator Speed", speed);
            SmartDashboard.putNumber("Elevator Point", elevatorPoint);
          }
        );
      }

    public Command changeElevatorPointCommand(double elevatorPoint) {
      return this.run(
          () -> {
            SmartDashboard.putNumber("Elevator Point", elevatorPoint + SmartDashboard.getNumber("Elevator Point", 0));
          }
        );
      }

  public Command elevatorUp(double speed) {
    return this.runEnd(
        () -> {
          setElevatorSpeed(speed);
        },
        () -> {
          stop();
        }
      );
  }

  public Command elevatorDown(double speed) {
    return this.runEnd(
        () -> {
          setElevatorSpeed(-speed);
        },
        () -> {
          stop();
        }
      );
  }

  public void setElevatorSpeed(double speed) {
    elevatorWheel.set(speed);
  }

  public void stop() {
    elevatorWheel.set(0);
  }

  @Override public void periodic() {
    elevatorEncoderValue = elevatorEncoder.getPosition();
    SmartDashboard.putNumber("Elevator Encoder", elevatorEncoderValue);
  }
}