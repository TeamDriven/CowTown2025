package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
    @AutoLog
    public class IntakeIOInputs {
        public double intakeMotorVoltage = 0;
        public double intakeMotorCurrent = 0;
        public double intakeMotorVel = 0;
        public double intakeMotorAccel = 0;
        
        public double centerMotorVoltage = 0;
        public double centerMotorCurrent = 0;
        public double centerMotorVel = 0;
        public double centerMotorAccel = 0;
        
        public double pivotMotorVoltage = 0;
        public double pivotMotorCurrent = 0;
        public double pivotMotorVel = 0;
        public double pivotMotorAccel = 0;

        public boolean gamePieceSensor = false;
    }

    default void updateInputs(IntakeIOInputs inputs) {}

    default void runIntakeMotor(double velocity) {}

    default void runCenterMotor(double velocity) {}
    
    default void runPivotMotor(double velocity) {}

    default void runIntakeVoltage(double volts) {}

    default void runCenterVoltage(double volts) {}
    
    default void runPivotVoltage(double volts) {}

    default void stopIntakeMotor() {}

    default void stopCenterMotor() {}
    
    default void stopPivotMotor() {}
}
