package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import static frc.robot.Constants.driver;

public class Controls {
    private static final boolean rightStickDrive = true;

    // Drivetrain
    public static DoubleSupplier driveX = () -> rightStickDrive ? -driver.getRightY() : -driver.getLeftY();
    public static DoubleSupplier driveY = () -> rightStickDrive ? -driver.getRightX() : -driver.getLeftX();
    public static DoubleSupplier driveOmega = () -> rightStickDrive ? -driver.getLeftX() : -driver.getRightX();
    public static Trigger resetPose = driver.start();

    public static Trigger noLimelightMode = rightStickDrive ? driver.x() : driver.pov(270);
    public static Trigger manualMode = rightStickDrive ? driver.b() : driver.pov(90);

    // Actions
    class StandardMode {
        public static Trigger cancelAction = (rightStickDrive ? driver.leftStick() : driver.rightStick())
                .and(RobotState.getInstance()::isStandardMode);
    }

    class NoLimelightMode {
        
    }

    class ManualMode {
        
    }
}
