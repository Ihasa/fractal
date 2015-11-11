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
	
	public void draw(Transform t){
		Graphics2D g2d = (Graphics2D)component.getGraphics();
		AffineTransform ord = g2d.getTransform();
		
		int w = (int)(width * t.scaling.x);
		int h = (int)(length * t.scaling.y);
		g2d.setTransform(t.getAffineTransform());
		g2d.drawRect((int)(t.position.x - w / 2), (int)(t.position.y - h), w, h);
		
		g2d.setTransform(ord);
	}
}
