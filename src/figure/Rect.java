package figure;

import transform.Transform;
import java.awt.geom.AffineTransform;
import java.awt.*;

public class Rect extends Figure{
	private int width;
	public Rect(int w, int h) {
		super(h);
		width = w;
	}
	
	public synchronized void draw(Graphics g, Transform t){
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform ord = g2d.getTransform();
		g2d.setTransform(t.getAffineTransform());

		g2d.drawRect(- width / 2, - length, width, length);
		
		g2d.setTransform(ord);
	}
}
