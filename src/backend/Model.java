package backend;

import simulation.Graph;

import java.awt.*;
import java.util.ArrayList;

public class Model {

	public Graph graph;

	public Model(Graph graph){
		this.graph = graph;

	}

	public void connectFSM(){
		//if 2 edges share the same vertex at their ends AND there exists a parrallel edge going out of that edge,
		// AND they are PARALLEL to each other, then let them share the same traffic light cycle.

		// if 2 edges share the same vertex at their ends AND one of them is perpendicular to another, then the FSM cycle of one edge will change
		//depending on the other

		graph.edges.get(0).getRoad().getTrafficLight().runRed();
		graph.edges.get(1).getRoad().getTrafficLight().runGreen();

		//use getroadweights method
	}

	
	public double desiredVelocity(double maxSpeed, int speedLimit, double obeyFactor){

		return Math.min(maxSpeed, speedLimit * obeyFactor); 
	}
	
	public double desiredVelocity(Car car) {
		
		car.setDesVel(desiredVelocity(car.getMaxVel(), 80, car.getObeyFactor()));
		return desiredVelocity(car.getMaxVel(), 80, car.getObeyFactor());
	}

	public double acceleration(Car car, double distCarFront, double carFrontVel) {

//		System.out.println("Acceleration (location) " + car.getVel() + "  " + car.getLocY());

		car.setAcc(acceleration(car.getMinimumSpacing(), desiredVelocity(car), car.getVel(), car.getExponent(), car.getTimeHeadway(), car.getVel() - carFrontVel, distCarFront, car.getMaxAcc(), car.getDesDec()));
		
		return acceleration(car.getMinimumSpacing(), desiredVelocity(car), car.getVel(), car.getExponent(), car.getTimeHeadway(), car.getVel() - carFrontVel, distCarFront, car.getMaxAcc(), car.getDesDec());
	}

	private double acceleration(int s0, double v0, double v, int e, int t, double dV, double s, double a, double b) {
		
		return a * (1 - Math.pow(v/v0, e) - Math.pow((s0 + Math.max(0, v*t + (v*dV)/(2 * Math.sqrt(a * b))))/s, 2));
	}
	
	
}
