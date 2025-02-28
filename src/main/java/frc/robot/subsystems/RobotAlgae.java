package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotAlgae extends SubsystemBase {

  SparkMax wheel;

  private DigitalInput limitSwitch;

  public RobotAlgae() {
    final int wheelDeviceID = 14;
    wheel = new SparkMax(wheelDeviceID, MotorType.kBrushless);
    limitSwitch = new DigitalInput(0);
  }

  public Command launch(double speed) {
    return this.runEnd(
        () -> {
          setWheelSpeed(-speed);
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
            setWheelSpeed(speed);
          } else {
            stop();
          }
        },
        () -> {
          stop();
        }
    );
  }

  public void setWheelSpeed(double speed) {
    wheel.set(speed);
  }

  public void stop() {
    wheel.set(0);
  }

}