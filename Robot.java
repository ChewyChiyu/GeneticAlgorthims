import java.awt.*;
import javax.swing.*;

public class Robot{

	final static Color ALIVE_COLOR = Color.GREEN;
	final static Color DEAD_COLOR = Color.RED;
	final static Color MUTATED_COLOR = Color.BLUE;
	final static int DEAD_RADIUS = 10;
	final static int RADIUS = 10;

	final static int BRAIN_INPUT_NODES = 5;
	final static int BRAIN_HIDDEN_LAYERS = 1;
	final static int BRAIN_HIDDEN_NODES = 40;
	final static int BRAIN_OUTPUT_NODES = 1;

	final static int SPEED = 2;

	final double MAX_DISTANCE_TO_GOAL = Math.sqrt(Simulation.WINDOW_HEIGHT*Simulation.WINDOW_HEIGHT+Simulation.WINDOW_WIDTH*Simulation.WINDOW_WIDTH);
	final double MAX_DISTANCE_TO_OBSTACLE_X = Simulation.WINDOW_WIDTH;
	final double MAX_DISTANCE_TO_OBSTACLE_Y = Simulation.WINDOW_HEIGHT;



	double distanceToGoal; 
	double distanceXToNearestPlatform;
	double distanceYToNearestPlatform;

	double theta;

	FeedForward brain;

	public double fitness;

	double x,y;

	public boolean alive;
    public double stepsAlive;

    boolean mutated;

    int radius;

	public Robot(double x, double y){
		fitness = 0;
		stepsAlive = 0;
		alive = true;
		mutated = false;
		radius = Robot.RADIUS;
		this.x = x;
		this.y = y;
		brain = new FeedForward(Robot.BRAIN_INPUT_NODES,Robot.BRAIN_HIDDEN_LAYERS,Robot.BRAIN_HIDDEN_NODES,Robot.BRAIN_OUTPUT_NODES);
	}

	public Robot(double x, double y, Robot parent, double mutationRate){
		fitness = 0;
		alive = true;
		stepsAlive = 0;
		mutated = true;
		radius = Robot.RADIUS;
		this.x = x;
		this.y = y;
		brain = new FeedForward(parent.brain,mutationRate);
	}

	public void reset(int x, int y){
		this.x = x;
		this.y =y;
		fitness = 0;
		stepsAlive = 0;
		alive = true;
		mutated = false;
	}

	public void move(){
		x+=(Math.sin(theta)*Robot.SPEED);
		y+=(Math.cos(theta)*Robot.SPEED);
		Simulation.plotOnCanvas((int)x,(int)y);
	}

	public void checkIfTouching(Layout layout){
		//first check any of the walls
		if(x<0||x>Simulation.WINDOW_WIDTH||y<0||y>Simulation.WINDOW_HEIGHT) alive = false;

		//touching goal / layout
		Obstacle[] platforms = layout.obstacles;
		Obstacle goal = layout.goal;

		if(distanceToObstacle(goal)<=((radius+goal.radius))) alive = false;

		for(Obstacle o : platforms){
			if(distanceToObstacle(o)<=((radius+o.radius))) alive = false;
		}

	}

	public void calculateFitness(Layout layout){
		Obstacle goal = layout.goal;
		distanceToGoal = distanceToObstacle(goal);
		fitness = Math.max(fitness,(MAX_DISTANCE_TO_GOAL- distanceToGoal) * GeneticAlgorthim.FITNESS_SCALE);
	}

	public double distanceToObstacleX(Obstacle o){
		return Math.abs(o.mask.x - this.x);
	}

	public double distanceToObstacleY(Obstacle o){
		return Math.abs(o.mask.y - this.y);
	}

	public double distanceToObstacle(Obstacle o){
		return Math.sqrt(distanceToObstacleX(o)*distanceToObstacleX(o)+distanceToObstacleY(o)*distanceToObstacleY(o));
	}


	public Obstacle nearestObstacle(Layout layout){
		double nearestDistance = Double.MAX_VALUE;
		Obstacle nearest = null;
		for(Obstacle o : layout.obstacles){
			double d = distanceToObstacle(o) - o.radius - radius;
			if(nearest==null) nearest = o;
			if(d<nearestDistance){
				nearest = o;
				nearestDistance = d;
			}
		}
		return nearest;
	}


	public void calcVel(Layout o){
		Obstacle near = nearestObstacle(o);
		Obstacle goal = o.goal;
		double[] data = new double[]{clampdistanceToGoal(),clampdistanceToObstacleX(goal),clampdistanceToObstacleY(goal),clampdistanceToObstacleX(near),clampdistanceToObstacleY(near)};
		double[] vel = brain.predict(data);
		theta = vel[0]*Math.PI*2;
	}

	public double clampdistanceToGoal(){
		return (((double)(Function.SIGMOID_MAX_DOMAIN-Function.SIGMOID_MIN_DOMAIN)/MAX_DISTANCE_TO_GOAL)*(distanceToGoal))+Function.SIGMOID_MIN_DOMAIN;
	}


	public double clampdistanceToObstacleX(Obstacle o){
		return (((double)(Function.SIGMOID_MAX_DOMAIN-Function.SIGMOID_MIN_DOMAIN)/MAX_DISTANCE_TO_OBSTACLE_X)*(o.mask.x-x))+Function.SIGMOID_MIN_DOMAIN;

	}

	public double clampdistanceToObstacleY(Obstacle o){
		return (((double)(Function.SIGMOID_MAX_DOMAIN-Function.SIGMOID_MIN_DOMAIN)/MAX_DISTANCE_TO_OBSTACLE_Y)*(o.mask.y-y))+Function.SIGMOID_MIN_DOMAIN;

	}

	public void draw(Graphics g){
		Color c = (alive) ? ((mutated) ? Robot.MUTATED_COLOR : Robot.ALIVE_COLOR) : Robot.DEAD_COLOR;
		g.setColor(c);
		if(alive) g.fillOval((int)(x-radius/2),(int)(y-radius/2),radius,radius);
		else g.fillOval((int)(x-radius/2),(int)(y-radius/2),Robot.DEAD_RADIUS,Robot.DEAD_RADIUS);
		
	}

	public String toString(){
		return "Robot: at pos:[" + x + " , " + y + " ] theta:[" + theta + " ] fitness: " + fitness + " alive: " + alive; 
	}

}

