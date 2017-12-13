package guyue.hu;

import java.awt.*;

public class Food {
	public static final int SIZE = 10;
	public static final int ARCS = 2;
	private int x,y;
	private int[][] locs = { { 400, 100 }, { 405, 105 }, { 410, 110 }, { 415, 115 }, { 420, 120 }, { 425, 125 },
			{ 430, 130 }, { 435, 135 }, { 440, 140 }, { 440, 145 }, { 445, 145 }, { 450, 145 }, { 455, 150 },
			{ 460, 155 }, { 465, 160 } };
	private int step = 0;
	private boolean live = true;
	private int count = 0;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void draw(Graphics g) {
		count ++;
		if(count == 100) {
			count = 0;
			live = !live;
		}
		if(!live) return;
		x = locs[step][0];
		y = locs[step][1];
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.fillRoundRect(x, y, SIZE, SIZE, ARCS, ARCS);
		g.setColor(c);
		step ++;
		if(step == locs.length) {
			step = 0;
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, SIZE,SIZE);
	}
}
