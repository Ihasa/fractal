package figure;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import transform.*;

public class Triangle extends Figure {
	private int width;
	private boolean reverse;
	public Triangle(int width, int height, boolean rev){
		super(height);
		this.width = width;
		reverse = rev;
	}
	public Triangle(int width,int height){
		this(width,height,false);
	}
	
	@Override
	public synchronized void draw(Graphics g, Transform t) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform ord = g2d.getTransform();
		g2d.setTransform(t.getAffineTransform());
		
		float y1 = -length;// * t.scaling.y;
		float y2 = 0;
		if(reverse){
			float tmp = y1;
			y1 = y2;
			y2 = tmp;
		}
//		Vec2 p1 = Vec2.add(t.position, new Vec2(0,y1));
//		Vec2 p2 = Vec2.add(t.position, new Vec2(width * t.scaling.x / 2, y2));//Vec2.mul(offset2, t.scaling));
//		Vec2 p3 = Vec2.add(t.position, new Vec2(-width * t.scaling.x / 2, y2));//Vec2.mul(offset3, t.scaling));
		Vec2 p1 = new Vec2(0,y1);
		Vec2 p2 = new Vec2(width / 2, y2);
		Vec2 p3 = new Vec2(-width / 2, y2);
		
		int[] xPoints = new int[]{(int)p1.x,(int)p2.x,(int)p3.x};
		int[] yPoints = new int[]{(int)p1.y,(int)p2.y,(int)p3.y};
		g2d.drawPolygon(
				xPoints,
				yPoints,
				3);
		g2d.setTransform(ord);
	}
	@Override
	protected Path2D getPath(Transform t) {
		float y1 = -length;// * t.scaling.y;
		float y2 = 0;
		if(reverse){
			float tmp = y1;
			y1 = y2;
			y2 = tmp;
		}
//		Vec2 p1 = new Vec2(0,y1);
//		Vec2 p2 = new Vec2(width / 2, y2);
//		Vec2 p3 = new Vec2(-width / 2, y2);
		Path2D res = new Path2D.Float();
		res.moveTo(0, y1);
		res.lineTo(width / 2, y2);
		res.lineTo(-width / 2, y2);
		res.closePath();
		res.transform(t.getAffineTransform());
		return res;
	}

}
