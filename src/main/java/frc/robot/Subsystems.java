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

    public static final Vision bottomVision;
    public static final Vision backVision;
    public static final Vision topVision;

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

                    bottomVision = new Vision("Bottom Vision", new VisionIOLimelight("limelight-bottom"),
                            drive::getSpeeds);
                    backVision = new Vision("Back Vision", new VisionIOLimelight("limelight-back"), drive::getSpeeds);
                    topVision = new Vision("Top Vision", new VisionIOLimelight("limelight-top"), drive::getSpeeds);
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

                    bottomVision = new Vision("Bottom Vision", new VisionIOLimelight("limelight-bottom"),
                            drive::getSpeeds);
                    backVision = new Vision("Back Vision", new VisionIOLimelight("limelight-back"), drive::getSpeeds);
                    topVision = new Vision("Top Vision", new VisionIOLimelight("limelight-top"), drive::getSpeeds);

                    // leds = new LED(new LEDIOCANdle(60));
                }
                case SIMBOT -> {
                    drive = new Drive(
                        new GyroIO() {},
                        new ModuleIOSim(DriveConstants.moduleConfigs[0]),
                        new ModuleIOSim(DriveConstants.moduleConfigs[1]),
                        new ModuleIOSim(DriveConstants.moduleConfigs[2]),
                        new ModuleIOSim(DriveConstants.moduleConfigs[3]));

                    // bottomVision = new Vision("Bottom Vision", new VisionIO() {}, drive::getSpeeds);
                    // backVision = new Vision("Back Vision", new VisionIO() {}, drive::getSpeeds);
                    // topVision = new Vision("Top Vision", new VisionIO() {}, drive::getSpeeds);
                    // rightVision = new Vision("Right Vision", new VisionIO() {},
                    // drive::getSpeeds);

                    throw new IllegalStateException("SIMBOT is not currently completely implemented on this robot");
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

            bottomVision = new Vision("Bottom Vision", new VisionIO() {
            }, drive::getSpeeds);
            backVision = new Vision("Back Vision", new VisionIO() {
            }, drive::getSpeeds);
            topVision = new Vision("Top Vision", new VisionIO() {
            }, drive::getSpeeds);
        }
    }
}
