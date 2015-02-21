package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

public class DrivingSystem {
	private Victor leftFrontVictor, leftRearVictor, rightFrontVictor,
			rightRearVictor;

	private final static double DRIVE_SLOWLY = 0.23;
	private final static double ROTATE_SLOWLY = 0.3;
	private final static double DRIVE_NORMAL = 0.6;

	public Victor getLeftFrontVictor() {
		return leftFrontVictor;
	}

	public Victor getLeftRearVictor() {
		return leftRearVictor;
	}

	public Victor getRightFrontVictor() {
		return rightFrontVictor;
	}

	public Victor getRightRearVictor() {
		return rightRearVictor;
	}

	public RobotDrive getDriver() {
		return driver;
	}

	private RobotDrive driver;

	public DrivingSystem(Victor lfVictor, Victor lrVictor, Victor rfVictor,
			Victor rrVictor) {
		leftFrontVictor = lfVictor;
		leftRearVictor = lrVictor;
		rightFrontVictor = rfVictor;
		rightRearVictor = rrVictor;

		driver = new RobotDrive(leftFrontVictor, leftRearVictor,
				rightFrontVictor, rightRearVictor);

		// inverting right motors
		this.driver.setInvertedMotor(MotorType.kFrontRight, true);
		this.driver.setInvertedMotor(MotorType.kRearRight, true);
	}

	public void driveSet(double leftFront, double leftRear, double rightFront,
			double rightRear) {
		leftFrontVictor.set(leftFront);
		leftRearVictor.set(leftRear);
		rightFrontVictor.set(rightFront);
		rightRearVictor.set(rightRear);
	}

	public void driveForwardSlowly() {
		driveSet(DRIVE_SLOWLY, DRIVE_SLOWLY, -DRIVE_SLOWLY, -DRIVE_SLOWLY);
	}

	public void stop() {
		driveSet(0, 0, 0, 0);
	}

	public void rotateRightSlowly() {
		driveSet(0, 0, -ROTATE_SLOWLY, -ROTATE_SLOWLY);
	}

	public void rotateLeftSlowly() {
		driveSet(ROTATE_SLOWLY, ROTATE_SLOWLY, 0, 0);
	}

	public void driveForward() {
		driveSet(DRIVE_NORMAL, DRIVE_NORMAL, -DRIVE_NORMAL, -DRIVE_NORMAL);
	}

	public void driveBackwardSlowly() {
		driveSet(-DRIVE_SLOWLY, -DRIVE_SLOWLY, DRIVE_SLOWLY, DRIVE_SLOWLY);
	}

	public void driveBackward() {
		driveSet(-DRIVE_NORMAL, -DRIVE_NORMAL, DRIVE_NORMAL, DRIVE_NORMAL);
	}

	public void driveLeft() {
		driveSet(-DRIVE_NORMAL, DRIVE_NORMAL, -DRIVE_NORMAL, DRIVE_NORMAL);
	}

	public void driveRight() {
		driveSet(DRIVE_NORMAL, -DRIVE_NORMAL, DRIVE_NORMAL, -DRIVE_NORMAL);
	}

	public void driveSlowlyLeft() {
		driveSet(-DRIVE_SLOWLY, DRIVE_SLOWLY, -DRIVE_SLOWLY, DRIVE_SLOWLY);
	}

	public void driveSlowlyRight() {
		driveSet(DRIVE_SLOWLY, -DRIVE_SLOWLY, DRIVE_SLOWLY, -DRIVE_SLOWLY);
	}

	public void driveDirectly(double magnitude, double direction,
			double rotation) {
		driver.mecanumDrive_Polar(magnitude, direction, rotation);
	}
}
