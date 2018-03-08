package backend;

public class Car {

	
	private int desiredVelocity;
	private int minimumSpacing;
	private int timeHeadway;
	private double acceleration;
	private double deceleration;
	private int exponent;
	
	private double time;
	// departure and arrival locations need to be added
	// current location aswell
	
	public Car(int desiredVelocity, int minimumSpacing){
		
	}

	public int getDesiredVelocity() {
		return desiredVelocity;
	}

	public void setDesiredVelocity(int desiredVelocity) {
		this.desiredVelocity = desiredVelocity;
	}

	public int getMinimumSpacing() {
		return minimumSpacing;
	}

	public void setMinimumSpacing(int minimumSpacing) {
		this.minimumSpacing = minimumSpacing;
	}

	public int getTimeHeadway() {
		return timeHeadway;
	}

	public void setTimeHeadway(int timeHeadway) {
		this.timeHeadway = timeHeadway;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getDeceleration() {
		return deceleration;
	}

	public void setDeceleration(double deceleration) {
		this.deceleration = deceleration;
	}

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
}
