import java.awt.*;

public class GeneticAlgorthim{


	final static int TEST_MAX_POP = 10;
	final static double TEST_MUTATION_RATE = .3;




	int maxPop;
	double fitnessScale;
	double mutationRate;

	Robot[] robots;


	public int generation;
	public double highestFitness;

	boolean toNextGeneration;

	Point globalSpawn;

	public GeneticAlgorthim(int maxPop, double mutationRate, Point globalSpawn){
		this.maxPop = maxPop;
		this.mutationRate = mutationRate;
		this.globalSpawn = globalSpawn;
		generation = 0;
		highestFitness = 0;
		toNextGeneration = false;
	}

	public void refreshHighestFitness(){
		for(Robot r: robots){
			highestFitness = Math.max(r.fitness,highestFitness);
		}
	}

	
	public void populate(){
		robots = new Robot[maxPop];
		for(int index = 0; index < robots.length; index++){
			robots[index] = new Robot(globalSpawn.x,globalSpawn.y);
		}
	}

	public void respawnIntoPool(Robot best){
		for(int index = 0; index < robots.length; index++){
			robots[index] = new Robot(globalSpawn.x,globalSpawn.y,best,mutationRate);
		}
	}

	public void update(){
		if(toNextGeneration){ 
			//transition to next generation
			transitionToNextGeneration();
		}else{

			stepRobots();
			checkIfContinueGeneration();
		}

	}

	public void transitionToNextGeneration(){
		refreshHighestFitness();
		Robot best  = pruneHighestFitnessRobot();
		respawnIntoPool(best);
		generation++;
		toNextGeneration = true;
	}

	public Robot pruneHighestFitnessRobot(){
		Robot best = null;
		for(Robot r: robots){
			if(best == null) best = r;
			best = (best.fitness < r.fitness) ? r : best;
		}
		return best;
	}

	public void checkIfContinueGeneration(){
		//if all robots dead continue to next generation
		boolean continueToNext = true;
		for(Robot r: robots){
			if(r.alive) continueToNext = false;
		}

	}

	public void stepRobots(){
		for(Robot r: robots){
			r.calcVel();
			r.move();
		}
	}

	public void draw(Graphics g){
		for(Robot r : robots){
			r.draw(g);
		}
	}

}