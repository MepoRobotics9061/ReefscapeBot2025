package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotAlgaePivot extends SubsystemBase {

  SparkMax pivotWheel;

  private RelativeEncoder pivotEncoder;

  private double pivotEncoderValue;

  private double coralCurrentPosition;

  private double algaeCurrentPosition;

  public RobotAlgaePivot() {
    final int pivotWheelDeviceID = 13;
    pivotWheel = new SparkMax(pivotWheelDeviceID, MotorType.kBrushless);
    pivotEncoder = pivotWheel.getEncoder();
    }

    public Command manualPivotMove(double manualAngle) {
      return this.runEnd(
          () -> {
            SmartDashboard.putNumber("Coral Pivot Point", manualAngle);
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

    public Command pivotPositionSet(double algaePivotPoint) {
      return this.run(
        () -> {
          coralCurrentPosition = SmartDashboard.getNumber("Coral Pivot Encoder", 0);
          algaeCurrentPosition = SmartDashboard.getNumber("Algae Pivot Encoder", 0);
          if(coralCurrentPosition > -20) {
            SmartDashboard.putNumber("Algae Pivot Point", algaePivotPoint);
          } else if(coralCurrentPosition < -20) {
            if(algaeCurrentPosition < -2.5 && algaePivotPoint < -2.5){
              SmartDashboard.putNumber("Algae Pivot Point", algaePivotPoint);
            }
          } else if(algaePivotPoint < -4 && algaeCurrentPosition < -4) {
            SmartDashboard.putNumber("Algae Pivot Point", algaePivotPoint);
          }
        }
      );
    }
  
    public void voidPivotMove(double manualAngle) {
      SmartDashboard.putNumber("Coral Pivot Point", manualAngle);
      if (pivotEncoderValue > (manualAngle + 2)) {
        setSpeed(-.2);
      } else if (pivotEncoderValue < (manualAngle - 2)) {
        setSpeed(.2);
      } else {
        setSpeed((manualAngle - pivotEncoderValue) * .1);
      }
    }
  
    public void setSpeed(double speed) {
      pivotWheel.set(speed);
    }
  
    public void stop() {
      pivotWheel.set(0);
    }

  @Override public void periodic() {
    pivotEncoderValue = pivotEncoder.getPosition();
    SmartDashboard.putNumber("Algae Pivot Encoder", pivotEncoderValue);
  }
}