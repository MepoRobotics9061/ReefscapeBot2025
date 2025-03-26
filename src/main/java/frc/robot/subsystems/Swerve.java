
package frc.robot.subsystems;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

//import com.ctre.phoenix.sensors.Pigeon2;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
// import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
// import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.RobotCamera;
import java.util.function.Function;

public class Swerve extends SubsystemBase {
  // private final Pigeon2 gyro;
  private final AHRS gyro;

  private SwerveDriveOdometry swerveOdometry;

  private SwerveModule[] mSwerveMods;

  private RobotCamera m_robotCamera;

  private Field2d field;

  private double gyroValue;
  private double tagDistance;

  private double gyroATagSpinAmount;

  public Swerve(RobotCamera robotCamera) {

    gyro = new AHRS(NavXComType.kMXP_SPI);

    gyro.zeroYaw();

    mSwerveMods = new SwerveModule[] {
        new SwerveModule(0, Constants.Swerve.Mod0.constants),
        new SwerveModule(1, Constants.Swerve.Mod1.constants),
        new SwerveModule(2, Constants.Swerve.Mod2.constants),
        new SwerveModule(3, Constants.Swerve.Mod3.constants)

    };

    field = new Field2d();

    SmartDashboard.putData("Field", field);

    m_robotCamera = robotCamera;

    // swerveOdometry = new
    // SwerveDriveOdometry(Constants.Swerve.swerveKinematics,getYaw());
  }

  public void drive(
      Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
    SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(
                translation.getX(), translation.getY(), rotation, getYawField())
            : new ChassisSpeeds(translation.getX(), translation.getY(), rotation));
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

    for (SwerveModule mod : mSwerveMods) {
      mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
    }
  }

  public void centerATagVoid() {
    drive(new Translation2d(0, m_robotCamera.tagX / 20), gyroATagSpinAmount, false, true);
  }

  public void coralPrepVoid() {
    if (m_robotCamera.tagArea != 0) {
    //  drive(new Translation2d(-(10 - m_robotCamera.tagArea) / 20, m_robotCamera.tagX / 40), gyroATagSpinAmount / 2 ,
      drive(new Translation2d(-(15.5 - tagDistance) / 20, m_robotCamera.tagX / 40), gyroATagSpinAmount / 2 ,
      false, true);
    }
  }

  public void algaePrepVoid() {
    if (m_robotCamera.tagArea != 0) {
     // drive(new Translation2d(-(14 - m_robotCamera.tagArea) / 20, m_robotCamera.tagX / 40), gyroATagSpinAmount / 2,
      drive(new Translation2d(-(13.5 - tagDistance) / 20, m_robotCamera.tagX / 40), gyroATagSpinAmount / 2, 
      false, true);
    }
  }

  public void rotateUntilVoid(double desiredAngle) {

    double rotateErrorAmount = gyroValue - desiredAngle;

    if(rotateErrorAmount > 180) {
      rotateErrorAmount -= 360;
    }

    if(rotateErrorAmount < -180) {
      rotateErrorAmount += 360;
    }

    rotateErrorAmount = rotateErrorAmount / 15;

    if ((.05 < rotateErrorAmount) && (rotateErrorAmount < .2)) {
      rotateErrorAmount = .2;
    }

    else if ((-.2 < rotateErrorAmount) && (rotateErrorAmount < -0.05)) {
      rotateErrorAmount = -.2;
    }

    if (rotateErrorAmount > 4) {
      rotateErrorAmount = 4;
    }

    if (rotateErrorAmount < -4) {
      rotateErrorAmount = -4;
    }

    drive(new Translation2d(0, 0), -rotateErrorAmount, false, true);
  }

  /* Used by SwerveControllerCommand in Auto */
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);

    for (SwerveModule mod : mSwerveMods) {
      mod.setDesiredState(desiredStates[mod.moduleNumber], false);
    }
  }

  public Pose2d getPose() {
    // return swerveOdometry.getPoseMeters();
    return new Pose2d();
  }

  public void resetOdometry(Pose2d pose) {
    // swerveOdometry.resetPosition(pose, getYaw());
  }

  public SwerveModuleState[] getStates() {
    SwerveModuleState[] states = new SwerveModuleState[4];
    for (SwerveModule mod : mSwerveMods) {
      states[mod.moduleNumber] = mod.getState();
    }
    return states;
  }

  public Command zeroGyro() {
    return this.run(() -> {
      gyro.zeroYaw();
    });
  }

  public Rotation2d getYaw() {
    return (Constants.Swerve.invertGyro)
        ? Rotation2d.fromDegrees(360 - gyro.getYaw())
        : Rotation2d.fromDegrees(gyro.getYaw());
  }

  public Rotation2d getYawField() {
    return (Constants.Swerve.invertGyro)
        ? Rotation2d.fromDegrees(360 - gyro.getYaw()+180)
        : Rotation2d.fromDegrees(gyro.getYaw()+180);
  }

  // public double rotateUntilAmount(double desiredAngle) {
  //   return 180 - desiredAngle;
  // }

  @Override
  public void periodic() {
    // swerveOdometry.update(getYaw(), getStates());
    field.setRobotPose(getPose());

    for (SwerveModule mod : mSwerveMods) {
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " Integrated", mod.getState().angle.getDegrees());
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);
    }

    //gyroValue = gyro.getYaw() + 180;
    gyroValue = gyro.getYaw() ;


    SmartDashboard.putNumber(
        "Gyro", gyroValue);

    double errorAmount = m_robotCamera.spinAmount - gyroValue;

    tagDistance = 14/Math.tan((23 + m_robotCamera.tagY)/57.29577)/3;

    SmartDashboard.putNumber("Tag Distance", tagDistance);

    if (errorAmount > 180) {
      errorAmount -= 360;
    }

    else if (errorAmount < -180) {
      errorAmount += 360;
    }

    gyroATagSpinAmount = (errorAmount) / 15;

    if ((.05 < gyroATagSpinAmount) && (gyroATagSpinAmount < .2)) {
      gyroATagSpinAmount = .2;
    }

    else if ((-.2 < gyroATagSpinAmount) && (gyroATagSpinAmount < -0.05)) {
      gyroATagSpinAmount = -.2;
    }

    if (gyroATagSpinAmount > 4) {
      gyroATagSpinAmount = 4;
    }

    if (gyroATagSpinAmount < -4) {
      gyroATagSpinAmount = -4;
    }

  }
}