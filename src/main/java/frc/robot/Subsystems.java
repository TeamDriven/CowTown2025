// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.DriveConstants;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOKrakenFOC;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOKraken;
import frc.robot.subsystems.intake.IntakeIOSim;
import frc.robot.subsystems.photonVision.Photon;
import frc.robot.subsystems.photonVision.PhotonIO;
import frc.robot.subsystems.photonVision.PhotonIOCamera;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.pivot.PivotIO;
import frc.robot.subsystems.pivot.PivotIOKraken;
import frc.robot.subsystems.pivot.PivotIOSim;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOLimelight;

/**
 * The Subsystems class represents the collection of subsystems used in the
 * robot. It provides
 * static references to various subsystem objects that are used in the robot.
 */
public final class Subsystems {
    public static final Drive drive;

    public static final Vision frontVision;
    public static final Vision backVision;

    public static final Photon camera;

    public static final Intake intake;

    public static final Pivot pivot;

    static {
        // Create subsystems
        if (Constants.getMode() != Constants.Mode.REPLAY) {
            switch (Constants.getRobot()) {
                case COMPBOT -> {
                    drive = new Drive(
                            new GyroIOPigeon2(true, "DriveBus"),
                            new ModuleIOKrakenFOC(DriveConstants.moduleConfigs[0], "DriveBus"),
                            new ModuleIOKrakenFOC(DriveConstants.moduleConfigs[1], "DriveBus"),
                            new ModuleIOKrakenFOC(DriveConstants.moduleConfigs[2], "DriveBus"),
                            new ModuleIOKrakenFOC(DriveConstants.moduleConfigs[3], "DriveBus"));

                    frontVision = new Vision("Bottom Vision", new VisionIOLimelight("limelight-bottom"),
                            drive::getSpeeds);
                    backVision = new Vision("Back Vision", new VisionIOLimelight("limelight-back"), drive::getSpeeds);

                    camera = new Photon(new PhotonIOCamera("front"));

                    intake = new Intake(new IntakeIOKraken(13, 14, 1));

                    pivot = new Pivot(new PivotIOKraken(15));
                }
                case DEVBOT -> {

                    drive = new Drive(
                            new GyroIO() {
                            },
                            new ModuleIO() {
                            },
                            new ModuleIO() {
                            },
                            new ModuleIO() {
                            },
                            new ModuleIO() {
                            });

                    frontVision = new Vision("Bottom Vision", new VisionIOLimelight("limelight-bottom"),
                            drive::getSpeeds);
                    backVision = new Vision("Back Vision", new VisionIOLimelight("limelight-back"), drive::getSpeeds);

                    camera = new Photon(new PhotonIOCamera("front"));

                    intake = new Intake(new IntakeIOKraken(13, 14, 1));

                    pivot = new Pivot(new PivotIOKraken(15));
                }
                case SIMBOT -> {
                    drive = new Drive(
                            new GyroIO() {
                            },
                            new ModuleIOSim(DriveConstants.moduleConfigs[0]),
                            new ModuleIOSim(DriveConstants.moduleConfigs[1]),
                            new ModuleIOSim(DriveConstants.moduleConfigs[2]),
                            new ModuleIOSim(DriveConstants.moduleConfigs[3]));

                    frontVision = new Vision("Bottom Vision", new VisionIO() {
                    },
                            drive::getSpeeds);
                    backVision = new Vision("Back Vision", new VisionIO() {
                    }, drive::getSpeeds);

                    camera = new Photon(new PhotonIO() {
                    });

                    intake = new Intake(new IntakeIOSim());

                    pivot = new Pivot(new PivotIOSim());
                    // throw new IllegalStateException("SIMBOT is not currently completely
                    // implemented on this robot");
                }
                default -> {
                    throw new IllegalStateException("Robot type not selected");
                }
            }
        } else {
            drive = new Drive(
                    new GyroIO() {
                    },
                    new ModuleIO() {
                    },
                    new ModuleIO() {
                    },
                    new ModuleIO() {
                    },
                    new ModuleIO() {
                    });

            frontVision = new Vision("Bottom Vision", new VisionIO() {
            }, drive::getSpeeds);
            backVision = new Vision("Back Vision", new VisionIO() {
            }, drive::getSpeeds);
            camera = new Photon(new PhotonIO() {
            });

            intake = new Intake(new IntakeIO() {
            });

            pivot = new Pivot(new PivotIO() {
            });
        }
    }
}
