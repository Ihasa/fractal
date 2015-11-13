package figure;

import java.awt.Graphics;
import java.awt.geom.Path2D;

import transform.Transform;

public class Line extends Figure {
	private int dir;
	public Line(int l, int d){
		super(l);
		dir = d;
	}
	public Line(int l){
		this(l,-90);
	}
	@Override
	public synchronized void draw(Graphics g,Transform t) {
		float x = t.position.x + (float)(t.scaling.x * length * Math.cos(Math.PI * (dir + t.rotation) / 180));
		float y = t.position.y + (float)(t.scaling.y * length * Math.sin(Math.PI * (dir + t.rotation) / 180));
		
		g.drawLine((int)t.position.x, (int)t.position.y, (int)x, (int)y);
	}
	@Override
	protected Path2D getPath(Transform t) {
		Path2D res = new Path2D.Float();
		res.moveTo(0,0);
		res.lineTo(0, -length);
		res.transform(t.getAffineTransform());
		return res;
	}
}
