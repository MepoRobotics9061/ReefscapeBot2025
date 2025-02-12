package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotCamera extends SubsystemBase {

  double tagX;
  double tagY;
  double tagArea;
  double tagID;
  double spinAmount;
  double gyro;
  String teamCol;
  NetworkTable table;
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;
  NetworkTableEntry tid;


  public RobotCamera() {
  }

  // public Command centerATagCommand(){
  //   return driveCommand(0, tagX/20, gyro - spinAmount);
  // }

  // public Command coralPrepCommand(){
  //   return driveCommand((10 - tagArea)/30, tagX/20,  gyro - spinAmount);
  // }

  @Override
  public void periodic() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    tid = table.getEntry("tid");

    tagX = tx.getDouble(0.0);
    tagY = ty.getDouble(0.0);
    tagArea = ta.getDouble(0.0);
    tagID = tid.getDouble(0.0);

    SmartDashboard.putNumber("Tag X", tagX);
    SmartDashboard.putNumber("Tag Y", tagY);
    SmartDashboard.putNumber("Tag Area", tagArea);
    SmartDashboard.putNumber("Tag ID", tagID);

    teamCol = SmartDashboard.getString("Team Color", "r");

    gyro = SmartDashboard.getNumber("Gyro", 0);

    SmartDashboard.putString("Team Color", teamCol);

    if (tagID == 1 || tagID == 9 || tagID == 19) {
      if (teamCol == "r") {
        spinAmount = 225;
      } else {
        spinAmount = 45;
      }
    } else if (tagID == 2 || tagID == 11 || tagID == 17) {
      if (teamCol == "r") {
        spinAmount = 135;
      } else {
        spinAmount = 315;
      }
    } else if (tagID == 3) {
      if (teamCol == "r") {
        spinAmount = 90;
      } else {
        spinAmount = 270;
      }
    } else if (tagID == 4 || tagID == 5 || tagID == 7 || tagID == 21) {
      if (teamCol == "r") {
        spinAmount = 0;
      } else {
        spinAmount = 180;
      }
    } else if (tagID == 6 || tagID == 13 || tagID == 22) {
      if (teamCol == "r") {
        spinAmount = 45;
      } else {
        spinAmount = 225;
      }
    } else if (tagID == 8 || tagID == 12 || tagID == 20) {
      if (teamCol == "r") {
        spinAmount = 315;
      } else {
        spinAmount = 135;
      }
    } else if (tagID == 10 || tagID == 14 || tagID == 15 || tagID == 18) {
      if (teamCol == "r") {
        spinAmount = 180;
      } else {
        spinAmount = 0;
      }
    } else if (tagID == 16) {
        if (teamCol == "r") {
          spinAmount = 270;
        } else {
          spinAmount = 90;
        }
      //Used only for our April Tag (#42)
    } else if (tagID == 42) {
      spinAmount = 90;
    }

    SmartDashboard.putNumber("Spin Amount", spinAmount);
  }
}