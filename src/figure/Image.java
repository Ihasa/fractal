package figure;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

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
	public void draw(Transform t) {
		Graphics2D g2d = (Graphics2D)component.getGraphics();
		AffineTransform ord = g2d.getTransform();
		
		int w = (int)(width * t.scaling.x);
		int h = (int)(length * t.scaling.y);
		g2d.setTransform(t.getAffineTransform());
		g2d.drawImage(img, (int)(t.position.x - w / 2), (int)(t.position.y - h), w, h,component);
		g2d.setTransform(ord);		
	}

}
