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

  private double targetPosition;

  public RobotAlgaePivot() {
    final int pivotWheelDeviceID = 13;
    pivotWheel = new SparkMax(pivotWheelDeviceID, MotorType.kBrushless);
    configWheel = new SparkMaxConfig();
    configWheel.smartCurrentLimit(20);
    pivotWheel.configure(configWheel, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    pivotEncoder = pivotWheel.getEncoder();
    }

    public Command pivotPositionSet(double algaePivotPoint) {
      return this.runEnd(
        () -> {
          // System.out.println("Algae pivotPositionSet Test");
          if(coralCurrentPosition > -600) {
            SmartDashboard.putNumber("Algae Pivot Point", algaePivotPoint);
          } else if((algaeCurrentPosition < -9) && (algaePivotPoint < -9)) {
            SmartDashboard.putNumber("Algae Pivot Point", algaePivotPoint);
          }
        },
        () -> {
          stop();
        }
      );
    }

    public Command manualPivotMove(double manualAngle) {
      return this.runEnd(
          () -> {
            // System.out.println("Algae manualPivotMove Test");
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
      // System.out.println("Algae voidPivotMove Test");
      SmartDashboard.putNumber("Algae Pivot Point", manualAngle);
      if (pivotEncoderValue > (manualAngle + 1)) {
        setSpeed(-.2);
      } else if (pivotEncoderValue < (manualAngle - 1)) {
        setSpeed(.2);
      } else {
        setSpeed((manualAngle - pivotEncoderValue) * .2);
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
      // System.out.println("Algae stop Test");
      pivotWheel.set(0);
    }

    public boolean inPosition() {
      return Math.abs(targetPosition - algaeCurrentPosition) < 1;
    }

  @Override public void periodic() {
    pivotEncoderValue = pivotEncoder.getPosition();
    SmartDashboard.putNumber("Algae Pivot Encoder", pivotEncoderValue);
    algaeCurrentPosition = SmartDashboard.getNumber("Algae Pivot Encoder", 0);
    coralCurrentPosition = SmartDashboard.getNumber("Coral Pivot Encoder", 0);
    targetPosition = SmartDashboard.getNumber("Algae Pivot Point", 0);
  }
}