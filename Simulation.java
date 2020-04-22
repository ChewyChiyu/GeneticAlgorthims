import java.awt.*;
import javax.swing.*;

public class Simulation extends JPanel{

	final static Dimension WINDOW_DIMENSION = new Dimension(800,800);

	final static int SIMULATION_SLEEP_TIME = 4;

	final static Point GLOBAL_SPAWN = new Point(WINDOW_DIMENSION.width/2,WINDOW_DIMENSION.height/2);




	boolean isRunningSimulation = true;

	GeneticAlgorthim genetic;

	public static void main(String[] args){
		new Simulation();
	}

	Simulation(){
		loadPanel();
		simulationCycle();
	}

	

	public void simulationCycle(){

		genetic = new GeneticAlgorthim(GeneticAlgorthim.TEST_MAX_POP,GeneticAlgorthim.TEST_MUTATION_RATE,Simulation.GLOBAL_SPAWN);
		genetic.populate();

		while(isRunningSimulation){
			update();
			try{ Thread.sleep(Simulation.SIMULATION_SLEEP_TIME); }catch(Exception e){e.printStackTrace();}
		}
	}


	public void update(){
		genetic.update();
		repaint();
	}

	public void paintComponent(Graphics g){
		if(genetic!=null)
			genetic.draw(g);
	}

	public void loadPanel(){
		JFrame frame = new JFrame("GENETIC ALGORTHIM");
		frame.add(this);
		frame.setPreferredSize(Simulation.WINDOW_DIMENSION);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}