package cihw2;

import java.util.ArrayList;

public class Fuzzy {
	private double angle;
	private double turnAngle;
	private ArrayList<Sensor> sensorList = new ArrayList<Sensor>();
	private ArrayList<Integer> distanceDescriptors = new ArrayList<Integer>(); // 0:far
																				// 1:middle
																				// 2:near
	private double leftSensorDist;
	private double middleSensorDist;
	private double rightSensorDist;
	private double smallA = 30;
	private double smallB = 50;
	private double mediumA = 30;
	private double mediumB = 50;
	private double mediumC = 80;
	private double mediumD = 100;
	private double largeA = 80;
	private double largeB = 100;

	public Fuzzy(Sensor sensor1, Sensor sensor2, Sensor sensor3, Double angle) {
		sensorList.add(sensor1);
		sensorList.add(sensor2);
		sensorList.add(sensor3);

		this.angle = angle;

	}

	public double getTurnAngle() {

		for (int i = 0; i < sensorList.size(); i++) {
			Sensor sensor = sensorList.get(i);
			Integer des = decitionRank(sensor.getClosestLineDistance());
			distanceDescriptors.add(des);
		}

		this.turnAngle = calAngle();
		distanceDescriptors.clear();

		return this.turnAngle;
	}

	public double checkRank(double angle) {
		leftSensorDist = this.sensorList.get(2).getClosestLineDistance();
		middleSensorDist = this.sensorList.get(0).getClosestLineDistance();
		rightSensorDist = this.sensorList.get(1).getClosestLineDistance();

		double[] leftSensorRank = checkDistanceRank(leftSensorDist);
		double[] mediumSensorRank = checkDistanceRank(middleSensorDist);
		double[] largeSensorRank = checkDistanceRank(rightSensorDist);

		double leftAlpha = Double.MIN_VALUE;
		double middleAlpha = Double.MIN_VALUE;
		double rightAlpha = Double.MIN_VALUE;

		if (leftAlpha < leftSensorRank[0]) {
			leftAlpha = leftSensorRank[0];
		} else if (leftAlpha < leftSensorRank[1]) {
			leftAlpha = leftSensorRank[1];
		} else if (leftAlpha < leftSensorRank[2]) {
			leftAlpha = leftSensorRank[2];
		}

		if (middleAlpha < mediumSensorRank[0]) {
			middleAlpha = mediumSensorRank[0];
		} else if (middleAlpha < mediumSensorRank[1]) {
			middleAlpha = mediumSensorRank[1];
		} else if (middleAlpha < mediumSensorRank[2]) {
			middleAlpha = mediumSensorRank[2];
		}

		if (rightAlpha < largeSensorRank[0]) {	
			rightAlpha = largeSensorRank[0];
		} else if (rightAlpha < largeSensorRank[1]) {
			rightAlpha = largeSensorRank[1];
		} else if (rightAlpha < largeSensorRank[2]) {
			rightAlpha = largeSensorRank[2];
		}
		double exAngle = angle + 40;
		double turnAngle = (exAngle * leftAlpha + exAngle * middleAlpha + exAngle * rightAlpha)
				/ (leftAlpha + middleAlpha + rightAlpha);

		return turnAngle;
	}

	public double[] checkDistanceRank(double distance) {

		double smallRank = 0;
		double mediumRank = 0;
		double largeRank = 0;

		if (distance > largeB) {
			largeRank = 1;
		} else if (distance <= largeB && distance > largeA) {
			largeRank = (distance - largeA) / (largeB - largeA);
		} else {
			largeRank = 0;
		}

		if (distance < mediumA) {
			mediumRank = 0;
		} else if (distance >= mediumA && distance < mediumB) {
			mediumRank = (distance - mediumA) / (mediumB - mediumA);
		} else if (distance >= mediumB && distance < mediumC) {
			mediumRank = 1;
		} else if (distance >= mediumC && distance < mediumD) {
			mediumRank = (distance - mediumC) / (mediumD - mediumC);
		} else {
			mediumRank = 0;
		}

		if (distance < smallA) {
			smallRank = 1;
		} else if (distance >= smallA && distance > smallB) {
			smallRank = (distance - smallA) / (smallB - smallA);
		} else {
			smallRank = 0;
		}
		double[] returnValue = { smallRank, mediumRank, largeRank };

		return returnValue;

	}

	public Integer decitionRank(double distance) {

		Integer descriptor = -1;

		if (distance > 100) {
			descriptor = 0;
			return descriptor;
		} else if ((distance > 50) && (distance <= 100)) {
			descriptor = 1;
			return descriptor;
		} else if (distance <= 50) {
			descriptor = 2;
			return descriptor;
		}

		return descriptor;
	}

	public double calAngle() {
		double angle = 0;
		int sensorNumber = distanceDescriptors.size();
		int zeroCount = 0;

		// For prevent start , all sensor's distance is all zero : 0 0 0
		if (sensorNumber == 0)
			return 0;

		for (int i = 0; i < sensorNumber; i++) {
			if (distanceDescriptors.get(i) == 0) {
				zeroCount++;
			}
		}

		if (zeroCount == sensorNumber) {
			return 0;
		}

		int leftS = distanceDescriptors.get(2);
		int middleS = distanceDescriptors.get(0);
		int rightS = distanceDescriptors.get(1);
		// Fuzzy rules

		if (sensorNumber == 3) {
			if (leftS == 0 && middleS == 0 && rightS == 0) {
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 0 && middleS == 0 && rightS == 1) {
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 0 && middleS == 0 && rightS == 2) {
				angle = -40;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 0 && middleS == 1 && rightS == 0) {
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 0 && middleS == 1 && rightS == 1) {
				angle = -30;

				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 0 && middleS == 1 && rightS == 2) {
				angle = -40;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 0 && middleS == 2 && rightS == 0) {
				angle = 40;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 0 && middleS == 2 && rightS == 1) {
				angle = -40;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 0 && middleS == 2 && rightS == 2) {
				angle = -40;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 0 && rightS == 0) {

				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 0 && rightS == 1) {
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 0 && rightS == 2) {
				angle = 10;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 1 && rightS == 0) {
				angle = 10;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 1 && rightS == 1) {
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 1 && rightS == 2) {
				angle = -10;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 2 && rightS == 0) {
				angle = 10;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 2 && rightS == 1) {
				angle = 30;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 1 && middleS == 2 && rightS == 2) {
				angle = -30;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 0 && rightS == 0) {
				angle = 30;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 0 && rightS == 1) {
				angle = 10;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 0 && rightS == 2) {
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 1 && rightS == 0) {
				angle = 30;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 1 && rightS == 1) {
				angle = 30;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 1 && rightS == 2) {
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 2 && rightS == 0) {
				angle = 40;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 2 && rightS == 1) {
				angle = -10;
				angle = checkRank(angle) - 40;
				return angle;

			} else if (leftS == 2 && middleS == 2 && rightS == 2) {
				angle = checkRank(angle) - 40;
				return angle;

			}
		}

		return angle;
	}
}
