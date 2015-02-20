package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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

	public void robotInit() {
		controller = new Joystick(0);

		base = new DrivingSystem(new Victor(1), new Victor(3), new Victor(5),
				new Victor(7));

		lifter = new LiftingSystem(new Victor(2), new Victor(4));

		sense = new SensorSystem(new DigitalInput(5), new DigitalInput(1),
				new DigitalInput(9), new DigitalInput(3), new DigitalInput(7),
				controller);

		tester = new PeriodicTester(controller);
		driver = new RobotAI(sense, base, lifter);
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		driver.go();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();

		tester.toTest();
	}

}