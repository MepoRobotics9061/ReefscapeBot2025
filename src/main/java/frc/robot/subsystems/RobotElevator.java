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

  private double gainSpeed = -0.06;

  private double targetPosition = -5;

  private double angleOffset = 0;

  private boolean isAuto = false;

  public RobotElevator() {
    final int pivotWheelDeviceID = 9;
    elevatorWheel = new SparkMax(pivotWheelDeviceID, MotorType.kBrushless);
    elevatorEncoder = elevatorWheel.getEncoder();
    }

    public Command manualElevatorMove(double manualAngle) {
      return this.runEnd(
          () -> {
            voidElevatorMove(manualAngle);
          },
          () -> {
            stop();
          }
        );
    }

    public Command manualElevatorMoveSet(double manualAngle) {
      return this.runEnd(
          () -> {
            voidElevatorMove(manualAngle);
          },
          () -> {
            doNothing();
          }
        );
    }
  
    public void voidElevatorMove(double manualAngle) {
      manualAngle = Math.max(Math.min(manualAngle - angleOffset, -4), -95);
      SmartDashboard.putNumber("Elevator Offset", angleOffset);
      
      double targetSpeed = (manualAngle - elevatorEncoderValue) * .2 + gainSpeed;
      SmartDashboard.putNumber("Elevator Point", manualAngle);

      if(targetSpeed < -.4) {
        targetSpeed = -.4;
      }
      if(targetSpeed > .1) {
        targetSpeed = .1;
      }
      setSpeed(targetSpeed);
      SmartDashboard.putNumber("Elevator Speed", targetSpeed);
    }

    public Command elevatorMove(double speed) {
      return this.runEnd(
          () -> {
      setSpeed(speed);
          }, 
          () -> {
            stop();
          }
      );
    }

    public Command bumpPosition(boolean bumpUp) {
      return this.runOnce(
        () -> {
          if(bumpUp == true) {
            offsetUp();
          }
          else {
            offsetDown();
          }
        }
      );
    }
  
    public void offsetUp() {
      angleOffset += 1;
      if (angleOffset > 5) {
        angleOffset = 5;
      }
    }

    public void offsetDown() {
      angleOffset -= 1;
      if (angleOffset < -5) {
        angleOffset = -5;
      }
    }

    public void setSpeed(double speed) {
      elevatorWheel.set(speed);
    }
  
    public void stop() {
      elevatorWheel.set(0);
    }

    public void doNothing(){

    }

    public void setPosition(double position) {
      SmartDashboard.putNumber("Elevator Point", position);
      targetPosition = position;
    }

    public boolean inPosition() {
      return Math.abs(targetPosition - elevatorEncoderValue) < .5;
    }

  @Override public void periodic() {
    elevatorEncoderValue = elevatorEncoder.getPosition();
    SmartDashboard.putNumber("Elevator Encoder", elevatorEncoderValue);

    isAuto = SmartDashboard.getBoolean("AutoMode", false);

 
    
  }
}