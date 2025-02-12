package frc.robot.subsystems;

//import com.ctre.phoenix.sensors.CANCoder;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CANSparkBase.ControlType;
//import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
//import com.revrobotics.SparkPIDController;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;


import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.lib.config.SwerveModuleConstants;
import frc.lib.math.OnboardModuleState;
//import frc.lib.util.CANCoderUtil;
//import frc.lib.util.CANCoderUtil.CCUsage;

import frc.robot.Constants;
import frc.robot.Robot;

public class SwerveModule {
  public int moduleNumber;
  private Rotation2d lastAngle;
  private Rotation2d angleOffset;

  private SparkMax angleMotor;
  private SparkMax driveMotor;

  private RelativeEncoder driveEncoder;
  private RelativeEncoder integratedAngleEncoder;
  //private CANCoder angleEncoder;
 private DutyCycleEncoder angleEncoder;
  private final SparkClosedLoopController driveController;
  private final SparkClosedLoopController angleController;

  private SparkMaxConfig configDrive;
  private SparkMaxConfig configAngle;

  private final SimpleMotorFeedforward feedforward =
      new SimpleMotorFeedforward(
          Constants.Swerve.driveKS, Constants.Swerve.driveKV, Constants.Swerve.driveKA);

  public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
    this.moduleNumber = moduleNumber;
    angleOffset = moduleConstants.angleOffset;

    /* Angle Encoder Config */
    //angleEncoder = new CANCoder(moduleConstants.cancoderID);
    angleEncoder = new DutyCycleEncoder(moduleConstants.cancoderID);
    configAngleEncoder();

    /* Angle Motor Config */
    angleMotor = new SparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);
    integratedAngleEncoder = angleMotor.getEncoder();
    angleController = angleMotor.getClosedLoopController();
    configAngle = new SparkMaxConfig();
    configAngleMotor();

    /* Drive Motor Config */
    driveMotor = new SparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);
    driveEncoder = driveMotor.getEncoder();
    driveController = driveMotor.getClosedLoopController();
    configDrive = new SparkMaxConfig();
    configDriveMotor();

    lastAngle = getState().angle;
  }

  public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
    // Custom optimize command, since default WPILib optimize assumes continuous controller which
    // REV and CTRE are not
    desiredState = OnboardModuleState.optimize(desiredState, getState().angle);

    setAngle(desiredState);
    setSpeed(desiredState, isOpenLoop);
  }

  private void resetToAbsolute() {
    double absolutePosition = getCanCoder().getDegrees() - angleOffset.getDegrees();
    integratedAngleEncoder.setPosition(absolutePosition);
  }

  private void configAngleEncoder() {
    //angleEncoder.configFactoryDefault();
    //CANCoderUtil.setCANCoderBusUsage(angleEncoder, CCUsage.kMinimal);
    //angleEncoder.configAllSettings(Robot.ctreConfigs.swerveCanCoderConfig);
    //angleEncoder.setDistancePerRotation(360.0);
  }

  private void configAngleMotor() {
    //angleMotor.restoreFactoryDefaults();
    //CANSparkMaxUtil.setCANSparkMaxBusUsage(angleMotor, Usage.kPositionOnly);
    configAngle.smartCurrentLimit(Constants.Swerve.angleContinuousCurrentLimit);
    configAngle.inverted(true);
    //configAngle.idleMode(Constants.Swerve.angleNeutralMode);
    configAngle.idleMode(IdleMode.kCoast);
    configAngle.encoder.positionConversionFactor(Constants.Swerve.angleConversionFactor);
    configAngle.closedLoop.pid(Constants.Swerve.angleKP,Constants.Swerve.angleKI,Constants.Swerve.angleKD);
   // angleController.setI(Constants.Swerve.angleKI);
   // angleController.setD(Constants.Swerve.angleKD);
   configAngle.closedLoop.velocityFF(Constants.Swerve.angleKFF);
   // angleMotor.enableVoltageCompensation(Constants.Swerve.voltageComp);
   // angleMotor.burnFlash();
    resetToAbsolute();
    angleMotor.configure(configAngle, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  private void configDriveMotor() {
   // driveMotor.restoreFactoryDefaults();
   // CANSparkMaxUtil.setCANSparkMaxBusUsage(driveMotor, Usage.kAll);
   // driveMotor.setSmartCurrentLimit(Constants.Swerve.driveContinuousCurrentLimit);
   // driveMotor.setInverted(Constants.Swerve.driveInvert);
   // driveMotor.setIdleMode(Constants.Swerve.driveNeutralMode);
   // driveEncoder.setVelocityConversionFactor(Constants.Swerve.driveConversionVelocityFactor);
   // driveEncoder.setPositionConversionFactor(Constants.Swerve.driveConversionPositionFactor);
   // driveController.setP(Constants.Swerve.angleKP);
   // driveController.setI(Constants.Swerve.angleKI);
   // driveController.setD(Constants.Swerve.angleKD);
   // driveController.setFF(Constants.Swerve.angleKFF);
   // driveMotor.enableVoltageCompensation(Constants.Swerve.voltageComp);
   // driveMotor.burnFlash();
   // driveEncoder.setPosition(0.0);

   configDrive.smartCurrentLimit(Constants.Swerve.angleContinuousCurrentLimit);
   configDrive.inverted(true);
   configDrive.idleMode(IdleMode.kCoast);
   configDrive.encoder.velocityConversionFactor(Constants.Swerve.driveConversionVelocityFactor);
   configDrive.encoder.positionConversionFactor(Constants.Swerve.driveConversionPositionFactor);
   configDrive.closedLoop.pid(Constants.Swerve.angleKP,Constants.Swerve.angleKI,Constants.Swerve.angleKD);
   configDrive.closedLoop.velocityFF(Constants.Swerve.angleKFF);
   resetToAbsolute();
   driveMotor.configure(configDrive, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }

  private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
    if (isOpenLoop) {
      double percentOutput = desiredState.speedMetersPerSecond / Constants.Swerve.maxSpeed;
      driveMotor.set(percentOutput);
    } else {
      driveController.setReference(
          desiredState.speedMetersPerSecond, ControlType.kVelocity,
          ClosedLoopSlot.kSlot0,
          feedforward.calculate(desiredState.speedMetersPerSecond));
    }
  }

  private void setAngle(SwerveModuleState desiredState) {
    // Prevent rotating module if speed is less then 1%. Prevents jittering.
    Rotation2d angle =
        (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01))
            ? lastAngle
            : desiredState.angle;

    angleController.setReference(angle.getDegrees(), ControlType.kPosition);
    lastAngle = angle;
  }

  private Rotation2d getAngle() {
    return Rotation2d.fromDegrees(integratedAngleEncoder.getPosition());
  }

  public Rotation2d getCanCoder() {
    
    return Rotation2d.fromDegrees(angleEncoder.get() * 360);
    //return Rotation2d.fromDegrees(angleEncoder.getDistance());
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(driveEncoder.getVelocity(), getAngle());
  }
}