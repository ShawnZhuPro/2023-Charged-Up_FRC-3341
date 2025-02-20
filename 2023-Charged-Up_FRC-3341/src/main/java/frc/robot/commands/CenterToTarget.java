// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CenterToTarget extends CommandBase {
  /** Creates a new CenterToTarget. */
  // Here I took everything from limelight subsystem and called it lime
  public Limelight lime;
  // Here is the middle of the x and y axis on the target
  public double centerx;
  public double centery;
  private final TankDrive tankDrive;
  private static DriveTrain dt;
  public PIDController Tpid;
  public PIDController Fpid;
  double TurnSpeed = 0.0;  
  double FowardSpeed = 0.0;  

  public CenterToTarget(Limelight lime, Drivetrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    tankDrive = new TankDrive(dt, null, null);
    this.lime = lime;
    this.dt = dt;

    // Connects limelight subsystem to this command
    addRequirements(dt, lime);
    centerx = Limelight.get_tx();
    centery = Limelight.get_ty();

    /* PID constants we found through characterization in sysid
    
        P is the Proportional constant 
          It will correct the error depending on how big the amount of error is:
          Small amount of error = low correction, High = larger correction 
          
        I is the Integral constant
          It adds up all the past errors to help remove constant errors because
          no matter how small the constant error, the sum will be significant enough 
          to adjust the controller output as needed
          
        D is the derivative constant
          It will predict the amount of error in the future because it examines
          the slope of the change in error 

    */
    Tpid = new PIDController(0.0093825*2, 0.0, 0.0);
    Fpid = new PIDController(0.0093825*2, 0.0, 0.0);

    Tpid.setTolerance(1);
    Fpid.setTolerance(1);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    centerx = Limelight.get_tx();
    centery = Limelight.get_ty();
    dt.resetEncoders();
    Tpid.setSetpoint(0.0);
    Fpid.setSetpoint(0.0);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // You need this because it keeps calling the value instead of only once because the robot is moving
    centerx = Limelight.get_tx();
    centery = Limelight.get_ty();
    TurnSpeed = Tpid.calculate(centerx) * 0.3;
    FowardSpeed = Fpid.calculate(Limelight.getDistance_Test() * 0.3);
    
    /* 
    if(Math.abs(distance) < 11.3){
      speed1 = Math.abs();
    } else if(Math.abs(distance) > 11.3){
      speed1 = Math.abs();
    }
    if(Math.abs(speed) > 0.6){
      speed = Math.abs(0.6)*(Math.abs(speed)/speed);
    } else if(Math.abs(speed) < 0.15){
      speed = Math.abs(0.15)*(Math.abs(speed)/speed);
    }
    */
    
    dt.tankDrive(FowardSpeed+TurnSpeed, FowardSpeed-TurnSpeed);
    SmartDashboard.putNumber("Speed", FowardSpeed);
}
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    dt.tankDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Tpid.atSetpoint() && Fpid.atSetpoint();
    //return false;
  }
}
