package backend;

import java.util.ArrayList;

public class Game {

	
	public Game(int x, int y){
		
		this.x = x;
		this.y = y;
	}
	
	private int x;
	private int y;
	private ArrayList<ArrayList<Intersection>> grid = new ArrayList<ArrayList<Intersection>>();
	private ArrayList<Car> cars = new ArrayList<Car>();
	private ArrayList<Road> roads = new ArrayList<Road>();
	
	
	public void initialiseGrid() {
		
		for(int i = 0; i < x; i++) {
			
			grid.add(new ArrayList<Intersection>());
			
			for(int j = 0; j < y; j++) {
				
				grid.get(i).add(new Intersection());
			}
		}
	}
	
//	public void addCar(Car car) {
//		cars.add()
//	}
}
