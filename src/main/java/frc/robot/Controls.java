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

    public static Trigger algaeMode = (driver.rightTrigger(0.5));
    public static Trigger coralMode = (driver.leftTrigger(0.5));

    // Actions
    class StandardMode {
        public static Trigger cancelActionLeft = (driver.leftStick())
                .and(RobotState.getInstance()::isStandardMode);
        public static Trigger cancelActionRight = (driver.rightStick())
                .and(RobotState.getInstance()::isStandardMode);

        public static Trigger pickUpCoral = (rightStickDrive ? driver.rightBumper() : driver.leftBumper())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isCoralMode);
        public static Trigger putDownCoral = (rightStickDrive ? driver.leftBumper() : driver.leftBumper())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isCoralMode);

        public static Trigger l4 = (rightStickDrive ? driver.pov(0) : driver.y())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isCoralMode);
        public static Trigger l3 = (rightStickDrive ? driver.pov(90) : driver.b())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isCoralMode);
        public static Trigger l2 = (rightStickDrive ? driver.pov(270) : driver.x())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isCoralMode);
        public static Trigger l1 = (rightStickDrive ? driver.pov(180) : driver.a())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isCoralMode);

        public static Trigger barge = (rightStickDrive ? driver.pov(0) : driver.y())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isAlgaeMode);
        public static Trigger highAlgae = (rightStickDrive ? driver.pov(90) : driver.b())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isAlgaeMode);
        public static Trigger lowAlgae = (rightStickDrive ? driver.pov(270) : driver.x())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isAlgaeMode);
        public static Trigger groundAlgae = (rightStickDrive ? driver.pov(180) : driver.a())
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isAlgaeMode);

        public static Trigger processor = (rightStickDrive ? driver.y() : driver.pov(0))
                .and(RobotState.getInstance()::isStandardMode).and(RobotState.getInstance()::isAlgaeMode);
        
        public static Trigger detectGamePiece = (driver.back()).and(RobotState.getInstance()::isStandardMode);
    }

    class NoLimelightMode {

    }

    class ManualMode {

    }
}
