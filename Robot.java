import java.awt.*;
import javax.swing.*;

public class Robot{

	final static Color ALIVE_COLOR = Color.GREEN;
	final static Color DEAD_COLOR = Color.RED;
	final static int DEAD_RADIUS = 10;
	final static int RADIUS = 10;

	final static int BRAIN_INPUT_NODES = 3;
	final static int BRAIN_HIDDEN_LAYERS = 1;
	final static int BRAIN_HIDDEN_NODES = 40;
	final static int BRAIN_OUTPUT_NODES = 1;

	final static int SPEED = 2;

	final double MAX_DISTANCE_TO_GOAL_SQUARED = Simulation.WINDOW_HEIGHT*Simulation.WINDOW_HEIGHT+Simulation.WINDOW_WIDTH*Simulation.WINDOW_WIDTH;
	final double MAX_DISTANCE_TO_GOAL_X = Simulation.WINDOW_WIDTH;
	final double MAX_DISTANCE_TO_GOAL_Y = Simulation.WINDOW_HEIGHT;


	double distanceToGoalSquared; 
	double distanceXToNearestPlatform;
	double distanceYToNearestPlatform;

	double theta;

	FeedForward brain;


	public double fitness;



	double x,y;

	public boolean alive;
    public double stepsAlive;



	public Robot(double x, double y){
		fitness = 0;
		stepsAlive = 0;
		alive = true;
		this.x = x;
		this.y = y;
		brain = new FeedForward(Robot.BRAIN_INPUT_NODES,Robot.BRAIN_HIDDEN_LAYERS,Robot.BRAIN_HIDDEN_NODES,Robot.BRAIN_OUTPUT_NODES);
	}

	public Robot(double x, double y, Robot parent, double mutationRate){
		fitness = 0;
		alive = true;
		this.x = x;
		this.y = y;
		brain = new FeedForward(parent.brain,mutationRate);
	}

	public void move(){
		x+=(Math.sin(theta)*Robot.SPEED);
		y+=(Math.cos(theta)*Robot.SPEED);
	}

	public void checkIfTouching(Layout layout){
		//first check any of the walls
		if(x<0||x>Simulation.WINDOW_WIDTH||y<0||y>Simulation.WINDOW_HEIGHT) alive = false;

	}

	public void calculateFitness(Layout layout){
		Obstacle goal = layout.goal;
		distanceToGoalSquared = distanceToObstacleSquared(goal);
		fitness = (MAX_DISTANCE_TO_GOAL_SQUARED-distanceToGoalSquared) * GeneticAlgorthim.FITNESS_SCALE;
	}

	public double distanceToObstacleX(Obstacle o){
		return o.mask.x - this.x;
	}

	public double distanceToObstacleY(Obstacle o){
		return o.mask.y - this.y;
	}

	public double distanceToObstacleSquared(Obstacle o){
		return distanceToObstacleX(o)*distanceToObstacleX(o)+distanceToObstacleY(o)*distanceToObstacleY(o);
	}


	public void calcVel(Layout o){
		System.out.println(theta);
		System.out.println(clampdistanceToObstacleX() + " " + clampdistanceToObstacleY());
		double[] data = new double[]{clampdistanceToGoalSquared(),clampdistanceToObstacleX(),clampdistanceToObstacleY()};
		double[] vel = brain.predict(data);
		theta = vel[0]*Math.PI*2;
	}

	public double clampdistanceToGoalSquared(){
		return ((double) 5/MAX_DISTANCE_TO_GOAL_SQUARED) * distanceToGoalSquared;
	}

	public double clampdistanceToObstacleX(){
		return ((double) 5/MAX_DISTANCE_TO_GOAL_X) * (Simulation.GOAL_SPAWN.x-x);
	}

	public double clampdistanceToObstacleY(){
		return ((double) 5/MAX_DISTANCE_TO_GOAL_Y) * (Simulation.GOAL_SPAWN.y-y);
	}

	public void draw(Graphics g){
		Color c = (alive)? Robot.ALIVE_COLOR : Robot.DEAD_COLOR;
		g.setColor(c);
		if(alive) g.fillOval((int)(x-Robot.RADIUS/2),(int)(y-Robot.RADIUS/2),Robot.RADIUS,Robot.RADIUS);
		else g.fillOval((int)(x-Robot.RADIUS/2),(int)(y-Robot.RADIUS/2),Robot.DEAD_RADIUS,Robot.DEAD_RADIUS);
	}

	public String toString(){
		return "Robot: at pos:[" + x + " , " + y + " ] theta:[" + theta + " ] fitness: " + fitness + " alive: " + alive; 
	}

}

