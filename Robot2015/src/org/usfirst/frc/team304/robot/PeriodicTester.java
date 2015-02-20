package org.usfirst.frc.team304.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PeriodicTester {
	Joystick controller;

	public PeriodicTester(Joystick joystick) {
		controller = joystick;
	}

	public void toTest() {
		print(0, getAxesDebugString());
		print(0, getButtonDebugString());
		print(2, "Axes #: " + controller.getAxisCount());
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