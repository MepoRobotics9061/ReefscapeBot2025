package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotAlgaePivot extends SubsystemBase {

  SparkMax pivotWheel;

  private SparkMaxConfig configWheel;

  private RelativeEncoder pivotEncoder;

  private double pivotEncoderValue;

  private double coralCurrentPosition;

  private double algaeCurrentPosition;

  public RobotAlgaePivot() {
    final int pivotWheelDeviceID = 13;
    pivotWheel = new SparkMax(pivotWheelDeviceID, MotorType.kBrushless);
    configWheel = new SparkMaxConfig();
    configWheel.smartCurrentLimit(10);
    pivotWheel.configure(configWheel, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    pivotEncoder = pivotWheel.getEncoder();
    }

    public Command pivotPositionSet(double algaePivotPoint) {
      return this.run(
        () -> {
          coralCurrentPosition = SmartDashboard.getNumber("Coral Pivot Encoder", 0);
          algaeCurrentPosition = SmartDashboard.getNumber("Algae Pivot Encoder", 0);
          if(coralCurrentPosition > -600) {
            SmartDashboard.putNumber("Algae Pivot Point", algaePivotPoint);
          } else if((algaeCurrentPosition < -15) && (algaePivotPoint < -15)) {
            SmartDashboard.putNumber("Algae Pivot Point", algaePivotPoint);
          }
        }
      );
    }

    public Command manualPivotMove(double manualAngle) {
      return this.runEnd(
          () -> {
            SmartDashboard.putNumber("Algae Pivot Point", manualAngle);
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
      SmartDashboard.putNumber("Algae Pivot Point", manualAngle);
      if (pivotEncoderValue > (manualAngle + 2)) {
        setSpeed(-.2);
      } else if (pivotEncoderValue < (manualAngle - 2)) {
        setSpeed(.2);
      } else {
        setSpeed((manualAngle - pivotEncoderValue) * .5);
      }
    }
    
    public Command testingSpeed(double speed) {
      return this.runEnd(
        () -> {
          setSpeed(speed);
        }, () -> {
          stop();
        }
      );
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