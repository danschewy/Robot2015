package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftingSystem {
	private Victor leftVictor;
	private Victor rightVictor;

	private static double KEEP_HEIGHT = 0.1;

	public Victor getLeftVictor() {
		return leftVictor;
	}

	public Victor getRightVictor() {
		return rightVictor;
	}

	public LiftingSystem(Victor leftVictor, Victor rightVictor) {
		this.leftVictor = leftVictor;
		this.rightVictor = rightVictor;
	}

	public void liftUp() {
		leftVictor.set(getSpeedUp());
		rightVictor.set(getSpeedUp());
	}

	public void liftDown() {
		leftVictor.set(getSpeedDown());
		rightVictor.set(getSpeedDown());
	}

	public void keepSameHeight() {
		leftVictor.set(KEEP_HEIGHT);
		rightVictor.set(KEEP_HEIGHT);
	}

	private double getSpeedUp() { // default value is 0.3
		double speed = SmartDashboard.getNumber("DB/Slider 0") / 5;
		if (speed != 0) {
			return speed;
		} else {
			return 0.3;
		}
	}

	private double getSpeedDown() { // default value is -0.3
		double speed = -SmartDashboard.getNumber("DB/Slider 1") / 5;

		if (speed != 0) {
			return speed;
		} else {
			return -0.3;
		}
	}
	
	public void stop() {
		leftVictor.set(0);
		rightVictor.set(0);
	}
}
