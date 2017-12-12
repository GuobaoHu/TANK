package guyue.hu;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

public class Tank {
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public static final int STEP = 5;
	private int x, y;
	private Direction direction = Direction.STOP;
	private Direction ptDirection = Direction.D;
	private boolean bU, bD, bL, bR;
	private TankClient tc;
	private boolean good;
	private static Random rnd = new Random();
	private int step = 10 + rnd.nextInt(5);
	private Direction[] dirs = Direction.values();
	
	public Tank(int x, int y, TankClient tc, boolean good) {
		this.x = x;
		this.y = y;
		this.tc = tc;
		this.good = good;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		this.move();
		this.drawPT(g);
	}
	
	public void KeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			bU = true;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			bD = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			bL = true;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			bR = true;
		}
		this.defDir();
	}
	
	public void KeyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			bU = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			bD = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			bL = false;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			bR = false;
		} else if(e.getKeyCode() == KeyEvent.VK_1) {
			this.fire();
		}
		this.defDir();
	}
	
	/**
	 * 根据按键组合定义移动方向
	 */
	public void defDir() {
		if(bU && !bD && !bL && !bR) {
			direction = Direction.U;
		} else if(!bU && bD && !bL && !bR) {
			direction = Direction.D;
		} else if(!bU && !bD && bL && !bR) {
			direction = Direction.L;
		} else if(!bU && !bD && !bL && bR) {
			direction = Direction.R;
		} else if(bU && !bD && bL && !bR) {
			direction = Direction.LU;
		} else if(bU && !bD && !bL && bR) {
			direction = Direction.RU;
		} else if(!bU && bD && bL && !bR) {
			direction = Direction.LD;
		} else if(!bU && bD && !bL && bR) {
			direction = Direction.RD;
		} else {
			direction = Direction.STOP;
		}
	}
	
	/**
	 * 移动的具体实现及出界判断
	 */
	public void move() {
		if(direction != Direction.STOP) {
			ptDirection = direction;
		}
		switch(direction) {
		case U :
			y -= STEP;
			break;
		case D :
			y += STEP;
			break;
		case L :
			x -= STEP;
			break;
		case R :
			x += STEP;
			break;
		case LU :
			x -= STEP;
			y -= STEP;
			break;
		case RU :
			x += STEP;
			y -= STEP;
			break;
		case LD :
			x -= STEP;
			y += STEP;
			break;
		case RD :
			x += STEP;
			y += STEP;
			break;
		case STOP :
			break;
		}
		if(x > TankClient.GAME_WIDTH - WIDTH)  x = TankClient.GAME_WIDTH - WIDTH;
		else if(x < 0)  x = 0;
		if(y > TankClient.GAME_HEIGHT - HEIGHT)  y = TankClient.GAME_HEIGHT - HEIGHT;
		else if(y < 30)  y = 30;
		if(!good) {
			if(step == 0) {
				step = 10 + rnd.nextInt(5);
				direction = dirs[rnd.nextInt(8)];
			}
			step --;
			if(rnd.nextInt(40) > 38) {
				this.fire();
			}
		}
	}
	
	/**
	 * 开火
	 */
	public void fire() {
		tc.getBullets().add(new Bullet(x+WIDTH/2-Bullet.SIZE/2, y+HEIGHT/2-Bullet.SIZE/2, ptDirection, tc));
	}
	
	public void drawPT(Graphics g) {
		switch(ptDirection) {
		case U :
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y);
			break;
		case D :
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y+HEIGHT);
			break;
		case L :
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT/2);
			break;
		case R :
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y+HEIGHT/2);
			break;
		case LU :
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y);
			break;
		case RU :
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y);
			break;
		case LD :
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT);
			break;
		case RD :
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y+HEIGHT);
			break;
		case STOP :
			break;
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x , y, WIDTH, HEIGHT);
	}
	
	/**
	 * 检查坦克是否重叠
	 */
	public boolean isOverlap(List<Tank> tanks) {
		if(tanks.size() == 0) {
			return false;
		} else {
			for(int i=0; i<tanks.size(); i++) {
				Tank t = tanks.get(i);
				if(this != t && this.getRect().intersects(t.getRect())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
