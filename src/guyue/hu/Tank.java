package guyue.hu;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import java.util.*;

public class Tank {
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public static final int STEP = 5;
	private int x, y;
	private int preX, preY;
	private Direction direction = Direction.STOP;
	private Direction ptDirection = Direction.D;
	private boolean bU, bD, bL, bR;
	private TankClient tc;
	private boolean good;
	private boolean live = true;
	private static Random rnd = new Random();
	private int step = 10 + rnd.nextInt(5);
	private Direction[] dirs = Direction.values();
	private int life = 100;
	private Blood blood = new Blood();
	private static Image[] imgs;
	private static Map<String, Image> bImgs = new HashMap<String, Image>();
	
	static {
		try {
			imgs = new Image[] {
					ImageIO.read(Tank.class.getResource("/images/tankU.gif")),
					ImageIO.read(Tank.class.getResource("/images/tankD.gif")),
					ImageIO.read(Tank.class.getResource("/images/tankL.gif")),
					ImageIO.read(Tank.class.getResource("/images/tankR.gif")),
					ImageIO.read(Tank.class.getResource("/images/tankLU.gif")),
					ImageIO.read(Tank.class.getResource("/images/tankRU.gif")),
					ImageIO.read(Tank.class.getResource("/images/tankLD.gif")),
					ImageIO.read(Tank.class.getResource("/images/tankRD.gif")),
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
	
	public Tank(int x, int y, TankClient tc, boolean good) {
		this.x = x;
		preX = x;
		this.y = y;
		preY = y;
		this.tc = tc;
		this.good = good;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isGood() {
		return good;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void draw(Graphics g) {
		if(!live) return;
		preX = x;
		preY = y;
		if(good) blood.draw(g);
		this.drawPT(g);
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
		} else if(e.getKeyCode() == KeyEvent.VK_F2) {
			if(good) {
				live = true;
				life = 100;
			}
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
			if(live) {
				this.fire();
			}
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
		tc.getBullets().add(new Bullet(x+WIDTH/2-Bullet.SIZE/2, 
				y+HEIGHT/2-Bullet.SIZE/2, ptDirection, tc, good));
	}
	
	public void drawPT(Graphics g) {
		switch(ptDirection) {
		case U :
			g.drawImage(bImgs.get("U"), x, y, null);
			break;
		case D :
			g.drawImage(bImgs.get("D"), x, y, null);
			break;
		case L :
			g.drawImage(bImgs.get("L"), x, y, null);
			break;
		case R :
			g.drawImage(bImgs.get("R"), x, y, null);
			break;
		case LU :
			g.drawImage(bImgs.get("LU"), x, y, null);
			break;
		case RU :
			g.drawImage(bImgs.get("RU"), x, y, null);
			break;
		case LD :
			g.drawImage(bImgs.get("LD"), x, y, null);
			break;
		case RD :
			g.drawImage(bImgs.get("RD"), x, y, null);
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
					x = preX;
					y = preY;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 撞墙判定
	 * @param w 墙
	 * @return 撞墙了返回true，没撞到返回false
	 */
	public boolean hitWall(Wall w) {
		if(live && this.getRect().intersects(w.getRect())) {
			x = preX;
			y = preY;
			return true;
		}
		return false;
	}
	
	/**
	 * 吃食物判定
	 * @param f 食物
	 * @return 吃到了返回true,没吃到返回false
	 */
	public boolean eat(Food f) {
		if(good && live && this.getRect().intersects(f.getRect())) {
			life = 100;
			f.setLive(false);
			return true;
		}
		return false;
	}
	
	private class Blood {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 5);
			g.setColor(Color.GREEN);
			g.fillRect(x, y-10, WIDTH*life/100, 5);
			g.setColor(c);
		}
	}
}
