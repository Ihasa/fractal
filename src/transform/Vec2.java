package transform;

public class Vec2 {
	public float x;
	public float y;
	public Vec2(float ix, float iy) {
		x = ix;
		y = iy;
	}
	public static Vec2 add(Vec2 v1, Vec2 v2){
		return new Vec2(
				v1.x + v2.x,
				v1.y + v2.y
		);
	}
	public static Vec2 sub(Vec2 v1, Vec2 v2){
		return new Vec2(
				v1.x - v2.x,
				v1.y - v2.y
				);
	}
	public static Vec2 mul(Vec2 v1, float f){
		return new Vec2(
				v1.x * f,
				v1.y * f
				);
	}
	public static Vec2 mul(Vec2 v1, Scaling sca){
		return new Vec2(
				v1.x * sca.x,
				v1.y * sca.y);
	}
	public static Vec2 mul(Vec2 v1, Scaling sca, Vec2 pivot){
		Vec2 v2 = Vec2.sub(v1, pivot);
		Vec2 v3 = Vec2.mul(v2, sca);
		return Vec2.add(v3, pivot);
	}
	public static Vec2 rotate(Vec2 v1, double rad){
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		return new Vec2(
			(float)(v1.x * cos - v1.y * sin),
			(float)(v1.x * sin + v1.y * cos)
		);
	}
	public static Vec2 rotate(Vec2 v1, double rad, Vec2 pivot){
		Vec2 v2 = Vec2.sub(v1, pivot);
		Vec2 v3 = Vec2.rotate(v2, rad);
		return Vec2.add(v3, pivot);
	}
	public Vec2 clone(){
		return new Vec2(x,y);
	}
}
