package figure;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import transform.Transform;

public class Arrow extends Figure{

	public Arrow(int l) {
		super(l);
	}

	@Override
	public void draw(Graphics g, Transform t) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform ord = g2d.getTransform();
		g2d.setTransform(t.getAffineTransform());

		g2d.drawLine(0, 0, 0, -length);
		g2d.drawLine(0, -length, -length / 16, -length * 7 / 8);
		g2d.drawLine(0, -length, length / 16, -length * 7 / 8);
		
		g2d.setTransform(ord);
	}

	@Override
	protected Path2D getPath(Transform t) {
		Path2D res = new Path2D.Float();
		res.moveTo(0, 0);
		res.lineTo(0, -length);
		res.lineTo(-length / 16, -length * 7 / 8);
		res.moveTo(0, -length);
		res.lineTo(length / 16, -length * 7 / 8);
		res.transform(t.getAffineTransform());
		return res;
	}

}
