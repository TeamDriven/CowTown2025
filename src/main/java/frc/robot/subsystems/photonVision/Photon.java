package frc.robot.subsystems.photonVision;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Photon extends SubsystemBase {
    private PhotonIO photonVisionIO;
    private PhotonIOInputsAutoLogged inputs = new PhotonIOInputsAutoLogged();

    public Photon(PhotonIO photonVisionIO) {
        this.photonVisionIO = photonVisionIO;
    }

    @Override
    public void periodic() {
        photonVisionIO.updateInputs(inputs);
        Logger.processInputs("photonVision", inputs);
    }

    public double getYaw() {
        return inputs.targetYaw;
    }
    public double getDistance() {
        return inputs.targetDistance;
    }
    public boolean targetVisable() {
        return inputs.hasTargets;
    }
}
