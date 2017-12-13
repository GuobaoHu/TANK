package guyue.hu;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Boom {
	private int x, y;
	private static Image[] imgs;
	private TankClient tc;
	private int step = 0;
	private boolean live = true;
	
	static {
		try {
			imgs = new Image[] {
					ImageIO.read(Boom.class.getResource("/images/0.gif")),
					ImageIO.read(Boom.class.getResource("/images/1.gif")),
					ImageIO.read(Boom.class.getResource("/images/2.gif")),
					ImageIO.read(Boom.class.getResource("/images/3.gif")),
					ImageIO.read(Boom.class.getResource("/images/4.gif")),
					ImageIO.read(Boom.class.getResource("/images/5.gif")),
					ImageIO.read(Boom.class.getResource("/images/6.gif")),
					ImageIO.read(Boom.class.getResource("/images/7.gif")),
					ImageIO.read(Boom.class.getResource("/images/8.gif")),
					ImageIO.read(Boom.class.getResource("/images/9.gif")),
					ImageIO.read(Boom.class.getResource("/images/10.gif")),
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boom(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if(!live) {
			tc.getBooms().remove(this);
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		step ++;
		if(step == imgs.length) {
			step = 0;
			live = false;
		}
	}
}
