package transform;

import java.awt.geom.AffineTransform;

public class Transform {
	public Vec2 position;
	public float rotation;
	//public float scaling;
	public Scaling scaling;
	public Transform(){
		this(new Vec2(0,0),0,1.0f);
	}
	public Transform(Vec2 iPos, float iRot, float iSca) {
		this(iPos,iRot,new Scaling(iSca));
	}
	public Transform(Vec2 iPos, float iRot, float iScaX, float iScaY){
		this(iPos,iRot,new Scaling(iScaX,iScaY));
	}
	public Transform(Vec2 iPos, float iRot, Scaling iSca){
		position = iPos;
		rotation = iRot;
		scaling = iSca;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("pos = ");
		sb.append("(" + position.x + ", " + position.y + ")");
		
		sb.append(", rot = ");
		sb.append(rotation);
		
		sb.append(", scaling = ");
		sb.append("(" + scaling.x + ", " + scaling.y + ")");
		sb.append('\n');
		return sb.toString();
	}

	public AffineTransform getAffineTransform() {
		AffineTransform translated = AffineTransform.getTranslateInstance(position.x, position.y);
		AffineTransform transleteI = AffineTransform.getTranslateInstance(-position.x, -position.y);
		AffineTransform scaled = AffineTransform.getScaleInstance(scaling.x, scaling.y);
		AffineTransform rotated = AffineTransform.getRotateInstance(Math.toRadians(rotation), position.x, position.y);
//		AffineTransform res = new AffineTransform();
//		res.concatenate(scaled);
//		res.concatenate(rotated);
		return rotated;
	}
	
	public Transform clone(){
		return new Transform(position.clone(),rotation,scaling.clone());
	}
}
