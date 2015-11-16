package fractal;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
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

public class FractalElem {
	public Transform absTransform;
	private FractalRules rules;
	public List<FractalElem> children;
	
	private static ExecutorService pool;
	private static List<Future<?>> futures = new ArrayList<Future<?>>();
	
	private int depth;
	private int parentDepth;
	private Color color;
	
	private int childs;
	
	static {
		pool = Executors.newSingleThreadExecutor();//newWorkStealingPool();//Executors.newFixedThreadPool(cores);
	}
	public FractalElem(Transform t, FractalRules inirules, int depth, int parent, Color c) {
		absTransform = t;
		rules = inirules;
		this.depth = depth;
		parentDepth = parent;
		children = new ArrayList<FractalElem>();
		color = c;
	}
	
	public void createChild(Color root, Color end){
		Color childColor = getChildColor(root, end);
		for(Transform rel : rules.elems){
			Transform childTransform = getChildTransform(rel);
			children.add(new FractalElem(childTransform, rules, depth + 1, parentDepth, childColor));
		}
	}
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
	private Color getChildColor(Color root, Color end){
		float x = (float)(depth+1) / parentDepth;
		return new Color(
				lerp(root.getRed(),end.getRed(),x),
				lerp(root.getGreen(),end.getGreen(),x),
				lerp(root.getBlue(),end.getBlue(),x)
				);
	}
	private int lerp(int y1, int y2, float x){
		return (int)(y1 + (y2 - y1) * x);
	}
	public void createChild(int depth, int parentDepth, Color root, Color end){
		this.parentDepth = parentDepth;
		if(depth == 1){
			createChild(root, end);
			childs = children.size();
		} else if(depth > 0){
			createChild(root, end);
			childs = children.size();
			depth--;
			for(FractalElem child : children){
				child.createChild(depth, parentDepth, root, end);
				childs += child.childs;
			}
		}
	}
	public boolean hasChild(){
		return children.size() > 0;
	}
	public void clearChild(){
		if(hasChild()){
			for(FractalElem e : children){
				e.clearChild();
			}
			children.clear();
		}
	}
	
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
		for(FractalElem e : children){
			e.updateTransform(dt, pivot);
		}
	}
	
	public void updateTransform(Transform abs){
		absTransform = abs;
		for(int i = 0; i < children.size(); i++){
			FractalElem e = children.get(i);
			e.updateTransform(getChildTransform(rules.elems[i]));
		}
		
		//葉っぱにするとうまく書けない
//		Transform dt = new Transform(
//				Vec2.sub(abs.position, absTransform.position),
//				abs.rotation - absTransform.rotation,
//				new Scaling(abs.scaling.x / absTransform.scaling.x, abs.scaling.y / absTransform.scaling.y)
//				);
//		Vec2 pivot = absTransform.position.clone();
//		updateTransform(dt, pivot);
	}
	public void draw(Graphics g, Figure base, figure.Drawable end){
		Color ord = g.getColor();
		g.setColor(color);
		if(hasChild()){
			base.draw(g, absTransform);

			if(isHeavyJob()){
				//スレッドプール形式
				for(int i = 0; i < children.size(); i++){
					FractalElem e = children.get(i);
					futures.add(pool.submit(()->{
						e.draw(g, base, end);
					}));
//					pool.execute(()->{
//						e.draw(base, end);
//					});
				}
			}else{
				//逐次実行形式
				for(FractalElem child : children){
					child.draw(g, base, end);
				}
			}			
		}else{
			end.draw(g, absTransform);
		}
		g.setColor(ord);
	}
	private boolean isHeavyJob(){
		return false;//rules.getLength() > 1 && depth == 7;
	}
	public static void await(){
		for(Future<?> f : futures){
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		futures.clear();
	}
	
	public Rectangle getBounds(Figure base, Drawable end){
		Rectangle r = base.getBounds(absTransform);
		return getBounds(base, end, new Vec2(r.x,r.y),new Vec2(r.x + r.width, r.y + r.height));
	}
	public Rectangle getBounds(Figure base, Drawable end, Vec2 min, Vec2 max){
		//Rectangle r = base.getBounds(absTransform);//hasChild() ? base.getBounds() : end.getBounds();		
		Rectangle current = base.getBounds(absTransform);//hasChild() ? base.getBounds() : end.getBounds();		
//		if(current.x < min.x)
//			min.x = current.x;
//		else if(current.x > max.x)
//			max.x = current.x;
//		
//		if(current.y < min.y)
//			min.y = current.y;
//		else if(current.y > max.y)
//			max.y = current.y;
		updateMinMax(current, min, max);
		for(FractalElem e : children){
			Rectangle r2 = e.getBounds(base, end, min, max);
			updateMinMax(r2,min,max);
		}
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
