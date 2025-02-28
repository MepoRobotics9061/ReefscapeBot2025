package frc.robot.subsystems;

import com.ctre.phoenix.led.CANdle;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.ejml.equation.Variable;

public class RobotLights extends SubsystemBase {

    private CANdle m_led;

    private AddressableLEDBufferView m_left;

    private AddressableLEDBufferView m_right;

    private LEDPattern red;

    private LEDPattern black;

public RobotLights() {
    final int CANdleDeviceID = 15;
    CANdle m_led = new CANdle(CANdleDeviceID);
    AddressableLEDBuffer m_buffer = new AddressableLEDBuffer(120);
    m_left = m_buffer.createView(0, 3);
    m_right = m_buffer.createView(4, 7).reversed();
    red = LEDPattern.solid(Color.kRed);
    black = LEDPattern.solid(Color.kBlack);
}

    public Command lightArea(String color, String bufferView){
        return this.run(
            () -> {
                if(color == "red"){

                    if(bufferView == "left"){
                        red.applyTo(m_left);
                    } else {
                        red.applyTo(m_right);
                    }

                } else {

                    if(bufferView == "left"){
                        black.applyTo(m_left);
                    } else {
                        black.applyTo(m_right);
                    }

                }

            }
        );
    }

}