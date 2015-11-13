package figure;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import transform.Transform;
import transform.Vec2;

public class Diamond extends Figure {
	private int width;
	public Diamond(int w, int l) {
		super(l);
		width = w;
	}

	@Override
	public synchronized void draw(Graphics g, Transform t) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform ord = g2d.getTransform();
		g2d.setTransform(t.getAffineTransform());
		
		float l = length;// * t.scaling.y;
//		Vec2 p1 = t.position;
//		Vec2 p2 = Vec2.add(t.position, new Vec2(width * t.scaling.x / 2, -l / 2));//Vec2.mul(offset2, t.scaling));
//		Vec2 p3 = Vec2.add(t.position, new Vec2(0,-l));
//		Vec2 p4 = Vec2.add(t.position, new Vec2(-width * t.scaling.x / 2, -l / 2));//Vec2.mul(offset3, t.scaling));
		Vec2 p1 = new Vec2(0,0);
		Vec2 p2 = new Vec2(width / 2, -l / 2);
		Vec2 p3 = new Vec2(0, -l);
		Vec2 p4 = new Vec2(-width / 2, -l / 2);
		int[] xPoints = new int[]{(int)p1.x,(int)p2.x,(int)p3.x, (int)p4.x};
		int[] yPoints = new int[]{(int)p1.y,(int)p2.y,(int)p3.y, (int)p4.y};
		g2d.drawPolygon(
				xPoints,
				yPoints,
				4);
		g2d.setTransform(ord);

	}

	@Override
	protected Path2D getPath(Transform t) {
		Path2D res = new Path2D.Float();
		res.moveTo(0, 0);
		res.lineTo(-width / 2, -length / 2);
		res.lineTo(0, -length);
		res.lineTo(width / 2, -length / 2);
		res.closePath();
		res.transform(t.getAffineTransform());
		return res;
	}

}
