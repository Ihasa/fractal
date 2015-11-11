package transform;

public class Scaling {
	public float x;
	public float y;
	public Scaling(float s){
		this(s,s);
	}
	public Scaling(float sx, float sy){
		x = sx;
		y = sy;
	}
	public static Scaling add(Scaling s1, Scaling s2){
		return new Scaling(
				s1.x * s2.x,
				s1.y * s2.y
				);
	}
	public Scaling clone(){
		return new Scaling(x,y);
	}
}
