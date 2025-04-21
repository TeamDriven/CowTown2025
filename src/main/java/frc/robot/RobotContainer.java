// Copyright (c) 2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot;

import static frc.robot.Subsystems.*;

import java.nio.charset.StandardCharsets;
import java.util.function.BooleanSupplier;

import static frc.robot.Constants.*;
import static frc.robot.Controls.*;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Controls.ManualMode;
import frc.robot.RobotState.actions;
import frc.robot.commands.FeedForwardCharacterization;
import frc.robot.commands.WheelRadiusCharacterization;
import frc.robot.subsystems.drive.Drive;
import frc.robot.util.*;
import frc.robot.util.Alert.AlertType;

public class RobotContainer {
    private final RobotState robotState = RobotState.getInstance();
    private final Alert driverDisconnected = new Alert("Driver controller disconnected (port 0).",
            AlertType.WARNING);

    private static final double maxDriveSpeed = 1;
    private static final double maxTurnSpeed = 0.9;

    private static double driveMult = maxDriveSpeed;
    private static double turnMult = maxTurnSpeed;

    private static void setDriveMults(double drive, double turn) {
        driveMult = drive;
        turnMult = turn;
    }

    private static SendableChooser<Command> autoChooser = new SendableChooser<>();

    public static boolean shouldUseZones = true;

    public double time = Double.NaN;

    private DigitalInput driverDistanceSensor = new DigitalInput(3);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        DriverStation.silenceJoystickConnectionWarning(true);

        FieldConstants.logFieldConstants();

        // Configure autos and buttons
        setupAutos();
        universalControls();
        configureStandardMode();
        configureManualMode();

        // Alerts for constants
        if (Constants.tuningMode) {
            new Alert("Tuning mode enabled", AlertType.INFO).set(true);
        }
    }

    private void setupAutos() {
        SmartDashboard.putData(autoChooser);
    }

    private Command driveCommand = drive.run(
            () -> drive.acceptTeleopInput(
                    driveX.getAsDouble() * driveMult, driveY.getAsDouble() * driveMult,
                    driveOmega.getAsDouble() * turnMult,
                    false))
            .withName("Drive Teleop Input");

    private Command setDesiredAction(actions desiredAction) {
        return Commands.runOnce(() -> RobotState.getInstance().setDesiredAction(desiredAction));
    }

    private BooleanSupplier isDesiredAction(actions desiredAction) {
        return () -> RobotState.getInstance().getDesiredAction() == desiredAction;
    }

    private void universalControls() {
        CommandScheduler.getInstance().getActiveButtonLoop().clear();

        drive.clearAutoAlignGoal();
        drive.clearHeadingGoal();

        RobotState.getInstance().setDesiredAction(actions.NONE);

        drive.setDefaultCommand(driveCommand);

        resetPose.onTrue(
                Commands.runOnce(
                        () -> robotState.resetPose(
                                new Pose2d(
                                        robotState.getEstimatedPose()
                                                .getTranslation(),
                                        AllianceFlipUtil.apply(
                                                new Rotation2d()))))
                        .ignoringDisable(true));

        new Trigger(() -> !driverDistanceSensor.get())
                .onTrue(Commands.runOnce(() -> driver.getHID().setRumble(RumbleType.kBothRumble, 0.5)))
                .onFalse(Commands.runOnce(() -> driver.getHID().setRumble(RumbleType.kBothRumble, 0)));

        manualMode.onTrue(Commands.runOnce(() -> RobotState.getInstance().setManualMode()));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link Joystick}
     * or {@link
     * XboxController}), and then passing it to a {@link JoystickButton}.
     */
    private void configureStandardMode() {

    }

    private void configureManualMode() {
      
    }

    /** Updates the alerts for disconnected controllers. */
    public void checkControllers() {
        driverDisconnected.set(
                !DriverStation.isJoystickConnected(driver.getHID().getPort())
                        || !DriverStation.getJoystickIsXbox(driver.getHID().getPort()));
    }

    /** Updates dashboard data. */
    public void updateDashboardOutputs() {
        SmartDashboard.putNumber("Match Time", DriverStation.getMatchTime());
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {

        // Drive Static
        // Characterization
        // return new StaticCharacterization(
        // drive, drive::runCharacterization, drive::getCharacterizationVelocity)
        // .finallyDo(drive::endCharacterization);

        // Drive FF Characterization
        // return new FeedForwardCharacterization(
        // drive, drive::runCharacterization, drive::getCharacterizationVelocity)
        // .finallyDo(drive::endCharacterization);

        // Drive Wheel Radius Characterization
        // return drive
        // .orientModules(Drive.getCircleOrientations())
        // .andThen(
        // new WheelRadiusCharacterization(
        // drive, WheelRadiusCharacterization.Direction.COUNTER_CLOCKWISE))
        // .withName("Drive Wheel Radius Characterization");

        // Slippage Calculator
        // return Commands.runOnce(
        // () ->
        // robotState.resetPose(
        // new Pose2d(
        // // robotState.getEstimatedPose().getTranslation(),
        // new Translation2d(), AllianceFlipUtil.apply(new Rotation2d()))))
        // .andThen(new SlippageCalculator(drive))
        // .withName("Slippage Calculator");

        return autoChooser.getSelected();
    }
}
