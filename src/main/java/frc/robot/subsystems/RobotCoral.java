package frc.robot.subsystems;


import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotCoral extends SubsystemBase {

  SparkMax leftWheel;
  SparkMax rightWheel;

    private DigitalInput limitSwitch;

  public RobotCoral() {
    final int leftWheelDeviceID = 11;
    final int rightWheelDeviceID = 12;
    leftWheel = new SparkMax(leftWheelDeviceID, MotorType.kBrushless);
    rightWheel = new SparkMax(rightWheelDeviceID, MotorType.kBrushless);

    limitSwitch = new DigitalInput(5);

  }

  public Command launch(double speed) {
    return this.runEnd(
        () -> {
            setWheelSpeed(-speed, -speed);
        },
        () -> {
          stop();
        }
      );
  }

  public Command intake(double speed) {
    return this.runEnd(
        () -> {
          if(limitSwitch.get() == false) {
            setWheelSpeed(speed, speed);
          } else {
            stop();
          }
        },
        () -> {
          stop();
        }
      );
  }

  public void setWheelSpeed(double speed1, double speed2) {
    leftWheel.set(speed1);
    rightWheel.set(-speed2);
  }

  public void stop() {
    leftWheel.set(0);
    rightWheel.set(0);
  }
}