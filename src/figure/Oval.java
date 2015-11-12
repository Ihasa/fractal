package figure;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import transform.Transform;

public class Oval extends Figure{
	private int width;
	public Oval(int w, int h){
		super(h);
		width = w;
	}
	@Override
	public synchronized void draw(Graphics g, Transform t) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform ord = g2d.getTransform();
		g2d.setTransform(t.getAffineTransform());

		g2d.drawOval(- width / 2, - length, width, length);
		
		g2d.setTransform(ord);
	}	
}
