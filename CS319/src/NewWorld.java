import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class NewWorld extends JPanel {
	private static final long serialVersionUID = -90716432820280065L;
	final int WIDTH = 800;
	final int HEIGHT = 600;
	public static final int DELAY = 16;
	
	ArrayList<GameElement> elements;
	Guy guy;
	double gravity, friction;
	boolean running, paused;
	float interpolation;
	int fps, frameCount;
	long lastFpsTime = 0;
	
	public NewWorld() {
		setFocusable(true);
		elements = new ArrayList<GameElement>();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(new Dimension(WIDTH, HEIGHT));
		addElement(new Obstacle(-5000, HEIGHT, 10000, 1));
		gravity = 2;
		addKeyListener(new WorldKey());
		addMouseListener(new WorldMouse());
		friction = 0.5;
		fps = 60;
		frameCount = 0;
		running = true;
		paused = false;
	}
	
	public void start() {
		Thread loop = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		loop.start();
	}
	
	public void addElement(GameElement e) {
		elements.add(e);
	}
	
	public void setGravity(int g) {
		gravity = g;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < elements.size(); i++) {
			elements.get(i).draw(g);
		}
		frameCount++;
	}
	
	public void setGuy(Guy g) {
		elements.remove(guy);
		elements.add(g);
		guy = g;
	}
	
	public void gameLoop() {
		final float interval = 1000000000 / fps;
		
		long now;
		float intervalNow = interval;
		long lastLoop = System.nanoTime();
		
		while(running) {
			update((double)(intervalNow/interval));
			now = System.nanoTime();
			intervalNow = now - lastLoop;
			lastLoop = now;
			if(intervalNow < interval) {
				try {
					Thread.sleep((long)((interval-intervalNow)/1000000));
					intervalNow = interval;
				} catch(Exception e) {}
			}
		}
	}
	
	public void update(double d) {
		GameElement e, o;
					
		for(int i = 0; i < elements.size(); i++) {
			e = elements.get(i);
			e.action(d);
			
			if(!e.flying && e.yvel < 20)
				e.yvel += gravity*d;
			
			if(e.yvel > 0) {
				for(int k = 0; k < e.yvel*d; k++) {
					e.moveY(1);
					e.ground = false;
					o = obstructed(elements.get(i));
					if(o != null) {
						e.obstruction("Bottom", o);
						if(e.smooth && o.smooth) {
							e.moveY(-1);
							e.yvel = 0;
							e.ground = true;
							break;
						}
					}
				}
			}
			if(e.yvel < 0) {
				for(int k = 0; k > e.yvel*d; k--) {
					e.moveY(-1);
					o = obstructed(elements.get(i));
					if(o != null) {
						e.obstruction("Top", o);
						if(e.smooth && o.smooth) {
							e.moveY(1);
							e.yvel = 0;
							e.jumping = false;
							break;
						}
					}
				}
			}
			
			if(e.xvel >= 1) {
				for(int k = 0; k < e.xvel*d; k++) {
					e.moveX(1);
					o = obstructed(elements.get(i));
					if(o != null) {
						e.obstruction("Left", o);
						if(e.smooth && o.smooth) {
							e.moveX(-1);
							e.xvel = 0;
							break;
						}
					}
				}
				if(e.fricted)
					e.xvel -= friction;
			}
			if(e.xvel <= -1) {
				for(int k = 0; k > e.xvel*d; k--) {
					e.moveX(-1);
					o = obstructed(elements.get(i));
					if(o != null) {
						e.obstruction("Right", o);
						if(e.smooth && o.smooth) {
							e.moveX(1);
							e.xvel = 0;
							break;
						}
					}
				}
				if(e.fricted)
					e.xvel += friction*d;
			}
			
			if(!e.active) {
				elements.remove(i);
				i--;
			}
		}
		
		if(guy.firing()) {
			if(guy.weapon.isMelee()) {
				Rectangle2D.Double impact = (Rectangle2D.Double)guy.weapon.getImpact();
				for(int k = 0; k < elements.size(); k++) {
					if(elements.get(k).getRectangle().intersects(impact.x, impact.y, impact.width, impact.height) && elements.get(k) != guy)
						elements.get(k).damage(guy.weapon.damage*((double)DELAY)/((double)1000));
				}
			}
			if(!guy.weapon.isMelee()) {
				addElement(guy.weapon.getBullet());
			}
		}
	}
	
	public GameElement obstructed(GameElement e) {
		Rectangle2D.Double r = e.getRectangle();
		
		for(int i = 0; i < elements.size(); i++) {
			GameElement o = elements.get(i);
					
			if(o != e) {
				Rectangle2D.Double or = o.getRectangle();
				
				if(o instanceof Bullet) {
				}				
				else if(e instanceof Bullet && o == guy){
				}				
				else if(r.intersects(or.x, or.y, or.width, or.height)) {
					return o;
				}
			}
		}
		return null;
	}
	
	public class WorldKey extends KeyAdapter {
		public void keyPressed(KeyEvent e) {			
			if(e.getKeyCode() == KeyEvent.VK_D) {
				guy.right(true);
			}			
			if(e.getKeyCode() == KeyEvent.VK_A) {
				guy.left(true);
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W) {
				guy.jump(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				guy.fire();
			}
		}
		
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_D) {
				guy.right(false);
			}
			
			if(e.getKeyCode() == KeyEvent.VK_A) {
				guy.left(false);
			}
			
			if(e.getKeyCode() == KeyEvent.VK_W) {
				guy.jump(false);
			}
		}
	}
	
	public class WorldMouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			Hook h = guy.throwHook(e.getX(), e.getY());
			if(h != null)
				elements.add(h);
		}
	}
}
