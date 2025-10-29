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
import java.util.function.DoubleSupplier;
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

    public Command pivotPositionSet(DoubleSupplier coralPivotPoint) {
      return this.runEnd(
        () -> {
          //System.out.println("Coral pivotPositionSet Test");
          if(algaeCurrentPosition < -12) {
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint.getAsDouble());
          } else if(algaeCurrentPosition > -6){
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint.getAsDouble());
          } else if(algaeCurrentPosition < -9){
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint.getAsDouble());
          } else if(coralPivotPoint.getAsDouble() > -600 && coralCurrentPosition > -600) {
            SmartDashboard.putNumber("Coral Pivot Point", coralPivotPoint.getAsDouble());
          }
          voidPivotMove(() -> SmartDashboard.getNumber("Coral Pivot Point",-100));
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

    public Command manualResetEncoder(){
      return this.runEnd(
        () ->{setSpeed(.2);},
        () ->{resetEncoder();}
      );
    }
  
    public void voidPivotMove(DoubleSupplier manualAngle) {
      SmartDashboard.putNumber("Coral Pivot Point", manualAngle.getAsDouble());
      //  System.out.println("Coral voidPivotMove Test");
      double targetSpeed = (manualAngle.getAsDouble() - pivotEncoderValue) * .005;

      if (targetSpeed > 0.19) {
        targetSpeed = 0.19;
      }
      if (targetSpeed < -0.16) {
        targetSpeed = -0.16;
      }

      // if (pivotEncoderValue > (manualAngle.getAsDouble() + 15)) {
      //   setSpeed(-.15);
      // } else if (pivotEncoderValue < (manualAngle.getAsDouble() - 15)) {
      //   setSpeed(.15);
      // } else {
      //   setSpeed((manualAngle.getAsDouble() - pivotEncoderValue) * .005);
      // }

      setSpeed(targetSpeed);
    }

    public Command testingSpeed(double speed) {
      return this.runEnd(
        () -> {
          setSpeed(speed);
          SmartDashboard.putNumber("Coral Pivot Point",pivotEncoderValue);
        }, () -> {
          stop();
        }
      );
    }
  
    public void setSpeed(double speed) {
      pivotWheel.set(speed);
    }

    public void resetEncoder(){
      pivotEncoder.setPosition(0.0);
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