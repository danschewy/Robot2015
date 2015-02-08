package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
//import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick stick;
	//AnalogInput bannerSensor;
	int autoLoopCounter;
	Victor leftFrontVictor;
	Victor leftRearVictor;
	Victor rightFrontVictor;
	Victor rightRearVictor;
	
	Victor liftVictor;	//lifting mechanism
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	stick = new Joystick(0);
    	SmartDashboard.putString("DB/String 0", "Hello, World!");
    	leftFrontVictor = new Victor(1);
    	leftRearVictor = new Victor(3);
    	rightFrontVictor = new Victor(5);
    	rightRearVictor = new Victor(7);
    	myRobot = new RobotDrive(leftFrontVictor,
    							 leftRearVictor,
    							 rightFrontVictor,
    							 rightRearVictor);
    	// it doesn't know which motors are inverted; we have to tell it!
    	this.myRobot.setInvertedMotor(MotorType.kFrontRight, true);
    	this.myRobot.setInvertedMotor(MotorType.kRearRight, true);
    	liftVictor = new Victor(2);
    	liftVictor = new Victor (4);
    	//bannerSensor = new AnalogInput(6);
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	double magnitude = stick.getMagnitude();
    	double direction = stick.getDirectionDegrees();
    	double rotation =  stick.getRawAxis(2);
		myRobot.mecanumDrive_Polar(magnitude, direction, rotation);
		SmartDashboard.putString("DB/String 0", "magnitude: "+magnitude);
		SmartDashboard.putString("DB/String 1", "direction: "+direction);
		SmartDashboard.putString("DB/String 2", "rotation: "+rotation);
		
		double speedUp = SmartDashboard.getNumber("DB/Slider 0") / 5;
		double speedDown = SmartDashboard.getNumber("DB/Slider 1") / 5;
		double speedz = (stick.getTrigger()) ? .25d : 0;
		
		double leftFrontInversion = Double.parseDouble(SmartDashboard.getString("DB/String 3", "1" ));
		double leftRearInversion = Double.parseDouble(SmartDashboard.getString("DB/String 4", "1"));
		double rightFrontInversion = Double.parseDouble(SmartDashboard.getString("DB/String 8", "1"));
		double rightRearInversion = Double.parseDouble(SmartDashboard.getString("DB/String 9", "1"));
		
		/*leftFrontVictor.set(leftFrontInversion*speedz);
		leftRearVictor.set(leftRearInversion*speedz);
		rightFrontVictor.set(rightFrontInversion*-speedz);
		rightRearVictor.set(rightRearInversion*-speedz);*/
		
		//lifting
		if(stick.getRawButton(5)) {
			liftVictor.set(speedUp);
			liftVictor.set(speedUp);
		} 
		else if(stick.getRawButton(6)) {
				liftVictor.set(-speedDown);
				liftVictor.set(-speedDown);
			} else {
				liftVictor.set(0);
				liftVictor.set(0);
			}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    	double magnitude = stick.getMagnitude();
    	double direction = stick.getDirectionDegrees();
    	double rotation = stick.getRawAxis(2);
		SmartDashboard.putString("DB/String 0", "m: "+magnitude);
		SmartDashboard.putString("DB/String 1", "d: "+direction);
		SmartDashboard.putString("DB/String 2", "r: "+rotation);
    }
    
}
