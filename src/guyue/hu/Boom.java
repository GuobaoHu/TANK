package guyue.hu;

import java.awt.*;

public class Boom {
	private int x, y;
	private int[] diameters = { 2, 5, 8, 10, 15, 20, 25, 30, 35, 40, 30, 20, 10, 2 };
	private TankClient tc;
	private int step = 0;
	private boolean live = true;
	
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
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, diameters[step], diameters[step]);
		g.setColor(c);
		step ++;
		if(step == diameters.length) {
			step = 0;
			live = false;
		}
	}
}
