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
		AffineTransform translate = AffineTransform.getTranslateInstance(position.x, position.y);
		AffineTransform translateI = AffineTransform.getTranslateInstance(-position.x, -position.y);
		AffineTransform scale = AffineTransform.getScaleInstance(scaling.x, scaling.y);
		AffineTransform rotate = AffineTransform.getRotateInstance(Math.toRadians(rotation), position.x, position.y);
//		AffineTransform res = new AffineTransform();
//		res.concatenate(scaled);
//		res.concatenate(rotated);
		rotate.concatenate(translate);
		rotate.concatenate(scale);
		//rotate.concatenate(translateI);
//		AffineTransform translate2 = AffineTransform.getTranslateInstance(position.x,position.y);
//		translate2.concatenate(rotate);
		return rotate;
	}
	
	public Transform clone(){
		return new Transform(position.clone(),rotation,scaling.clone());
	}
}
