import java.awt.*;

public class Obstacle{



	Rectangle mask;

	public Obstacle(Rectangle mask){
		 this.mask = mask;
	}

	public void draw(Graphics g){
		g.fillRect(mask.x,mask.y,mask.width,mask.height);
	}



}