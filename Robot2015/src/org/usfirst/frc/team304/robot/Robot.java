package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
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
	GenericHID joystick;
	//AnalogInput bannerSensor;
	int autoLoopCounter;
	Victor leftFrontVictor;
	Victor leftRearVictor;
	Victor rightFrontVictor;
	Victor rightRearVictor;
	
	//lifting mechanism
	Victor leftLiftVictor;
	Victor rightLiftVictor;
	
	DigitalInput photoIn, switchRightIn, switchLeftIn;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	photoIn = new DigitalInput(9);
    	switchRightIn = new DigitalInput(3);
    	switchLeftIn = new DigitalInput(5);
    	
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
    	leftLiftVictor = new Victor(2);
    	rightLiftVictor = new Victor (4);
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
    	double rotation =  (stick.getRawAxis(2) != 0) ? -stick.getRawAxis(2) : (stick.getRawAxis(3));
    	//myRobot.mecanumDrive_Polar(Math.pow(magnitude, 2), direction, Math.pow(rotation, 3));
		SmartDashboard.putString("DB/String 0", "m:"+magnitude);
		SmartDashboard.putString("DB/String 1", "d:"+direction);
		SmartDashboard.putString("DB/String 2", "r:"+rotation);
		//good
		
		double speedUp = SmartDashboard.getNumber("DB/Slider 0") / 5;
		double speedDown = SmartDashboard.getNumber("DB/Slider 1") / 5;
		//double speedz = (stick.getTrigger()) ? .25d : 0;
		
        SmartDashboard.putString("DB/String 3", "" + leftFrontVictor.get());
        SmartDashboard.putString("DB/String 4", "" + leftRearVictor.get());
        SmartDashboard.putString("DB/String 8", "" + rightFrontVictor.get());
        SmartDashboard.putString("DB/String 9", "" + rightRearVictor.get());
		
        SmartDashboard.putString("DB/String 5", "Photo: "+photoIn.get());
        SmartDashboard.putString("DB/String 6", "SwitchRight: "+switchRightIn.get());
        SmartDashboard.putString("DB/String 7", "SwitchLeft: "+switchLeftIn.get());
        
		/*leftFrontVictor.set(leftFrontInversion*speedz);
		leftRearVictor.set(leftRearInversion*speedz);
		rightFrontVictor.set(rightFrontInversion*-speedz);
		rightRearVictor.set(rightRearInversion*-speedz);*/
		
		//lifting
		if(stick.getRawButton(5)) {
			leftLiftVictor.set(speedUp);
			rightLiftVictor.set(speedUp);
		} 
		else if(stick.getRawButton(6)) {
				leftLiftVictor.set(-speedDown);
				rightLiftVictor.set(-speedDown);
		}
		else {
				leftLiftVictor.set(0);
				rightLiftVictor.set(0);
		}
		
		if(stick.getRawButton(8)) {
			if(photoIn.get()) {
				driveSet(.15, .15, -.15, -.15);
			}
			else {
				driveSet(0, 0, 0, 0);
				
				if(!switchLeftIn.get() && !switchRightIn.get()) {
					//lifting
				}
				else if(!switchLeftIn.get()) {
					driveSet(0, 0, -0.23, -0.23);
				}
				else if(!switchRightIn.get()) {
					driveSet(0.23, 0.23, 0, 0);
				}
				else {
					driveSet(.15, .15, -.15, -.15);
				}
			}
		}
		else if (stick.getRawButton(1)) {
			leftFrontVictor.set(-.5);
			leftRearVictor.set(-.5);
			rightFrontVictor.set(.5);
			rightRearVictor.set(.5);
		}
		else if (stick.getRawButton(4)){
			leftFrontVictor.set(.5);
			leftRearVictor.set(.5);
			rightFrontVictor.set(-.5);
			rightRearVictor.set(-.5);
		}
		else if (stick.getRawButton(2)){
			leftFrontVictor.set(.5);
			leftRearVictor.set(-.5);
			rightFrontVictor.set(.5);
			rightRearVictor.set(-.5);
		}
		else if (stick.getRawButton(3)){
			leftFrontVictor.set(-.5);
			leftRearVictor.set(.5);
			rightFrontVictor.set(-.5);
			rightRearVictor.set(.5);
		}
		else {
			myRobot.mecanumDrive_Polar(Math.pow(magnitude, 2), direction, Math.pow(rotation, 3));
		}
    }
    
    public void driveSet(double leftFront, double leftRear,
    						double rightFront, double rightRear) {
    	leftFrontVictor.set(leftFront);
		leftRearVictor.set(leftRear);
		rightFrontVictor.set(rightFront);
		rightRearVictor.set(rightRear);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    	double magnitude = stick.getMagnitude();
    	double direction = stick.getDirectionDegrees();
    	double rotation = (stick.getRawAxis(2) != 0) ? stick.getRawAxis(2) : (-stick.getRawAxis(3));
		/*SmartDashboard.putString("DB/String 0", "m: "+magnitude);
		SmartDashboard.putString("DB/String 1", "d: "+direction);
		SmartDashboard.putString("DB/String 2", "r: "+rotation);*/
    	
    	String stixk = "";
		for (int x=0; x<12; x++) {
			double val = stick.getRawAxis(x);
			stixk = stixk + ((val != 0) ? "1" : "0");
		}
		SmartDashboard.putString("DB/String 1", stixk);
		
		String button = "";
		for (int x=1; x<9; x++)
			button = button + ((stick.getRawButton(x)) ? 1 : 0);
		
		SmartDashboard.putString("DB/String 9", button);
		SmartDashboard.putString("DB/String 8", ""+stick.getAxisCount());
    }
    
}
