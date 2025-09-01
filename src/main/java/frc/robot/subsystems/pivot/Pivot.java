package frc.robot.subsystems.pivot;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase{
    private PivotIO pivotIO;
    private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();

    
    private enum mode {
        VELOCITY,
        VOLTAGE,
        MOTIONMAGIC;
    }

    private mode currentMode = mode.VELOCITY;
    
    private double value = 0;

    public Pivot(PivotIO pivotIO) {
        this.pivotIO = pivotIO;
    }

    @Override
    public void periodic() {
        pivotIO.updateInputs(inputs);
        Logger.processInputs("Pivot", inputs);

        Logger.recordOutput("Pivot/mode", currentMode);
        
        if (value == 0) {
            pivotIO.stopMotor();
        } else if (currentMode == mode.VELOCITY) {
            pivotIO.runMotor(value);
        } else if (currentMode == mode.VOLTAGE) {
            pivotIO.runVoltage(value);
        } else if (currentMode == mode.MOTIONMAGIC) {
            pivotIO.runMotorPos(value);
        } else {
            throw new IllegalStateException();
        }
    }
    
    
    public void setPos(double pos) {
        currentMode = mode.MOTIONMAGIC;
        this.value = pos;
    }

    public void runPivotVelocity(double velocity) {
        currentMode = mode.VELOCITY;
        this.value = velocity;
    }

    public void runPivotVoltage(double volts) {
        currentMode = mode.VOLTAGE;
        this.value = volts;
    }

    public Command runPivotVelocityCommand(double vel) {
        return Commands.startEnd(() -> runPivotVelocity(vel), () -> runPivotVelocity(0), this);
    }

    public Command runPivotVelocityCommand(DoubleSupplier vel) {
        return Commands.startEnd(() -> runPivotVelocity(vel.getAsDouble()), () -> runPivotVelocity(0), this);
    }
}
