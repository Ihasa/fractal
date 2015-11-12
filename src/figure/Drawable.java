package figure;

import java.awt.Graphics;
import transform.Transform;

public interface Drawable {
	public void draw(Graphics g, Transform t);
}
