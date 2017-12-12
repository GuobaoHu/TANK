package guyue.hu;

import java.awt.*;

public class Bullet {
	public static final int SIZE = 10;
	public static final int STEP = 10;
	private int x, y;
	private Direction direction;
	private TankClient tc;
	private boolean live = true;

	public Bullet(int x, int y, Direction direction, TankClient tc) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, SIZE, SIZE);
		g.setColor(c);
		this.move();
	}

	/**
	 * 子弹移动，并做出界判断
	 */
	public void move() {
		switch(direction) {
		case U :
			y -= STEP;
			break;
		case D :
			y += STEP;
			break;
		case L :
			y -= STEP;
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
		if(x > TankClient.GAME_WIDTH || x < 0 || y > TankClient.GAME_HEIGHT || y<0) {
			this.live = false;
			tc.getBullets().remove(this);
		}
	}
	
}
