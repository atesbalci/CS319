
public class Rope {
	final double PULL_SPEED = 20;
	
	GameElement source;
	int x, y, reach;
	double speed;
	Hook hook;
	
	public Rope(int x, int y, GameElement source) {
		this.source = source;
		this.x = x;
		this.y = y;
		speed = 20;
		reach = 300;
		hook = new Hook();
	}
	
	public Hook fire(int xdest, int ydest) {
		if(!hook.active) {
			double angle = Math.atan((double)(y-ydest) / (double)(x-xdest));
			if(x-xdest < 0)
				hook = new Hook(x, y, angle, speed, this);
			else {
				angle = Math.PI + angle;
				hook = new Hook(x, y, angle, speed, this);
			}
			System.out.println(angle);
			return hook;
		}
		else {
			hook.active = false;
		}
		return null;
	}
}
