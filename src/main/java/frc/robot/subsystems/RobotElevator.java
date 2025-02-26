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

    public Command manualElevatorMove(double manualAngle) {
      return this.runEnd(
          () -> {
            SmartDashboard.putNumber("Elevator Point", manualAngle);
            if (elevatorEncoderValue > (manualAngle + 2)) {
              setSpeed(-.2);
            } else if (elevatorEncoderValue < (manualAngle - 2)) {
              setSpeed(.2);
            } else {
              setSpeed((manualAngle - elevatorEncoderValue) * .1);
            }
          },
          () -> {
            stop();
          }
        );
    }
  
    public void voidElevatorMove(double manualAngle) {
      SmartDashboard.putNumber("Elevator Point", manualAngle);
      if (elevatorEncoderValue > (manualAngle + 2)) {
        setSpeed(-.2);
      } else if (elevatorEncoderValue < (manualAngle - 2)) {
        setSpeed(.2);
      } else {
        setSpeed((manualAngle - elevatorEncoderValue) * .1);
      }
    }
  
    public void setSpeed(double speed) {
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