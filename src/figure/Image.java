package figure;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import transform.Transform;

public class Image extends Figure{
	private int width;
	private java.awt.Image img;
	public Image(int width, int height, String path) {
		super(height);
		this.width = width;
		img = loadImage(path);
	}
	
	private java.awt.Image loadImage(String name) {
		try {
		    java.net.URL url = getClass().getResource(name);
		    return component.createImage((java.awt.image.ImageProducer) url.getContent());
		} catch (Exception ex) {
		    return null;
		}
	}
	@Override
	public synchronized void draw(Graphics g, Transform t) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform ord = g2d.getTransform();
		
		g2d.setTransform(t.getAffineTransform());
		g2d.drawImage(img, -width / 2, -length, width, length, component);
		g2d.setTransform(ord);		
	}

	@Override
	protected Path2D getPath(Transform t) {
		Rectangle rect = new Rectangle(-width / 2, -length, width, length);
		return new Path2D.Float(rect, t.getAffineTransform());
	}

}
