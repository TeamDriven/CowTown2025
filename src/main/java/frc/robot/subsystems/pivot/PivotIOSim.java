package frc.robot.subsystems.pivot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import frc.robot.Constants;
import static frc.robot.subsystems.pivot.PivotConstants.intakeGearRatio;

public class PivotIOSim implements PivotIO {
    private static final DCMotor motorModel = DCMotor.getKrakenX60Foc(1);

    private final DCMotorSim motorSim = new DCMotorSim(
            LinearSystemId.createDCMotorSystem(motorModel, 0.025, intakeGearRatio),
            motorModel);

    private PIDController pid = new PIDController(6.5, 0, 0.02);

    private double appliedVolts = 0.0;
    private double FFVolts = 0.0;

    public PivotIOSim() {
    }

    @Override
    public void updateInputs(PivotIOInputs inputs) {

        appliedVolts = FFVolts + pid.calculate(motorSim.getAngularVelocityRadPerSec());

        motorSim.update(Constants.loopPeriodSecs);

        inputs.motorPos = motorSim.getAngularPositionRotations() * intakeGearRatio;
        inputs.motorVoltage = appliedVolts;
        inputs.motorCurrent = Math.abs(motorSim.getCurrentDrawAmps());
        inputs.motorVel = motorSim.getAngularVelocityRPM() / intakeGearRatio;
        inputs.motorAccel = motorSim.getAngularAccelerationRadPerSecSq() / intakeGearRatio;

        motorSim.setInputVoltage(MathUtil.clamp(appliedVolts, -12.0, 12.0));

        motorSim.update(Constants.loopPeriodSecs);
    }

    @Override
    public void runMotorPos(double pos) {
        pid.setSetpoint(pos);
    }

    @Override
    public void runMotor(double speed) {
        pid.setSetpoint(speed);
    }

    @Override
    public void runVoltage(double volts) {
        appliedVolts = MathUtil.clamp(volts, -12, 12);
        motorSim.setInputVoltage(appliedVolts);
    }

    @Override
    public void stopMotor() {
        runVoltage(0.0);
    }
}