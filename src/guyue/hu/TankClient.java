package guyue.hu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


/**
 * GAME界面
 * 
 * @author hgb22613
 *
 */
public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	/**
	 * 重画间隔(ms)
	 */
	public static final int PCELL = 50;
	private Tank myTank = new Tank(100, 150, this, true);	
	private boolean flag = true;
	private Image image;
	private List<Bullet> bullets = new ArrayList<Bullet>();
	private List<Tank> enemys = new ArrayList<Tank>();
	private static Random rnd = new Random();
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launch();
	}
	
	/**
	 * 增加敌人
	 */
	public void addEnmeys() {
		for(int i=0; i<10; i++) {
			Tank t = new Tank(rnd.nextInt(GAME_WIDTH-Tank.WIDTH), 
					30+rnd.nextInt(GAME_HEIGHT-Tank.HEIGHT-30), this, false);
			while(t.isOverlap(enemys)) {
				t = new Tank(rnd.nextInt(GAME_WIDTH-Tank.WIDTH), 
						30+rnd.nextInt(GAME_HEIGHT-Tank.HEIGHT-30), this, false);
			}
			enemys.add(t);
		}
	}
	
	public List<Tank> getEnemys() {
		return enemys;
	}

	public List<Bullet> getBullets() {
		return bullets;
	}

	public void launch() {
		this.setBounds(300, 300, GAME_WIDTH, GAME_HEIGHT);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		this.addEnmeys();
		new Thread(new RePnt()).start();
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		g.drawString("bullets count:" + bullets.size(), 10, 40);
		myTank.draw(g);
		 // 用for循环而不用Iterator，因为后者在循环过程中会锁定对象
		for(int i=0; i<bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.draw(g);
		}
		//绘制敌方坦克
		for(int i=0; i<enemys.size(); i++) {
			Tank t = enemys.get(i);
			t.draw(g);
		}
	}
	
	/**
	 * 双缓冲解决闪烁问题的写法
	 */
	public void update(Graphics g) {
		if(image == null) {
			image = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gD = image.getGraphics();
		Color c = gD.getColor();
		gD.setColor(Color.WHITE);
		gD.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gD.setColor(c);
		this.paint(gD);
		g.drawImage(image, 0, 0, null);
	}

	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			myTank.KeyPressed(e);
		}
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.KeyReleased(e);
		}
	}
	
	private class RePnt implements Runnable {
		@Override
		public void run() {
			try {
				while(flag) {
					repaint();
					Thread.sleep(PCELL);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
