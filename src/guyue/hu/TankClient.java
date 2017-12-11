package guyue.hu;

import java.awt.*;
import java.awt.event.*;

/**
 * GAME����
 * 
 * @author hgb22613
 *
 */
public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final int PCELL = 50;
	private Tank myTank = new Tank(100, 150);	
	private boolean flag = true;
	private Image image;
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launch();
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
		new Thread(new RePnt()).start();
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		myTank.draw(g);
	}
	
	/**
	 * ˫��������˸�����д��
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
