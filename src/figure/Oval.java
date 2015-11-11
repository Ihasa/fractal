package figure;

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
	public void draw(Transform t) {
		Graphics2D g2d = (Graphics2D)component.getGraphics();
		AffineTransform ord = g2d.getTransform();
		g2d.setTransform(t.getAffineTransform());
		int w = (int)(width * t.scaling.x);
		int h = (int)(length * t.scaling.y);
		g2d.drawOval((int)(t.position.x - w / 2), (int)(t.position.y - h), w, h);
		g2d.setTransform(ord);
	}
	
}
