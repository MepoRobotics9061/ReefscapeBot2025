package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdleConfiguration;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RobotLights extends SubsystemBase {

CANdle m_led;

public RobotLights() {
    final int CANdleDeviceID = 17;
    m_led = new CANdle(CANdleDeviceID);
    CANdleConfiguration config = new CANdleConfiguration();
    config.stripType = LEDStripType.RGB; // set the strip type to RGB
    config.brightnessScalar = 0.5; // dim the LEDs to half brightness
    m_led.configAllSettings(config);
}

    public Command lightArea(String side, String color){
        return this.run(
            () -> {
                m_led.setLEDs(0, 0, 0);
                if(side == "left"){

                    if(color == "white"){
                        m_led.setLEDs(255, 255, 255, 100, 9, 150);
                        m_led.setLEDs(0, 0, 0, 0, 159, 150);

                    } else {
                        m_led.setLEDs(0, 150, 150, 10, 9, 150);
                        m_led.setLEDs(0, 0, 0, 0, 159, 150);
                    }

                } else {

                    if(color == "white"){
                        m_led.setLEDs(0, 0, 0, 0, 9, 150);
                        m_led.setLEDs(255, 255, 255, 100, 159, 150);

                    } else {
                        m_led.setLEDs(0, 0, 0, 0, 9, 150);
                        m_led.setLEDs(0, 150, 150, 100, 159, 150);
                    }

                }

            }
        );
    }

}