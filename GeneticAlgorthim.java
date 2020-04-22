import java.awt.*;

public class GeneticAlgorthim{


	final static int TEST_MAX_POP = 10;
	final static double TEST_FITNESS_SCALE = 4;
	final static double TEST_MUTATION_RATE = .3;

	int maxPop;
	double fitnessScale;
	double mutationRate;

	Robot[] robots;


	public int generation;
	public int highestFitness;

	public GeneticAlgorthim(int maxPop, double fitnessScale, double mutationRate ){
		this.maxPop = maxPop;
		this.fitnessScale = fitnessScale;
		this.mutationRate = mutationRate;
		generation = 0;
		highestFitness = 0;
	}

	public void refreshHighestFitness(){
		for(Robot r: robots){
			highestFitness = Math.max(r.fitness,highestFitness);
		}
	}

	
	public void populateAt(int x, int y){
		robots = new Robot[maxPop];
		for(int index = 0; index < robots.length; index++){
			robots[index] = new Robot(x,y);
		}
	}

	public void stepRobots(){
		for(Robot r: robots){
			r.move();
		}
	}

	public void draw(Graphics g){
		for(Robot r : robots){
			r.draw(g);
		}
	}

}