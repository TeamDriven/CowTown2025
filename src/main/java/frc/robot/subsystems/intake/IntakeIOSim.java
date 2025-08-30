package frc.robot.subsystems.intake;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants;
import static frc.robot.subsystems.pivot.PivotConstants.centerGearRatio;
import static frc.robot.subsystems.pivot.PivotConstants.intakeGearRatio;

public class IntakeIOSim implements IntakeIO {
    private static final DCMotor intakeMotorModel = DCMotor.getKrakenX60Foc(1);
    private static final DCMotor centerMotorModel = DCMotor.getKrakenX60Foc(1);

    private final DCMotorSim intakeMotorSim = new DCMotorSim(
            LinearSystemId.createDCMotorSystem(intakeMotorModel, 0.025, intakeGearRatio),
            intakeMotorModel);

    private final DCMotorSim centerMotorSim = new DCMotorSim(
            LinearSystemId.createDCMotorSystem(centerMotorModel, 0.025, intakeGearRatio),
            centerMotorModel);

    private PIDController pidIntake = new PIDController(6.5, 0, 0.02);
    private PIDController pidCenter = new PIDController(6.5, 0, 0.02);

    private double intakeAppliedVolts = 0.0;
    private double intakeFFVolts = 0.0;

    private double centerAppliedVolts = 0.0;
    private double centerFFVolts = 0.0;

    public IntakeIOSim() {
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {

        intakeAppliedVolts = intakeFFVolts + pidIntake.calculate(intakeMotorSim.getAngularVelocityRadPerSec());

        intakeMotorSim.update(Constants.loopPeriodSecs);

        inputs.intakeMotorVoltage = intakeAppliedVolts;
        inputs.intakeMotorCurrent = Math.abs(intakeMotorSim.getCurrentDrawAmps());
        inputs.intakeMotorVel = intakeMotorSim.getAngularVelocityRPM() / intakeGearRatio;
        inputs.intakeMotorAccel = intakeMotorSim.getAngularAccelerationRadPerSecSq() / intakeGearRatio;

        intakeMotorSim.setInputVoltage(MathUtil.clamp(intakeAppliedVolts, -12.0, 12.0));

        intakeMotorSim.update(Constants.loopPeriodSecs);

        centerAppliedVolts = centerFFVolts + pidIntake.calculate(centerMotorSim.getAngularVelocityRadPerSec());

        centerMotorSim.update(Constants.loopPeriodSecs);

        inputs.centerMotorVoltage = centerAppliedVolts;
        inputs.centerMotorCurrent = Math.abs(centerMotorSim.getCurrentDrawAmps());
        inputs.centerMotorVel = centerMotorSim.getAngularVelocityRPM() / centerGearRatio;
        inputs.centerMotorAccel = centerMotorSim.getAngularAccelerationRadPerSecSq() / centerGearRatio;

        centerMotorSim.setInputVoltage(MathUtil.clamp(centerAppliedVolts, -12.0, 12.0));

        centerMotorSim.update(Constants.loopPeriodSecs);
    }

    @Override
    public void runIntakeMotor(double speed) {
        pidIntake.setSetpoint(speed);
    }

    @Override
    public void runIntakeVoltage(double volts) {
        intakeAppliedVolts = MathUtil.clamp(volts, -12, 12);
        intakeMotorSim.setInputVoltage(intakeAppliedVolts);
    }

    @Override
    public void stopIntakeMotor() {
        runIntakeVoltage(0.0);
    }

    @Override
    public void runCenterMotor(double speed) {
        pidCenter.setSetpoint(speed);
    }

    @Override
    public void runCenterVoltage(double volts) {
        centerAppliedVolts = MathUtil.clamp(volts, -12, 12);
        centerMotorSim.setInputVoltage(intakeAppliedVolts);
    }

    @Override
    public void stopCenterMotor() {
        runCenterVoltage(0.0);
    }
}