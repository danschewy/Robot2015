package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.Timer;

public class RobotAI {
	private SensorSystem sense;
	private DrivingSystem base;
	private LiftingSystem lifter;
	private AnalogAccelerometer accel; // inside computer??? O_o

	private boolean keepHeightMarker = false;
	private Timer timer;
	double l = 0d;

	public RobotAI(SensorSystem sense, DrivingSystem base,
			LiftingSystem lifter, Timer timer) {
		this.sense = sense;
		this.base = base;
		this.lifter = lifter;
		this.timer = timer;
		
		accel = new AnalogAccelerometer(0); //create accelerometer on analog input 0
		accel.setSensitivity(.018); // Set sensitivity to 18mV/g (ADXL193)
		accel.setZero(2.5); //Set zero to 2.5V (actual value should be determined experimentally)
	}

	public void go() {
		double t = timer.get();

		l = +sense.getYAcceleration() * t * t / 2d;

		timer.reset();
		timer.start();
		printDebug();

		lift();

		drive();
	}

	public void goAutonomousImproved() {
		if (timer.get() < 1d) {
			lifter.liftUp();
		} else if (timer.get() < 1.5d) {
			base.driveForwardSlowly();
			lifter.liftUp();
		} else if (timer.get() < 5.5d) {
			lifter.liftUp();
			base.driveRight();
		} else if (timer.get() < 7d) {
			base.rotateLeft();
			lifter.liftUp();
		} else if (timer.get() < 10.5d) {
			base.stop();
			lifter.liftDown();
		} else if (timer.get() < 11d) {
			base.driveBackwardSlowly();
			lifter.stop();
		} else {
			base.stop();
		}
	}

	public void goAutonomousTrashCan() {
		if (timer.get() < 2d) {
			lifter.liftUp();
		} else if (timer.get() < 2.5d) {
			base.driveForwardSlowly();
			lifter.liftUp();
		} else if (timer.get() < 6.5d) {
			lifter.liftUp();
			base.driveRight();
		} else if (timer.get() < 8d) {
			base.rotateLeft();
			lifter.liftUp();
		} else if (timer.get() < 11.5d) {
			base.stop();
			lifter.liftDown();
		} else if (timer.get() < 12d) {
			base.driveBackwardSlowly();
			lifter.stop();
		} else {
			base.stop();
		}
	}

	public void goAutonomousBasic() {
		if (timer.get() < 2d) {
			lifter.liftUp();
		} else if (timer.get() < 4.5d) {
			lifter.liftUp();
			base.driveBackwardSlowly();
		} else if (timer.get() < 7d) {
			lifter.liftDown();
			base.stop();
		} else if (timer.get() < 8d) {
			base.driveBackwardSlowly();
		} else {
			base.stop();
		}
	}

	public void printDebug() {
		SmartDashboard.putString("DB/String 0",
				"X: " + sense.getXAcceleration());
		SmartDashboard.putString("DB/String 1",
				"Y: " + sense.getYAcceleration());
		SmartDashboard.putString("DB/String 2",
				"Z: " + sense.getZAcceleration());

		SmartDashboard.putString("DB/String 3", "l: " + l);

		SmartDashboard.putString("DB/String 5",
				"Accel: " + accel.getAcceleration());
		SmartDashboard.putString("DB/String 6",
				"SwitchRight: " + sense.getSwitchRightInValue());
		SmartDashboard.putString("DB/String 7",
				"SwitchLeft: " + sense.getSwitchLeftInValue());
	}

	// comment to make it commit...
	public void autoPark() {
		if (!sense.seesLifter()) {
			lifter.liftUp();
			keepHeightMarker = false;
		} else {
			keepHeightMarker = true;

			if (!sense.isBoxVisible()) {
				base.driveForwardSlowly();
			} else {
				base.stop();

				if (sense.sideSensorsNotSee()) {
					if (sense.areBothSensorsTouched()) {
						// lifting
					} else if (sense.isLeftSensorTouched()) {
						base.rotateRightSlowly();
					} else if (sense.isRightSensorTouched()) {
						base.rotateLeftSlowly();
					} else {
						base.driveForwardSlowly();
					}
				} else {
					if (sense.leftSideSees()) {
						base.driveSlowlyLeft();
					} else if (sense.rightSideSees()) {
						base.driveSlowlyRight();
					}
				}
			}
		}
	}

	public void lift() {
		if (sense.isKeepHeightPressed()) { // and if up or down pressed -
											// interrupt
			keepHeightMarker = true;
		}

		if (sense.isLiftingUpPressed()) {
			if (!sense.getLifterSensorValue()) {
				keepHeightMarker = false;
				lifter.liftUp();
			} else {
				lifter.stop();
			}
		} else if (sense.isLiftingDownPressed()) {
			keepHeightMarker = false;
			lifter.liftDown();
		} else if (keepHeightMarker) {
			lifter.keepSameHeight(); // add encoders, if box moves down -
										// move it up
		} else {
			lifter.stop();
		}
	}

	public void drive() {
		if (sense.isAutoParkingPressed()) {
			autoPark();
		} else if (sense.isForwardPressed()) {
			base.driveForward();
		} else if (sense.isBackwardPressed()) {
			base.driveBackward();
		} else if (sense.isLeftPressed()) {
			base.driveLeft();
		} else if (sense.isRightPressed()) {
			base.driveRight();
		} else {
			base.driveDirectly(Math.pow(sense.getMagnitude(), 2),
					sense.getDirection(), Math.pow(sense.getRotation(), 3));
		}
	}
}
