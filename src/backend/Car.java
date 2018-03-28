package backend;
import simulation.Edge;
import simulation.Graph;
import simulation.Node;

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
	private double locX;
	private double locY;
	private double time;
	private boolean carFollow;
	private double distCar;
	private double distTran;
	private double obeyFactor;
	
	private double vel;
	private double acc;
	
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

	
	public Car(Node start, Node end, Graph graph){
		this.desVel = 80;
		this.maxVel = 120;
		this.minimumSpacing = 5;
		this.timeHeadway = 10;
		this.desAcc = 1;
		this.maxAcc = 3;
		this.desDec = 1;
		this.maxDec = 3;
		this.exponent = 4;
		this.locX = start.getXpos();
		this.locY = start.getYpos();
		this.carFollow = false;
		this.obeyFactor = 1;
		this.start = start;
		this.end = end;
		this.locRoad = graph.getEdge(start, graph.getNodeByIndex(1)).getRoad();
	}
	
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
		return locRoad;
	}

	public void setLocRoad(Road locRoad) {
		this.locRoad = locRoad;
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
