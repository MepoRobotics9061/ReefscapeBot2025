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

  private double algaePivotPoint;

  private double coralCurrentPosition;

  public RobotCoralPivot() {
    final int pivotWheelDeviceID = 10;
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

    public Command pivotPositionSet(double coralPivotPoint) {
      return this.run(
        () -> {
          algaePivotPoint = SmartDashboard.getNumber("Algae Pivot Point", 0);
          coralCurrentPosition = SmartDashboard.getNumber("Coral Pivot Point", 0);
          if(algaePivotPoint < -4) {
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint);
          } else if(algaePivotPoint > -1 && algaePivotPoint < -2){
            if(coralPivotPoint > -35) {
              SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint);
            }
          } else if(coralPivotPoint > -20 && coralCurrentPosition > -20) {
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint);
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
    SmartDashboard.putNumber("Coral Pivot Encoder", pivotEncoderValue);
  }
}