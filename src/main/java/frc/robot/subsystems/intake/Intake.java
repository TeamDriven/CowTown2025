package frc.robot.subsystems.intake;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotState;

public class Intake extends SubsystemBase {
    private IntakeIO intakeIO;
    private IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

    
    private enum mode {
        VELOCITY,
        VOLTAGE;
    }

    private mode currentIntakeMode = mode.VELOCITY;
    private mode currentCenterMode = mode.VELOCITY;

    private double intakeValue = 0;
    private double centerValue = 0;

    public Intake(IntakeIO intakeIO) {
        this.intakeIO = intakeIO;
    }

    @Override
    public void periodic() {
        intakeIO.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);

        // Should report to RobotState when piece status changes
        RobotState.getInstance().setGamePiece(inputs.gamePieceSensor);

        
        Logger.recordOutput("Intake/intakeMode", currentIntakeMode);
        Logger.recordOutput("Intake/centerMode", currentCenterMode);
        Logger.recordOutput("Intake/value", intakeValue);

        if (intakeValue == 0) {
            intakeIO.stopIntakeMotor();
        } else if (currentIntakeMode == mode.VELOCITY) {
            if (RobotState.getInstance().hasCoral()) {
                intakeValue = 0;
            }
            intakeIO.runIntakeMotor(intakeValue);
        } else if (currentIntakeMode == mode.VOLTAGE) {
            intakeIO.runIntakeVoltage(intakeValue);
        } else {
            throw new IllegalStateException();
        }

        if (centerValue == 0) {
            intakeIO.stopCenterMotor();
        } else if (currentCenterMode == mode.VELOCITY) {
            intakeIO.runCenterMotor(centerValue);
        } else if (currentIntakeMode == mode.VOLTAGE) {
            intakeIO.runCenterVoltage(centerValue);
        } else {
            throw new IllegalStateException();
        }
        
    }

    public void runIntakeVelocity(double velocity) {
        currentIntakeMode = mode.VELOCITY;
        this.intakeValue = velocity;
    }

    public void runIntakeVoltage(double volts) {
        currentIntakeMode = mode.VOLTAGE;
        this.intakeValue = volts;
    }

    public Command runIntakeVelocityCommand(double vel) {
        return Commands.startEnd(() -> runIntakeVelocity(vel), () -> runIntakeVelocity(0), this);
    }

    public Command runIntakeVelocityCommand(DoubleSupplier vel) {
        return Commands.startEnd(() -> runIntakeVelocity(vel.getAsDouble()), () -> runIntakeVelocity(0), this);
    }

    public void runCenterVelocity(double velocity) {
        currentCenterMode = mode.VELOCITY;
        this.centerValue = velocity;
    }

    public void runCenterVoltage(double volts) {
        currentCenterMode = mode.VOLTAGE;
        this.centerValue = volts;
    }

    public Command runCenterVelocityCommand(double vel) {
        return Commands.startEnd(() -> runCenterVelocity(vel), () -> runCenterVelocity(0), this);
    }

    public Command runCenterVelocityCommand(DoubleSupplier vel) {
        return Commands.startEnd(() -> runCenterVelocity(vel.getAsDouble()), () -> runCenterVelocity(0), this);
    }

}