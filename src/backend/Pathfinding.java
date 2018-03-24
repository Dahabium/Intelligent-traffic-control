package backend;
import java.util.ArrayList;
import simulation.*; 


public class Pathfinding{


	private ArrayList<AStarNode> newGraph = new ArrayList<AStarNode>();
	
	public Pathfinding(Graph graph){
		
		for(int i = 0; i < graph.getNodes().size(); i++) {
			
			newGraph.add(new AStarNode(graph.getNodes().get(i)));
		}
		
		for(int i = 0; i < graph.getNodes().size(); i++) {
			
			for(int j = 0; j < graph.getAdjecents(graph.getNodes().get(i)).size(); j++) {
				
				newGraph.get(i).getNeighbours().add(new AStarNode(graph.getAdjecents(graph.getNodes().get(i)).get(j)));
				
			}
		}
	}
	
	public void Astar(Car car, Graph graph){
		
		ArrayList<AStarNode> closedSet = new ArrayList<AStarNode>(); 
		ArrayList<AStarNode> openSet = new ArrayList<AStarNode>();
		
		int start = 0;
		
		for(int i = 0; i < graph.getNodes().size(); i++) {
			
			if(graph.getNodes().get(i) == car.getStart()) {
				
				start = i;
			}
		}
		
		openSet.add(newGraph.get(start));
		
		int end = 0;
		
		for(int i = 0; i < graph.getNodes().size(); i++) {
			
			if(graph.getNodes().get(i) == car.getEnd()) {
				
				end = i;
			}
		}
		
		newGraph.get(start).setG_score(0);
		newGraph.get(start).setF_score(calcPytho(newGraph.get(start), newGraph.get(end)));

		AStarNode current;
		
		while(!openSet.isEmpty()){

			
			int index = 0;
			double score = Double.MAX_VALUE;
			
			for(int i = 0; i < openSet.size(); i++) {
				
				if(openSet.get(i).getF_score() <= score) {
					index = i;
					score = openSet.get(i).getF_score();
				}
			}
			
			current = openSet.get(index);
			
			if(current == newGraph.get(end)) {
				
				// return the path
				constructPath(current, newGraph.get(start));
			}
			
			openSet.remove(index);
			closedSet.add(current);
			
			for(int i = 0; i < current.getNeighbours().size(); i++) {
				
				boolean closed = false;
				
				for(int j = 0; j < closedSet.size(); j++) {
					
					if(closedSet.get(j) == current.getNeighbours().get(i)) {
						
						closed = true;
					}
				}
				
				boolean exists = false;
				
				for(int j = 0; j < openSet.size(); j++) {
					
					if(openSet.get(j) == current.getNeighbours().get(i)) {
						
						exists = true;
					}
				}
				
				if(!exists && !closed) {
					
					openSet.add(current.getNeighbours().get(i));
				}
				
				double tentative_gscore = current.getG_score() + calcPytho(current, current.getNeighbours().get(i));
				
				if(tentative_gscore < current.getNeighbours().get(i).getG_score()) {
					
					current.getNeighbours().get(i).setCameFrom(current);
					current.getNeighbours().get(i).setG_score(tentative_gscore);
					current.getNeighbours().get(i).setF_score(current.getNeighbours().get(i).getG_score() + calcPytho(current.getNeighbours().get(i), newGraph.get(end)));
				}
			}
		}
			

	}

	public ArrayList<AStarNode> constructPath(AStarNode current, AStarNode start){

		ArrayList<AStarNode> path = new ArrayList<>();
		path.add(current);

		while(current.getCameFrom() != start){
			current = current.getCameFrom();
			path.add(current);
		}

			return path;
	}
	
	
	public double calcPytho(AStarNode start, AStarNode end) {
		
		return Math.sqrt(Math.pow((start.getX() - end.getX()), 2) + (Math.pow(start.getY() - end.getY(), 2)));
	}
}
