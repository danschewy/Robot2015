package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class SensorSystem {
	private DigitalInput photoIn, photoLeftIn, photoRightIn, switchLeftIn,
			switchRightIn, lifterSensor;

	private Joystick controller;
	
	private ADXL345_I2C accelerometer;

	public DigitalInput getPhotoIn() {
		return photoIn;
	}

	public boolean getPhotoInValue() {
		return !photoIn.get();
	}

	public DigitalInput getSwitchLeftIn() {
		return switchLeftIn;
	}

	public boolean getSwitchLeftInValue() {
		return !switchLeftIn.get();
	}

	public DigitalInput getSwitchRightIn() {
		return switchRightIn;
	}

	public boolean getSwitchRightInValue() {
		return !switchRightIn.get();
	}

	public boolean getPhotoLeftInValue() {
		return !photoLeftIn.get();
	}

	public DigitalInput getPhotoLeftIn() {
		return photoLeftIn;
	}

	public DigitalInput getPhotoRightIn() {
		return photoRightIn;
	}

	public boolean getPhotoRightInValue() {
		return !photoRightIn.get();
	}

	public DigitalInput getLifterSensor() {
		return lifterSensor;
	}

	public boolean getLifterSensorValue() {
		return !lifterSensor.get();
	}

	public Joystick getController() {
		return controller;
	}

	public SensorSystem(DigitalInput photoIn, DigitalInput photoLeftIn,
			DigitalInput photoRightIn, DigitalInput switchLeftIn,
			DigitalInput switchRightIn, DigitalInput lifterSensor,
			Joystick controller, double maxRotationAcceleration) {
		this.photoIn = photoIn;
		this.photoLeftIn = photoLeftIn;
		this.photoRightIn = photoRightIn;
		this.switchRightIn = switchRightIn;
		this.switchLeftIn = switchLeftIn;
		this.lifterSensor = lifterSensor;
		this.controller = controller;

		this.maxRotationAcceleration = maxRotationAcceleration;
		
		this.accelerometer = new ADXL345_I2C(Port.kOnboard, Range.k16G);
	}

	public double getXAcceleration() {
		return accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
	}
	
	public double getYAcceleration() {
		return accelerometer.getAcceleration(ADXL345_I2C.Axes.kY);
	}
	
	public double getZAcceleration() {
		return accelerometer.getAcceleration(ADXL345_I2C.Axes.kZ);
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
		return controller.getRawButton(4);
	}

	public boolean isBackwardPressed() {
		return controller.getRawButton(1);
	}

	public boolean isLeftPressed() {
		return controller.getRawButton(3);
	}

	public boolean isRightPressed() {
		return controller.getRawButton(2);
	}

	public boolean seesLifter() {
		return !lifterSensor.get();
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

	private double previousRotationLeft = 0d;
	private double previousRotationRight = 0d;
	private double maxRotationAcceleration;

	public double getSmoothRotation() {
		double currentRotationLeft = controller.getRawAxis(2);
		double currentRotationRight = controller.getRawAxis(3);
		// rot > (previous + max) = previous + max
		// else = rot
		double rotation = 0d;

		if (currentRotationLeft > (previousRotationLeft + maxRotationAcceleration)) {
			rotation = -(previousRotationLeft + maxRotationAcceleration);

			previousRotationLeft = rotation;
			previousRotationRight = 0d;
		} else if (currentRotationRight > (previousRotationRight + maxRotationAcceleration)) {
			rotation = previousRotationRight + maxRotationAcceleration;

			previousRotationRight = rotation;
			previousRotationLeft = 0d;
		} else {
			if (currentRotationLeft > currentRotationRight) {
				rotation = -currentRotationLeft;
				previousRotationLeft = rotation;
				previousRotationRight = 0d;
			} else {
				rotation = currentRotationRight;
				previousRotationRight = rotation;
				previousRotationLeft = 0d;
			}
		}

		return rotation;
	}

	public boolean isKeepHeightPressed() {
		return controller.getRawButton(7);
	}

}
