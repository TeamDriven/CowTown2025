package frc.robot.subsystems.intake;

import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import static frc.robot.subsystems.intake.IntakeConstants.centerMotorAccel;
import static frc.robot.subsystems.intake.IntakeConstants.intakeMotorAccel;
import frc.robot.util.TalonFXUtil.MotorFactory;

public class IntakeIOKraken implements IntakeIO {
    private MotorFactory motorFactory;
    private final TalonFX intakeMotor;
    private final TalonFX centerMotor;
    private final DigitalInput gamePieceSensor;

    private final VelocityVoltage velocityIntakeControl;
    private final VelocityVoltage velocityCenterControl;
    private final VoltageOut voltageControl;
    private final NeutralOut stopMode;

    public IntakeIOKraken(int intakeID, int centerID, int sensorChannel) {
        motorFactory = new MotorFactory("Intake", intakeID, centerID);

        motorFactory.setBrakeMode(true);
        motorFactory.setInverted(false);
        motorFactory.setCurrentLimits(40);
        motorFactory.setVoltageLimits(8);
        motorFactory.setSlot0(0.15, 0, 0.0025);
        motorFactory.setSlot0(0.5);
        motorFactory.setSlot1(0.15, 0, 0.0025);
        motorFactory.setSlot1(0.5);

        motorFactory.configureMotors();
        var motors = motorFactory.getMotors();
        intakeMotor = motors[0];
        centerMotor = motors[1];

        velocityIntakeControl = new VelocityVoltage(0).withEnableFOC(true)
            .withAcceleration(intakeMotorAccel).withSlot(0);
        velocityCenterControl = new VelocityVoltage(0).withEnableFOC(true)
            .withAcceleration(centerMotorAccel).withSlot(1);
        voltageControl = new VoltageOut(0).withEnableFOC(true);
        stopMode = new NeutralOut();

        gamePieceSensor = new DigitalInput(sensorChannel);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.intakeMotorCurrent = intakeMotor.getSupplyCurrent().getValueAsDouble();
        inputs.intakeMotorVel = intakeMotor.getVelocity().getValueAsDouble();
        inputs.intakeMotorVoltage = intakeMotor.getMotorVoltage().getValueAsDouble();
        inputs.intakeMotorAccel = intakeMotor.getAcceleration().getValueAsDouble();

        inputs.centerMotorCurrent = centerMotor.getSupplyCurrent().getValueAsDouble();
        inputs.centerMotorVel = centerMotor.getVelocity().getValueAsDouble();
        inputs.centerMotorVoltage = centerMotor.getMotorVoltage().getValueAsDouble();
        inputs.centerMotorAccel = centerMotor.getAcceleration().getValueAsDouble();

        inputs.gamePieceSensor = !gamePieceSensor.get();
    }

    @Override
    public void runIntakeMotor(double velocity) {
        intakeMotor.setControl(velocityIntakeControl.withVelocity(velocity));
    }

    @Override
    public void runIntakeVoltage(double volts) {
        intakeMotor.setControl(voltageControl.withOutput(volts));
    }

    @Override
    public void stopIntakeMotor() {
        intakeMotor.setControl(stopMode);
    }

    @Override
    public void runCenterMotor(double velocity) {
        centerMotor.setControl(velocityCenterControl.withVelocity(velocity));
    }

    @Override
    public void runCenterVoltage(double volts) {
        centerMotor.setControl(voltageControl.withOutput(volts));
    }

    @Override
    public void stopCenterMotor() {
        centerMotor.setControl(stopMode);
    }
}
