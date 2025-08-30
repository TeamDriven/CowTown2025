package frc.robot.subsystems.pivot;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

import static frc.robot.subsystems.pivot.PivotConstants.motorAccel;
import frc.robot.util.TalonFXUtil.MotorFactory;

public class PivotIOKraken implements PivotIO {
    private MotorFactory motorFactory;

    private final TalonFX pivotMotor;
    
    private final VelocityVoltage velocityPivotControl;
    private final VoltageOut voltageControl;
    private final MotionMagicVoltage motionMagicControl;
    private final NeutralOut stopMode;

    public PivotIOKraken(int pivotID) {
        motorFactory = new MotorFactory("Pivot", pivotID);

        motorFactory.setBrakeMode(true);
        motorFactory.setInverted(false);
        motorFactory.setCurrentLimits(40);
        motorFactory.setVoltageLimits(8);

        motorFactory.setSlot0(0.15, 0, 0.0025);
        motorFactory.setSlot0(0.5);

        motorFactory.setSlot1(13, 0.5, 0.065);
        motorFactory.setSlot1(1.8, 0.4, GravityTypeValue.Elevator_Static);
        motorFactory.setMotionMagic(10, 90, 100);

        motorFactory.configureMotors();
        pivotMotor = motorFactory.getMotors()[0];
        
        velocityPivotControl = new VelocityVoltage(0).withEnableFOC(true)
            .withAcceleration(motorAccel).withSlot(0);
        voltageControl = new VoltageOut(0).withEnableFOC(true);
        motionMagicControl = new MotionMagicVoltage(0).withEnableFOC(true).withSlot(1);
        stopMode = new NeutralOut();
    }

    @Override
    public void updateInputs(PivotIOInputs inputs) {
        inputs.motorCurrent = pivotMotor.getSupplyCurrent().getValueAsDouble();
        inputs.motorVel = pivotMotor.getVelocity().getValueAsDouble();
        inputs.motorVoltage = pivotMotor.getMotorVoltage().getValueAsDouble();
        inputs.motorAccel = pivotMotor.getAcceleration().getValueAsDouble();
        inputs.motorPos = pivotMotor.getPosition().getValueAsDouble();
    }

    @Override
    public void runMotor(double velocity) {
        pivotMotor.setControl(velocityPivotControl.withVelocity(velocity));
    }

    @Override
    public void runVoltage(double volts) {
        pivotMotor.setControl(voltageControl.withOutput(volts));
    }

    @Override    
    public void runMotorPos(double pos) {
        pivotMotor.setControl(motionMagicControl.withPosition(pos));
    }

    @Override
    public void stopMotor() {
        pivotMotor.setControl(stopMode);
    }
}