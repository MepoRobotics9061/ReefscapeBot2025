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

    public Command manualPivotMove(double manualAngle) {
      return this.runEnd(
          () -> {
            SmartDashboard.putNumber("Pivot Point", manualAngle);
            if (pivotEncoderValue > (manualAngle + 2)) {
              setSpeed(-.2);
            } else if (pivotEncoderValue < (manualAngle - 2)) {
              setSpeed(.2);
            } else {
              setSpeed((manualAngle - pivotEncoderValue) * .1);
            }
          },
          () -> {
            stop();
          }
        );
    }
  
    public void voidPivotMove(double manualAngle) {
      SmartDashboard.putNumber("Pivot Point", manualAngle);
      if (pivotEncoderValue > (manualAngle + 2)) {
        setSpeed(-.2);
      } else if (pivotEncoderValue < (manualAngle - 2)) {
        setSpeed(.2);
      } else {
        setSpeed((manualAngle - pivotEncoderValue) * .1);
      }
    }

    // public Command pivotUp(double speed) {
    //   return this.runEnd(
    //       () -> {
    //         SmartDashboard.putNumber("Manual Angle", SmartDashboard.getNumber("Manual Angle", 0 + speed));
    //         if (pivotEncoderValue < (manualAngle - .02)) {
    //           setSpeed(.2);
    //         } else if (pivotEncoderValue > (manualAngle + .02)) {
    //           setSpeed(-.2);
    //         } else {
    //           setSpeed((manualAngle - pivotEncoderValue) * .2);
    //         }
    //       },
    //       () -> {
    //         stop();
    //       }
    //     );
    // }
  
    public void setSpeed(double speed) {
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