package backend;

import simulation.Edge;
import simulation.Graph;
import simulation.Node;

import java.awt.*;
import java.util.ArrayList;

public class Car {

	
	private double desVel;
	private double maxVel;
	private int minimumSpacing;
	private int timeHeadway;
	private double desAcc;
	private double maxAcc;
	private double desDec;
	private double maxDec;
	private int exponent;
	private Road locRoad;

	//todo just doing it with an edge now, in future we will use road
	private Edge locEdge;

	private double locX;
	private double locY;
	private double time;
	private boolean carFollow;
	private double distCar;
	private double distTran;
	private double obeyFactor;
	
	private double vel = 0.5;
	private double acc = 2;

	private int width = 25;
	private int height = 25;

	private ArrayList<Integer> path;

	//TODO fix the startroadend thing (why do we need it?)
    public Car(Node start, Node end, Graph graph){
        this.desVel = 80;
        this.maxVel = 100;
        this.minimumSpacing = 0;
        this.timeHeadway = 2;
        this.desAcc = 1.5;
        this.maxAcc = 2.5;
        this.desDec = 1.67;
        this.maxDec = 3;
        this.exponent = 4;
        this.locX = start.getXpos();
        this.locY = start.getYpos();
        this.carFollow = false;
        this.obeyFactor = 1;
        this.start = start;
        this.end = end;
//        this.locRoad = graph.getEdge(start, startRoadend).getRoad();
    }
//    public Car()

    public ArrayList<Integer> getPath(){
    	return this.path;
	}
	public void setPath(ArrayList<Integer> path){
    	this.path = path;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	private Node start;
	private Node end;

	// departure and arrival locations need to be added
	// current location aswell
	
	public double getObeyFactor() {
		return obeyFactor;
	}

	public void setObeyFactor(double obeyFactor) {
		this.obeyFactor = obeyFactor;
	}

	public double getVel() {
		return vel;
	}

	public void setVel(double vel) {
		this.vel = vel;
	}

	public double getAcc() {
		return acc;
	}

	public void setAcc(double acc) {
		this.acc = acc;
	}

	public double getDesVel() {
		return desVel;
	}

	public void setDesVel(double desVel) {
		this.desVel = desVel;
	}

	public double getMaxVel() {
		return maxVel;
	}

	public void setMaxVel(double maxVel) {
		this.maxVel = maxVel;
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

	public double getDesAcc() {
		return desAcc;
	}

	public void setDesAcc(double desAcc) {
		this.desAcc = desAcc;
	}

	public double getMaxAcc() {
		return maxAcc;
	}

	public void setMaxAcc(double maxAcc) {
		this.maxAcc = maxAcc;
	}

	public double getDesDec() {
		return desDec;
	}

	public void setDesDec(double desDec) {
		this.desDec = desDec;
	}

	public double getMaxDec() {
		return maxDec;
	}

	public void setMaxDec(double maxDec) {
		this.maxDec = maxDec;
	}

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	public Road getLocRoad() {

//		return locRoad;
		return this.locEdge.road;

	}

	public void setLocRoad(Road locRoad) {
		this.locRoad = locRoad;
	}

	public void setLocEdge(Edge edge){
		this.locEdge = edge;
	}

	public Edge getLocEdge(){
		return locEdge;
	}

	public double getPercentageOnCurrentRoad(){
		//vertical edge
		if(locEdge.start.Xpos == locEdge.end.Xpos){

			double roadDistance = Math.abs(locEdge.start.Ypos - locEdge.end.Ypos);
			double driven = Math.abs(this.getLocY() - locEdge.start.Ypos);

			double z = driven / roadDistance;

			double roundZ = Math.round(z * 100.0) / 100.0;

			return roundZ;
		}

		//horizontal edge
		if(locEdge.start.Ypos == locEdge.end.Ypos){
			double roadDistance = Math.abs(locEdge.start.Xpos - locEdge.end.Xpos);
			double driven = Math.abs(this.getLocX() - locEdge.start.Xpos);

			double z = driven / roadDistance;

			double roundZ = Math.round(z * 100.0) / 100.0;

			return roundZ * 100;
		}

		return -1000000;
	}

	public double getLocX() {
		return locX;
	}

	public void setLocX(double locX) {
		this.locX = locX;
	}

	public double getLocY() {
		return locY;
	}

	public void setLocY(double locY) {
		this.locY = locY;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public boolean isCarFollow() {
		return carFollow;
	}

	public void setCarFollow(boolean carFollow) {
		this.carFollow = carFollow;
	}

	public double getDistCar() {
		return distCar;
	}

	public void setDistCar(double distCar) {
		this.distCar = distCar;
	}

	public double getDistTran() {
		return distTran;
	}

	public void setDistTran(double distTran) {
		this.distTran = distTran;
	}

	
}
