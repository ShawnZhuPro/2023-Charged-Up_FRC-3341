// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PoweredIntake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoCone extends SequentialCommandGroup {
  /**
   * Creates a new AutoCone SequentialCommandGroup
   * @param dt - Drivetrain Subsystem
   * @param arm - Arm Subsystem
   * @param poweredIntake - Powered Intake Subsystem
   */
  public AutoCone(DriveTrain dt, Arm arm, PoweredIntake poweredIntake) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    poweredIntake.setClawPos(Constants.Measurements.poweredIntakeConePinchCanRotatePos);
    addCommands(new SetPoweredClawPos(poweredIntake, Constants.Measurements.poweredIntakeConePinchCanRotatePos), new Rotate(arm, 99), new Extend(arm, 15.65), new SetWristPosPI(poweredIntake, -225), new SpitTap(poweredIntake, 0.5, 5), new SetPoweredClawPos(poweredIntake, Constants.Measurements.poweredIntakeCubePinch), new Stow(dt, arm, poweredIntake), new AutoDrive(dt, -1.0, 0, true, true));
  }
}
