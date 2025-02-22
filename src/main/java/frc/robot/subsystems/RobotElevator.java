package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.AbsoluteEncoder;
// import com.revrobotics.RelativeEncoder;

// import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotElevator extends SubsystemBase {

  SparkMax elevatorWheel;
  AbsoluteEncoder elevatorAbsEncoder;
  double encoderDistance;

    public RobotElevator() {
    final int wheelDeviceID = 13;
    elevatorWheel = new SparkMax(wheelDeviceID, MotorType.kBrushless);
    elevatorAbsEncoder = elevatorWheel.getAbsoluteEncoder();
    };

  public Command elevatorUp(double speed) {
    return this.runEnd(
        () -> {
          setWheelSpeed(-speed);
        },
        () -> {
          stop();
        }
    );
  }

  public Command elevatorDown(double speed) {
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
    elevatorWheel.set(speed);
  }

  public void stop() {
    elevatorWheel.set(0);
  }
  
  @Override
  public void periodic() {
    encoderDistance = elevatorAbsEncoder.getPosition();
    SmartDashboard.putNumber("Elevator Encoder", encoderDistance);
  }
}
