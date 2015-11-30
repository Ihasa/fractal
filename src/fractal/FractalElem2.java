package fractal;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import figure.Drawable;
import figure.Figure;
import transform.*;
import java.awt.Color;

public class FractalElem2 {
	public Transform absTransform;
	private FractalRules rules;
	
	public final int depth;
	//private int parentDepth;
	//private Color color;
	
	private int childs;
	
	public FractalElem2(Transform t, FractalRules inirules, int depth/*, int parent, Color c*/) {
		absTransform = t;
		rules = inirules;
		this.depth = depth;
//		parentDepth = parent;
//		color = c;
	}
	
//	public void createChild(Color root, Color end){
//		Color childColor = getChildColor(root, end);
//		for(Transform rel : rules.elems){
//			Transform childTransform = getChildTransform(rel);
//			children.add(new FractalElem2(childTransform, rules, depth + 1, parentDepth, childColor));
//		}
//	}
	
	private Transform getChildTransform(Transform relTransform){
		Vec2 pos = Vec2.add(
				absTransform.position, 
				Vec2.rotate(
					Vec2.mul(relTransform.position, absTransform.scaling),
					Math.toRadians(absTransform.rotation)
				)
		);
		float rot = absTransform.rotation + relTransform.rotation;
		if(rot > 360)
			rot -= 360;
		else if(rot < 0){
			rot += 360;
		}
		Scaling sca = Scaling.add(absTransform.scaling, relTransform.scaling);
		return new Transform(pos,rot,sca);
	}
//	private Color getChildColor(Color root, Color end){
//		float x = (float)(depth+1) / parentDepth;
//		return new Color(
//				lerp(root.getRed(),end.getRed(),x),
//				lerp(root.getGreen(),end.getGreen(),x),
//				lerp(root.getBlue(),end.getBlue(),x)
//				);
//	}
//	private int lerp(int y1, int y2, float x){
//		return (int)(y1 + (y2 - y1) * x);
//	}
//	public void createChild(int depth, int parentDepth, Color root, Color end){
//		this.parentDepth = parentDepth;
//		if(depth == 1){
//			createChild(root, end);
//			childs = children.size();
//		} else if(depth > 0){
//			createChild(root, end);
//			childs = children.size();
//			depth--;
//			for(FractalElem2 child : children){
//				child.createChild(depth, parentDepth, root, end);
//				childs += child.childs;
//			}
//		}
//	}
	public List<FractalElem2> createChild(int depth){
		List<FractalElem2> res = new LinkedList<FractalElem2>();
		createChild(depth, res);
		return res;
	}
	private void createChild(int depth, List<FractalElem2> res){
		if(depth < 1)
			return;
		List<FractalElem2> children = new LinkedList<FractalElem2>();
//		Color childColor = getChildColor(root, end);
		for(Transform rel : rules.elems){
			Transform childTransform = getChildTransform(rel);
			children.add(new FractalElem2(childTransform, rules, this.depth + 1/*, parentDepth, childColor*/));
		}
		childs = children.size();
		for(FractalElem2 e : children){
			res.add(e);
			e.createChild(depth - 1, res);
			childs += e.childs;
		}
	}
//	public boolean hasChild(){
//		return this.depth < parentDepth;//children.size() > 0;
//	}
//	public void clearChild(){
//		if(hasChild()){
//			for(FractalElem2 e : children){
//				e.clearChild();
//			}
//			children.clear();
//		}
//	}
	
	public void updateTransform(Transform dt, Vec2 pivot){
		Vec2 newPosition = Vec2.add(
				Vec2.rotate(
						absTransform.position,
						Math.toRadians(dt.rotation),
						pivot),
				dt.position
		);
		absTransform = new Transform(
				Vec2.mul(newPosition, dt.scaling, pivot),
				absTransform.rotation + dt.rotation,
				Scaling.add(absTransform.scaling, dt.scaling)
				);
//		for(FractalElem2 e : children){
//			e.updateTransform(dt, pivot);//親への参照が必要
//		}
	}
	
	public void updateTransform(Transform abs){
		absTransform = abs;
//		for(int i = 0; i < children.size(); i++){
//			FractalElem2 e = children.get(i);
//			e.updateTransform(getChildTransform(rules.elems[i]));
//		}
		
		//葉っぱにするとうまく書けない
//		Transform dt = new Transform(
//				Vec2.sub(abs.position, absTransform.position),
//				abs.rotation - absTransform.rotation,
//				new Scaling(abs.scaling.x / absTransform.scaling.x, abs.scaling.y / absTransform.scaling.y)
//				);
//		Vec2 pivot = absTransform.position.clone();
//		updateTransform(dt, pivot);
	}
	public void draw(Graphics g, figure.Drawable base){
//		Color ord = g.getColor();
//		g.setColor(color);
		base.draw(g, absTransform);
//		if(hasChild()){
//			base.draw(g, absTransform);
//				//逐次実行形式
//			for(FractalElem2 child : children){
//				child.draw(g, base, end);
//			}
//		}else{
//			end.draw(g, absTransform);
//		}
//		g.setColor(ord);
	}
	
	public Rectangle getBounds(Figure base, Drawable end){
		Rectangle r = base.getBounds(absTransform);
		return getBounds(base, end, new Vec2(r.x,r.y),new Vec2(r.x + r.width, r.y + r.height));
	}
	public Rectangle getBounds(Figure base, Drawable end, Vec2 min, Vec2 max){
		//Rectangle r = base.getBounds(absTransform);//hasChild() ? base.getBounds() : end.getBounds();		
		Rectangle current = base.getBounds(absTransform);//hasChild() ? base.getBounds() : end.getBounds();		

		updateMinMax(current, min, max);
//		for(FractalElem2 e : children){
//			Rectangle r2 = e.getBounds(base, end, min, max);
//			updateMinMax(r2,min,max);
//		}
		return new Rectangle((int)min.x, (int)min.y, (int)(max.x - min.x), (int)(max.y - min.y));
	}
	private void updateMinMax(Rectangle current, Vec2 min, Vec2 max){
		if(current.x < min.x)
			min.x = current.x;
		else if(current.x + current.width > max.x)
			max.x = current.x + current.width;
		
		if(current.y < min.y)
			min.y = current.y;
		else if(current.y + current.height > max.y)
			max.y = current.y + current.height;
	}
}
