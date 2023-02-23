// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  public final Arm arm = new Arm();
  public final Claw claw = new Claw();
  public final StarClaw starClaw = new StarClaw();

  public static final Joystick leftJoystick = new Joystick(0);
  public static final Joystick rightJoystick = new Joystick(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    JoystickButton triggerStowPos = new JoystickButton(leftJoystick, Constants.ButtonMap.stowPosition);
    triggerStowPos.onTrue(new Rotate(arm, 0));

    JoystickButton triggerMiddlePos = new JoystickButton(leftJoystick, Constants.ButtonMap.middlePosition);
    triggerMiddlePos.onTrue(new Rotate(arm, 10));

    JoystickButton triggerOtherPos = new JoystickButton(leftJoystick, Constants.ButtonMap.otherArbPosition);
    triggerOtherPos.onTrue(new Rotate(arm, 30));

    JoystickButton triggerGroundPos = new JoystickButton(leftJoystick, Constants.ButtonMap.groundPosition);
    triggerGroundPos.onTrue(new Rotate(arm, 90));

    // JoystickButton triggerExt = new JoystickButton(leftJoystick, Constants.ButtonMap.fullyExtendedArm);
    // Extends 1 inch for testing
    // triggerExt.onTrue(new Extend(arm, 1));

    // JoystickButton RestPosExt = new JoystickButton(leftJoystick, Constants.ButtonMap.restPositionArm);
    // RestPosExt.onTrue(new Extend(arm, 0));

    JoystickButton triggerWristRight = new JoystickButton(rightJoystick, Constants.ButtonMap.wristRight);
    triggerWristRight.onTrue(new SetWristPos(claw, 225));

    JoystickButton triggerWristLeft = new JoystickButton(rightJoystick, Constants.ButtonMap.wristLeft);
    triggerWristLeft.onTrue(new SetWristPos(claw, -225));

    JoystickButton triggerWristCenter = new JoystickButton(rightJoystick, Constants.ButtonMap.wristCenter);
    triggerWristCenter.onTrue(new SetWristPos(claw, 0));

    
    JoystickButton triggerClawRest = new JoystickButton(rightJoystick, Constants.ButtonMap.clawRest);
    triggerClawRest.onTrue(new SetClawPos(claw, 0));

    JoystickButton triggerClawClosed = new JoystickButton(rightJoystick, Constants.ButtonMap.clawClosed);
    triggerClawClosed.onTrue(new SetClawPos(claw, 50));
    

    /* 
    JoystickButton triggerClawRest = new JoystickButton(rightJoystick, Constants.ButtonMap.clawRest);
    triggerClawRest.onTrue(new SetStarClawPos(starClaw, 0));

    JoystickButton triggerClawClosed = new JoystickButton(rightJoystick, Constants.ButtonMap.clawClosed);
    triggerClawClosed.onTrue(new SetStarClawPos(starClaw, 2));
    */

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }

  public static Joystick getJoy1() {
    return leftJoystick;
  }

  public static Joystick getJoy2() {
    return rightJoystick;
  }
}
