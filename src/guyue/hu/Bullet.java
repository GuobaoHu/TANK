package guyue.hu;

import java.awt.*;
import java.util.List;

public class Bullet {
	public static final int SIZE = 10;
	public static final int STEP = 10;
	private int x, y;
	private Direction direction;
	private TankClient tc;
	private boolean live = true;
	private boolean good;
	
	public Bullet(int x, int y, Direction direction, TankClient tc, boolean good) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tc = tc;
		this.good = good;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		Color c = g.getColor();
		if(good) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.RED);
		}
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
	
	/**
	 * 获取子弹的Rectangle
	 * @return 返回Rectangle,用于做碰撞检测
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, SIZE, SIZE);
	}
	
	/**
	 * 子弹是否击中坦克判断
	 * @param t 被击中坦克
	 * @return 被击中返回true，否则false
	 */
	public boolean hitTank(Tank t) {
		if(live && t.isLive() && (good!=t.isGood()) &&
				this.getRect().intersects(t.getRect())) {
			if(!t.isGood()) {
				t.setLive(false);
			} else if(t.isGood()) {
				t.setLife(t.getLife()-10);
				if(t.getLife() <= 0) {
					t.setLive(false);
				}
			}
			live = false;
			tc.getBullets().remove(this);
			tc.getEnemys().remove(t);
			tc.getBooms().add(new Boom(x, y, tc));
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this.hitTank(t)) {
				return true;
			}
		}
		if(tanks.size() == 0) {
			tc.addEnmeys();
		}
		return false;
	}
	
	/**
	 * 撞墙判定
	 */
	public boolean hitWall(Wall w) {
		if(live && this.getRect().intersects(w.getRect())) {
			live = false;
			tc.getBullets().remove(this);
			return true;
		}
		return false;
	}
}
