package guyue.hu;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import java.util.*;

public class Bullet {
	public static final int SIZE = 10;
	public static final int STEP = 10;
	private int x, y;
	private Direction direction;
	private TankClient tc;
	private boolean live = true;
	private boolean good;
	private static Image[] imgs;
	private static Map<String, Image> bImgs = new HashMap<String, Image>();
	
	static {
		try {
			imgs = new Image[] {
					ImageIO.read(Bullet.class.getResource("/images/missileU.gif")),
					ImageIO.read(Bullet.class.getResource("/images/missileD.gif")),
					ImageIO.read(Bullet.class.getResource("/images/missileL.gif")),
					ImageIO.read(Bullet.class.getResource("/images/missileR.gif")),
					ImageIO.read(Bullet.class.getResource("/images/missileLU.gif")),
					ImageIO.read(Bullet.class.getResource("/images/missileRU.gif")),
					ImageIO.read(Bullet.class.getResource("/images/missileLD.gif")),
					ImageIO.read(Bullet.class.getResource("/images/missileRD.gif")),
			};
			bImgs.put("U", imgs[0]);
			bImgs.put("D", imgs[1]);
			bImgs.put("L", imgs[2]);
			bImgs.put("R", imgs[3]);
			bImgs.put("LU", imgs[4]);
			bImgs.put("RU", imgs[5]);
			bImgs.put("LD", imgs[6]);
			bImgs.put("RD", imgs[7]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Bullet(int x, int y, Direction direction, TankClient tc, boolean good) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tc = tc;
		this.good = good;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		this.move(g);
	}

	/**
	 * 子弹移动，并做出界判断
	 */
	public void move(Graphics g) {
		switch(direction) {
		case U :
			g.drawImage(bImgs.get("U"), x, y, null);
			y -= STEP;
			break;
		case D :
			g.drawImage(bImgs.get("D"), x, y, null);
			y += STEP;
			break;
		case L :
			g.drawImage(bImgs.get("L"), x, y, null);
			x -= STEP;
			break;
		case R :
			g.drawImage(bImgs.get("R"), x, y, null);
			x += STEP;
			break;
		case LU :
			g.drawImage(bImgs.get("LU"), x, y, null);
			x -= STEP;
			y -= STEP;
			break;
		case RU :
			g.drawImage(bImgs.get("RU"), x, y, null);
			x += STEP;
			y -= STEP;
			break;
		case LD :
			g.drawImage(bImgs.get("LD"), x, y, null);
			x -= STEP;
			y += STEP;
			break;
		case RD :
			g.drawImage(bImgs.get("RD"), x, y, null);
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
			tc.addEnmeys(Integer.parseInt(PropMgrs.getProps("reAdd")));
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
