package figure;

import java.awt.Component;

import transform.Transform;

public abstract class Figure implements Drawable{
	public final int length;
	public Figure(int l){
		length = l;
	}
	
	public static Component component;
	public abstract void draw(Transform t);
}
