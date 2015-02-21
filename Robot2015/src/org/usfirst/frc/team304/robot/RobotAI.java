package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotAI {
	private SensorSystem sense;
	private DrivingSystem base;
	private LiftingSystem lifter;

	private boolean keepHeightMarker = false;

	public RobotAI(SensorSystem sense, DrivingSystem base, LiftingSystem lifter) {
		this.sense = sense;
		this.base = base;
		this.lifter = lifter;
	}

	public void go() {
		printDebug();

		lift();

		drive();
	}

	public void printDebug() {
		SmartDashboard.putString("DB/String 0",
				"PhotoL: " + sense.getPhotoLeftInValue());
		SmartDashboard.putString("DB/String 1",
				"PhotoR: " + sense.getPhotoRightInValue());
		SmartDashboard.putString("DB/String 2",
				"Lifter: " + sense.getLifterSensorValue());

		SmartDashboard.putString("DB/String 3", "lf: "
				+ base.getLeftFrontVictor().get());
		SmartDashboard.putString("DB/String 4", "lr: "
				+ base.getLeftRearVictor().get());
		SmartDashboard.putString("DB/String 8", "rf: "
				+ base.getRightFrontVictor().get());
		SmartDashboard.putString("DB/String 9", "rr: "
				+ base.getRightRearVictor().get());

		SmartDashboard.putString("DB/String 5",
				"Photo: " + sense.getPhotoInValue());
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
			lifter.keepSameHeight();

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
				} else { // if both sensors see it we approach
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
			keepHeightMarker = (keepHeightMarker) ? false : true;
		}

		if (sense.isLiftingUpPressed()) {
			keepHeightMarker = false;
			lifter.liftUp();
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
