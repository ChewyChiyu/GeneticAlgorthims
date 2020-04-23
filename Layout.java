import java.awt.*;

public enum Layout{


	ALPHA( new Obstacle[]{ 
			//no obstacles for now
	    },
		new Obstacle(new Rectangle(Simulation.GOAL_SPAWN.x,Simulation.GOAL_SPAWN.y,10,10)) 
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
		goal.draw(g);
	}



}