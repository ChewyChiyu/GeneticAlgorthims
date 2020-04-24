import java.awt.*;

public enum Layout{


	ALPHA( new Obstacle[]{ 
			
		new Obstacle(new Point((int)(Simulation.WINDOW_WIDTH*.2),(int)(Simulation.WINDOW_HEIGHT*.7)),150),
		new Obstacle(new Point((int)(Simulation.WINDOW_WIDTH*.3),(int)(Simulation.WINDOW_HEIGHT*.3)),100),
		new Obstacle(new Point((int)(Simulation.WINDOW_WIDTH*.5),(int)(Simulation.WINDOW_HEIGHT*.7)),60),
		new Obstacle(new Point((int)(Simulation.WINDOW_WIDTH*.6),(int)(Simulation.WINDOW_HEIGHT*.4)),90),
		new Obstacle(new Point((int)(Simulation.WINDOW_WIDTH*.9),(int)(Simulation.WINDOW_HEIGHT*.3)),40),
		new Obstacle(new Point((int)(Simulation.WINDOW_WIDTH*.8),(int)(Simulation.WINDOW_HEIGHT*.7)),85)
	    },
		new Obstacle(new Point(Simulation.GOAL_SPAWN.x,Simulation.GOAL_SPAWN.y),10) 
		);

	public Obstacle[] obstacles;
	public Obstacle goal;

	

	private Layout(Obstacle[] obstacle, Obstacle goal){
		this.obstacles = obstacle;
		this.goal = goal;
	}


	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		for(Obstacle platform: obstacles){
			platform.draw(g);
		}
		g.setColor(Color.GREEN);
		goal.draw(g);
	}



}