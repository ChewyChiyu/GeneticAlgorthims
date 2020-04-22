import java.awt.*;
import javax.swing.*;

public class Robot{

	final static Color ALIVE_COLOR = Color.GREEN;
	final static Color DEAD_COLOR = Color.RED;
	final static int DEAD_RADIUS = 10;
	final static int RADIUS = 25;

	final static int BRAIN_INPUT_NODES = 5;
	final static int BRAIN_HIDDEN_LAYERS = 5;
	final static int BRAIN_HIDDEN_NODES = 10;
	final static int BRAIN_OUTPUT_NODES = 5;


	FeedForward brain;
	
	public double fitness;

	double x,y;
	double dx,dy;

	public boolean alive;

	public Robot(double x, double y){
		fitness = 0;
		alive = true;
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		brain = new FeedForward(Robot.BRAIN_INPUT_NODES,Robot.BRAIN_HIDDEN_LAYERS,Robot.BRAIN_HIDDEN_NODES,Robot.BRAIN_OUTPUT_NODES);
	}

	public Robot(double x, double y, Robot parent, double mutationRate){
		fitness = 0;
		alive = true;
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
		brain = new FeedForward(parent.brain,mutationRate);
	}

	public void mutate(){

	}

	public void move(){
		x+=dx;
		y+=dy;
	}

	public void calcVel(){

	}

	public void draw(Graphics g){
		Color c = (alive)? Robot.ALIVE_COLOR : Robot.DEAD_COLOR;
		g.setColor(c);
		if(alive)
			g.fillOval((int)(x-Robot.RADIUS/2),(int)(y-Robot.RADIUS/2),Robot.RADIUS,Robot.RADIUS);
		else 
			g.fillOval((int)(x-Robot.RADIUS/2),(int)(y-Robot.RADIUS/2),Robot.DEAD_RADIUS,Robot.DEAD_RADIUS);
	}

	public String toString(){
		return "Robot: at pos:[" + x + " , " + y + " ] vel:[" + dx + " , " + dy + " ] fitness: " + fitness + " alive: " + alive; 
	}

}

