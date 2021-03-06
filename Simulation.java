import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class Simulation extends JPanel{

	final static int WINDOW_WIDTH = 1000;
	final static int WINDOW_HEIGHT = 800;

	final static Dimension WINDOW_DIMENSION = new Dimension(Simulation.WINDOW_WIDTH,Simulation.WINDOW_HEIGHT);

	final static int SIMULATION_SLEEP_TIME = 4;

	final static Point GLOBAL_SPAWN = new Point((int)(Simulation.WINDOW_DIMENSION.width*.05),(int)(Simulation.WINDOW_DIMENSION.height*.95));

	final static Point GOAL_SPAWN = new Point((int)(Simulation.WINDOW_DIMENSION.width*.9),(int)(Simulation.WINDOW_DIMENSION.height*.1));


	static BufferedImage canvas = new BufferedImage(Simulation.WINDOW_WIDTH,Simulation.WINDOW_HEIGHT,BufferedImage.TYPE_INT_RGB);
	static Graphics canvasGraphics = canvas.getGraphics();

	boolean isRunningSimulation = true;

	GeneticAlgorthim genetic;

	Layout layout;

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

		layout = Layout.ALPHA;
		Simulation.clearCanvas();

		while(isRunningSimulation){
			update();
			try{ Thread.sleep(Simulation.SIMULATION_SLEEP_TIME); }catch(Exception e){e.printStackTrace();}
		}
	}


	public void update(){
		genetic.update(layout); 
		repaint();
	}

	public void paintComponent(Graphics g){

		if(canvas!=null) g.drawImage(canvas,0,0,canvas.getWidth(),canvas.getHeight(),this);
		if(layout!=null) layout.draw(g);
		if(genetic!=null) genetic.draw(g);
	}

	public static void clearCanvas(){
		canvasGraphics.setColor(Color.WHITE);
		canvasGraphics.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
	}

	public static void plotOnCanvas(int x, int y){
		canvasGraphics.setColor(Color.BLUE);
		canvasGraphics.fillOval(x,y,2,2);
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