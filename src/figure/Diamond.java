package figure;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import transform.Transform;
import transform.Vec2;

public class Diamond extends Figure {
	private int width;
	public Diamond(int w, int l) {
		super(l);
		width = w;
	}

	@Override
	public void draw(Transform t) {
		Graphics2D g2d = (Graphics2D)component.getGraphics();
		AffineTransform ord = g2d.getTransform();
		g2d.setTransform(t.getAffineTransform());
		
		float l = length * t.scaling.y;
		Vec2 p1 = t.position;
		Vec2 p2 = Vec2.add(t.position, new Vec2(width * t.scaling.x / 2, -l / 2));//Vec2.mul(offset2, t.scaling));
		Vec2 p3 = Vec2.add(t.position, new Vec2(0,-l));
		Vec2 p4 = Vec2.add(t.position, new Vec2(-width * t.scaling.x / 2, -l / 2));//Vec2.mul(offset3, t.scaling));
		int[] xPoints = new int[]{(int)p1.x,(int)p2.x,(int)p3.x, (int)p4.x};
		int[] yPoints = new int[]{(int)p1.y,(int)p2.y,(int)p3.y, (int)p4.y};
		g2d.drawPolygon(
				xPoints,
				yPoints,
				4);
		g2d.setTransform(ord);

	}

}
