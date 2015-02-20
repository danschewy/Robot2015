package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class SensorSystem {
	private DigitalInput photoIn, photoLeftIn, photoRightIn, switchLeftIn,
			switchRightIn;

	public DigitalInput getPhotoIn() {
		return photoIn;
	}
	
	public DigitalInput getSwitchLeftIn() {
		return switchLeftIn;
	}

	public DigitalInput getSwitchRightIn() {
		return switchRightIn;
	}

	public DigitalInput getPhotoLeftIn() {
		return photoLeftIn;
	}

	public DigitalInput getPhotoRightIn() {
		return photoRightIn;
	}

	public Joystick getController() {
		return controller;
	}

	private Joystick controller;

	public SensorSystem(DigitalInput photoIn, DigitalInput photoLeftIn,
			DigitalInput photoRightIn, DigitalInput switchLeftIn,
			DigitalInput switchRightIn, Joystick controller) {
		this.photoIn = photoIn;
		this.photoLeftIn = photoLeftIn;
		this.photoRightIn = photoRightIn;
		this.switchRightIn = switchRightIn;
		this.switchLeftIn = switchLeftIn;
		this.controller = controller;
	}

	public boolean isLiftingUpPressed() {
		return controller.getRawButton(5);
	}

	public boolean isLiftingDownPressed() {
		return controller.getRawButton(6);
	}

	public boolean isAutoParkingPressed() {
		return controller.getRawButton(8);
	}

	public boolean isBoxVisible() {
		return !photoIn.get();
	}
	
	public boolean leftSideSees() {
		return !photoLeftIn.get();
	}
	
	public boolean rightSideSees() {
		return !photoRightIn.get();
	}
	
	public boolean sideSensorsNotSee() {
		return !leftSideSees() && !rightSideSees();
	}

	public boolean areBothSensorsTouched() {
		return !switchRightIn.get() && !switchLeftIn.get();
	}

	public boolean isLeftSensorTouched() {
		return !switchLeftIn.get();
	}

	public boolean isRightSensorTouched() {
		return !switchRightIn.get();
	}

	public boolean isForwardPressed() {
		return controller.getRawButton(1);
	}

	public boolean isBackwardPressed() {
		return controller.getRawButton(4);
	}

	public boolean isLeftPressed() {
		return controller.getRawButton(3);
	}

	public boolean isRightPressed() {
		return controller.getRawButton(2);
	}

	public double getMagnitude() {
		return controller.getMagnitude();
	}

	public double getDirection() {
		return controller.getDirectionDegrees();
	}

	public double getRotation() {
		double rotationLeft = -controller.getRawAxis(2);
		double rotationRight = controller.getRawAxis(3);

		return (Math.abs(rotationLeft) > 0.1) ? rotationLeft : rotationRight;
	}

	public boolean isKeepHeightPressed() {
		return controller.getRawButton(7);
	}

}
