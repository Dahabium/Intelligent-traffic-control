package backend;

public class Model {

	
	
	public double desiredVelocity(double maxSpeed, int speedLimit, double obeyFactor){
		
		return Math.min(maxSpeed, speedLimit * obeyFactor); 
	}
	
	public double desiredVelocity(Car car) {
		
		car.setDesVel(desiredVelocity(car.getMaxVel(), car.getLocRoad().getSpeedLimit(), car.getObeyFactor()));
		return desiredVelocity(car.getMaxVel(), car.getLocRoad().getSpeedLimit(), car.getObeyFactor());
	}

	public double acceleration(Car car, double distCarFront, double carFrontVel) {
		
		return acceleration(car.getMinimumSpacing(), desiredVelocity(car), car.getVel(), car.getExponent(), car.getTimeHeadway(), car.getVel() - carFrontVel, distCarFront, car.getMaxAcc(), car.getDesDec());
	}

	private double acceleration(int s0, double v0, int v, int e, int t, double dV, double s, double a, double b) {
		
		return a * (1 - Math.pow(v/v0, e) - (s0 + Math.max(0, v*t + (v*dV)/(2 * Math.sqrt(a * b)))));
	}
	
	
}
