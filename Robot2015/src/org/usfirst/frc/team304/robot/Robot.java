package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Joystick;
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
	PeriodicTester tester;
	DrivingSystem base;
	LiftingSystem lifter;
	SensorSystem sense;

	Joystick controller;

	RobotAI driver;

	Timer timer;
	boolean isAutonomous = false;
	boolean isImproved = false;

	@Override
	public void robotInit() {
		controller = new Joystick(0);

		base = new DrivingSystem(new Victor(1), new Victor(3), new Victor(5),
				new Victor(7));

		lifter = new LiftingSystem(new Victor(2), new Victor(4));
		
		double maxRotAcc = SmartDashboard.getNumber("DB/Slider 3", 0.5d);
		sense = new SensorSystem(new DigitalInput(5), new DigitalInput(1),
				new DigitalInput(9), new DigitalInput(3), new DigitalInput(7),
				new DigitalInput(0), controller, maxRotAcc);

		tester = new PeriodicTester(sense);

		timer = new Timer();

		driver = new RobotAI(sense, base, lifter, timer);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		driver.go();
	}

	@Override
	public void autonomousInit() {
		isAutonomous = SmartDashboard.getBoolean("DB/Button 1", false);
		isImproved = SmartDashboard.getBoolean("DB/Button 2", false);
		if (isAutonomous) {
			timer.reset();
			timer.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		if (isAutonomous && isImproved) {
			driver.goAutonomousImproved();
		} else if (isImproved) {
			driver.goAutonomousTrashCan();
		} else if (isAutonomous) {
			driver.goAutonomousBasic();
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();

		tester.toTest();
	}

}