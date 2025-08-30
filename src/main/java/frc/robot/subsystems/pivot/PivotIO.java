package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface  PivotIO {
    @AutoLog
    public class PivotIOInputs {
        public double motorVoltage = 0;
        public double motorCurrent = 0;
        public double motorVel = 0;
        public double motorAccel = 0;
        public double motorPos = 0;
    }

    default void updateInputs(PivotIOInputs inputs) {}

    default void runMotor(double velocity) {}
    
    default void runVoltage(double volts) {}

    default void runMotorPos(double pos) {}
    
    default void stopMotor() {}
}
