import java.awt.*;
import java.awt.geom.*;

public class Obstacle extends StaticElement {
	int width, height;
	
	public Obstacle(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	public Rectangle2D.Double getRectangle() {
		return new Rectangle2D.Double(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		g.fillRect((int)x, (int)y, width, height);
	}
}
