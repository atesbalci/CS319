import java.awt.Graphics;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.geom.*;

public class Sword extends Weapon {
	Timer duration;
	
	public Sword() {
		super(true);
		duration = new Timer(1000, new FireAction());
		duration.setRepeats(false);
		damage = 20;
	}
	
	public void draw(Graphics g) {
		if(firing) {
			if(direction)
				g.fillRect(x, y, 20, 5);
			else
				g.fillRect(x - 20, y, 20, 5);
		}
	}
	
	public Rectangle2D.Double getImpact() {
		if(direction)
			return new Rectangle2D.Double(x, y, 20, 5);
		return new Rectangle2D.Double(x - 20, y, 20, 5);
	}
	
	public Bullet getBullet() {
		return null;
	}

	public void fire() {
		firing = true;
		duration.start();
	}
	
	private class FireAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			firing = false;
		}
	}
}
