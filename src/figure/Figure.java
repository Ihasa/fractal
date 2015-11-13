package figure;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.geom.Path2D;

import transform.Transform;

public abstract class Figure implements Drawable{
	public final int length;
	public Figure(int l){
		length = l;
	}
	public Rectangle getBounds(Transform t){
		Path2D path = getPath(t);
		return path.getBounds();
	}
	protected abstract Path2D getPath(Transform t);
	public static Component component;
}
