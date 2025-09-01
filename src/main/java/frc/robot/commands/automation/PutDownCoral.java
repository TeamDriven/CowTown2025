package frc.robot.commands.automation;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import static frc.robot.Subsystems.intake;
import static frc.robot.Subsystems.pivot;
import frc.robot.subsystems.pivot.PivotConstants;

public class PutDownCoral extends SequentialCommandGroup{
    public PutDownCoral() {
        addCommands(
            pivot.runOnce(() -> pivot.setPos(PivotConstants.L1Pos)),
            intake.runOnce(() -> intake.runIntakeVelocity(-10)),
            Commands.waitUntil(intake.hasNoCoral()),
            pivot.runOnce(() -> pivot.setPos(PivotConstants.tuckPos)),
            intake.runOnce(() -> intake.runIntakeVelocity(0))
        );
    }
}
