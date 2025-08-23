package frc.robot.commands.drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import static frc.robot.Subsystems.drive;
import frc.robot.subsystems.photonVision.Photon;

public class AutoTurnToNearestGamePiece extends Command {
    private static final double minStartTime = 0.25;
    private Timer startTimer = new Timer();

    private Supplier<Rotation2d> turn;

    public AutoTurnToNearestGamePiece(Photon camera) {
        turn = () -> new Rotation2d((camera.targetVisable()) ? camera.getYaw() : 0);
    }

    @Override
    public void initialize() {
        drive.setHeadingGoal(turn);
        startTimer.restart();
    }

    @Override
    public void end(boolean isInterrupted) {
        drive.clearHeadingGoal();
        startTimer.stop();
    }

    @Override
    public boolean isFinished() {
        return drive.atHeadingGoal() && startTimer.hasElapsed(minStartTime);
    }
    
}
