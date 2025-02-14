package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkMax;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotElevator extends SubsystemBase {

    SparkMax elevatorWheel;
    // Encoder encoder;
    // RelativeEncoder encoderDistance;

    public RobotElevator() {
    final int wheelDeviceID = 15;
    elevatorWheel = new SparkMax(wheelDeviceID, MotorType.kBrushless);
        
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
    // encoderDistance = elevatorWheel.getEncoder();
    // SmartDashboard.putData("Elevator Encoder", (Sendable) encoderDistance);
  }
}
