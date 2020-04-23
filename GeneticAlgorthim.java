import java.awt.*;

public class GeneticAlgorthim{


	final static int TEST_MAX_POP = 100;
	final static double TEST_MUTATION_RATE = .1;

	final static double FITNESS_SCALE = .1;


	public int maxPop;
	public double fitnessScale;
	public double mutationRate;

	public Robot[] robots;


	public int generation;
	public double highestFitness;

	public boolean toNextGeneration;

	public Point globalSpawn;

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
		best.reset(globalSpawn.x,globalSpawn.y);
		robots[robots.length-1] = best;
		for(int index = robots.length-2; index >= 0; index--){
			robots[index] = new Robot(globalSpawn.x,globalSpawn.y,best,mutationRate);
		}
	}

	public void update(Layout layout){
		if(checkIfContinueGeneration()){ 
			//transition to next generation
			transitionToNextGeneration();

		}else{
			stepRobots(layout);
			checkIfContinueGeneration();
		}

	}

	public void transitionToNextGeneration(){
		Robot best  = pruneHighestFitnessRobot();
		respawnIntoPool(best);
		generation++;
		Simulation.clearCanvas();
	}

	public Robot pruneHighestFitnessRobot(){
		Robot best = null;
		for(Robot r: robots){
			if(best == null) best = r;
			best = (best.fitness < r.fitness) ? r : best;
		}
		highestFitness = best.fitness;
		return best;
	}

	public boolean checkIfContinueGeneration(){
		//if all robots dead continue to next generation
		for(Robot r: robots){
			if(r.alive) return false;
		}
		return true;
	}

	public void stepRobots(Layout layout){
		for(Robot r: robots){
			if(!r.alive) continue;
			r.checkIfTouching(layout);
			r.calculateFitness(layout);
			r.calcVel(layout);
			r.move();
			r.stepsAlive++;
		}
	}


	public void draw(Graphics g){
		for(Robot r : robots){
			if(r!=null) r.draw(g);
		}
	}

}