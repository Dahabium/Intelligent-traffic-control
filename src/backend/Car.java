package backend;

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
	private int locX;
	private int locY;
	private double time;
	private boolean carFollow;
	private double distCar;
	private double distTran;
	private double obeyFactor;
	
	private int vel;
	private double acc;
	
	// departure and arrival locations need to be added
	// current location aswell
	
	public Car(int desiredVelocity, int minimumSpacing){
		 
	}
	
	public double getObeyFactor() {
		return obeyFactor;
	}

	public void setObeyFactor(double obeyFactor) {
		this.obeyFactor = obeyFactor;
	}

	public int getVel() {
		return vel;
	}

	public void setVel(int vel) {
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

	public int getLocX() {
		return locX;
	}

	public void setLocX(int locX) {
		this.locX = locX;
	}

	public int getLocY() {
		return locY;
	}

	public void setLocY(int locY) {
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
