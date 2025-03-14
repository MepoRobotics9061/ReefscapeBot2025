package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotHang extends SubsystemBase{

    SparkMax leftHangWheel;
    SparkMax rightHangWheel;
    private RelativeEncoder leftEncoder;
    private RelativeEncoder rightEncoder;
    private double leftEncoderValue;
    private double rightEncoderValue;

    public RobotHang() {
        final int leftHangWheelDeviceID = 15;
        final int rightHangWheelDeviceID = 16;
        leftHangWheel = new SparkMax(leftHangWheelDeviceID, MotorType.kBrushless);
        rightHangWheel = new SparkMax(rightHangWheelDeviceID, MotorType.kBrushless);
        leftEncoder = leftHangWheel.getEncoder();
        rightEncoder = rightHangWheel.getEncoder();
    }

    public Command hangClose() {
        return this.runEnd(
            () -> {
              if(leftEncoderValue < 5) {
                  setSpeed(1);
              } else if(leftEncoderValue > 15) {
                  setSpeed(-1);
              } else {
                  setSpeed((10 - leftEncoderValue) / 5);
              }
          },
            () -> {
              stop();
            }
          );
      }  
      
      public Command hangOpen() {
        return this.runEnd(
            () -> {
              if(leftEncoderValue < -5) {
                  setSpeed(1);
              } else if(leftEncoderValue > 5) {
                  setSpeed(-1);
              } else {
                  setSpeed((0 - leftEncoderValue) / 5);
              }
          },
            () -> {
              stop();
            }
          );
      }

    public Command hangMove(double speed) {
      return this.runEnd(
          () -> {
      setSpeed(speed);
          }, 
          () -> {
            stop();
          }
      );
    }
  
    public void setSpeed(double speed) {
        leftHangWheel.set(speed);
        rightHangWheel.set(-speed);
    }
  
    public void stop() {
        leftHangWheel.set(0);
        rightHangWheel.set(0);
    }

    public void getEncoderValue() {

    }

  @Override public void periodic() {
    leftEncoderValue = leftEncoder.getPosition();
    SmartDashboard.putNumber("Hang Left Encoder", leftEncoderValue);
    SmartDashboard.putNumber("Hang Right Encoder", rightEncoderValue);
    }
}
