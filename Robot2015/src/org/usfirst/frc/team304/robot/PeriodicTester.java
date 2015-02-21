package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PeriodicTester {
	private Joystick controller;
	private SensorSystem sense;

	public PeriodicTester(SensorSystem sense) {
		controller = sense.getController();
		this.sense = sense;
	}

	public void toTest() {
		print(0, getAxesDebugString());
		print(1, getButtonDebugString());
		print(2, "Axes #: " + controller.getAxisCount());

		print(3, "PhotoL: " + sense.getPhotoLeftInValue());
		print(4, "PhotoR: " + sense.getPhotoRightInValue());
		print(5, "Lifter: " + sense.getLifterSensorValue());

		print(6, "Photo: " + sense.getPhotoInValue());
		print(8, "SwitchRight: " + sense.getSwitchRightInValue());
		print(9, "SwitchLeft: " + sense.getSwitchLeftInValue());
	}

	private String getAxesDebugString() {
		String stixk = "Axes: ";

		for (int x = 0; x < 12; x++) { // maximum number of axes is 12
			double val = controller.getRawAxis(x);

			stixk = stixk + (Math.abs(val) > 0.1 ? "1" : "0");
		}

		return stixk;
	}

	private String getButtonDebugString() {
		String buttonDebug = "Buttons: ";
		for (int x = 1; x < 12; x++)
			buttonDebug = buttonDebug + ((controller.getRawButton(x)) ? 1 : 0);

		return buttonDebug;
	}

	private void print(int lineNumber, String text) {
		SmartDashboard.putString("DB/String " + lineNumber, text);
	}
}