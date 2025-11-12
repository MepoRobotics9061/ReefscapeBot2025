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
    rightWheel = new SparkMax(rightWheelDeviceID, MotorType.kBrushed);

    limitSwitch = new DigitalInput(5);

  }

  public Command launch(double speed) {
    return this.runEnd(
        () -> {
            setWheelSpeed(speed);
        },
        () -> {
          stop();
        }
      );
  }

  public Command intake(double speed) {
    return this.runEnd(
        () -> {
          // if(limitSwitch.get() == false) {
            setWheelSpeed(-speed);
          // } else {
          //   stop();
          // }
        },
        () -> {
          stop();
        }
      );
  }

  public void setWheelSpeed(double speed) {
    leftWheel.set(speed);
    rightWheel.set(-speed*.2);
  }

  public void stop() {
    leftWheel.set(0);
    rightWheel.set(0);
  }
}