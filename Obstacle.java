import java.awt.*;

public class Obstacle{



	public Point mask;
	public int radius;

	public Obstacle(Point mask, int radius){
		 this.mask = mask;
		 this.radius = radius;
	}

	public void draw(Graphics g){
		g.fillOval(mask.x-radius,mask.y-radius,radius*2,radius*2);
	}



}