package guyue.hu;

import java.awt.*;
import java.awt.event.*;

public class Tank {
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	private int x, y;
	private Direction direction = Direction.STOP;
	private boolean bU, bD, bL, bR;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		this.move();
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
		}
		this.defDir();
	}
	
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
	
	public void move() {
		switch(direction) {
		case U :
			y -= 5;
			break;
		case D :
			y += 5;
			break;
		case L :
			y -= 5;
			break;
		case R :
			x += 5;
			break;
		case LU :
			x -= 5;
			y -= 5;
			break;
		case RU :
			x += 5;
			y -= 5;
			break;
		case LD :
			x -= 5;
			y += 5;
			break;
		case RD :
			x += 5;
			y += 5;
			break;
		case STOP :
			break;
		}
	}
}
