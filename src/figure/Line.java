package figure;

import java.awt.Graphics;

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
	public void draw(Graphics g,Transform t) {
		float x = t.position.x + (float)(t.scaling.x * length * Math.cos(Math.PI * (dir + t.rotation) / 180));
		float y = t.position.y + (float)(t.scaling.y * length * Math.sin(Math.PI * (dir + t.rotation) / 180));
		
		g.drawLine((int)t.position.x, (int)t.position.y, (int)x, (int)y);
	}

}
