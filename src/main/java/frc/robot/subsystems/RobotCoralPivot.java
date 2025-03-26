package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.security.cert.X509CRL;
public class RobotCoralPivot extends SubsystemBase {

  SparkMax pivotWheel;

   

  private RelativeEncoder pivotEncoder;

  private double pivotEncoderValue;

  private double algaeCurrentPosition;

  private double coralCurrentPosition;

  private double targetPosition;

  public RobotCoralPivot() {
    final int pivotWheelDeviceID = 10;
    pivotWheel = new SparkMax(pivotWheelDeviceID, MotorType.kBrushless);
    pivotEncoder = pivotWheel.getEncoder();
    }

    public Command pivotPositionSet(double coralPivotPoint) {
      return this.runEnd(
        () -> {
          // System.out.println("Coral pivotPositionSet Test");
          if(algaeCurrentPosition < -12) {
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint);
          } else if(algaeCurrentPosition > -6){
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint);
          } else if(algaeCurrentPosition < -9){
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint);
          } else if(coralPivotPoint > -600 && coralCurrentPosition > -600) {
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint);
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
            // System.out.println("Coral manualPivotMove Test");
            SmartDashboard.putNumber("Coral Pivot Point", manualAngle);
            if (pivotEncoderValue > (manualAngle + 15)) {
              setSpeed(-.2);
            } else if (pivotEncoderValue < (manualAngle - 15)) {
              setSpeed(.2);
            } else {
              setSpeed((manualAngle - pivotEncoderValue) * .005);
            }
          },
          () -> {
            stop();
          }
        );
    }
  
    public void voidPivotMove(double manualAngle) {
      SmartDashboard.putNumber("Coral Pivot Point", manualAngle);
       System.out.println("Coral voidPivotMove Test");
      if (pivotEncoderValue > (manualAngle + 15)) {
        setSpeed(-.2);
      } else if (pivotEncoderValue < (manualAngle - 15)) {
        setSpeed(.2);
      } else {
        setSpeed((manualAngle - pivotEncoderValue) * .005);
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
      // System.out.println("Coral stop Test");
    }

    public boolean inPosition() {
      return Math.abs(targetPosition - coralCurrentPosition) < 5;
    }

  @Override public void periodic() {
    pivotEncoderValue = pivotEncoder.getPosition();
    SmartDashboard.putNumber("Coral Pivot Encoder", pivotEncoderValue);
    algaeCurrentPosition = SmartDashboard.getNumber("Algae Pivot Encoder", 0);
    coralCurrentPosition = SmartDashboard.getNumber("Coral Pivot Encoder", 0);
    targetPosition = SmartDashboard.getNumber("Coral Pivot Point", 0);
  }
}