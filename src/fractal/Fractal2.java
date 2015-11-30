package fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import figure.Drawable;
import figure.Figure;
import transform.Scaling;
import transform.Transform;
import transform.Vec2;

public class Fractal2 implements Drawable{
	private Figure baseFigure;
	private Drawable endFigure;
	private int currentDepth;
	private boolean drawRoot;
	private Color rootColor;
	private Color endColor;

	private List<FractalElem2> children;
	private FractalRules rules;
	private Transform rootTransform;
	
	public Fractal2(Transform rootTransform, FractalRules inirules, Figure base, Drawable end, Color rootColor, Color endColor, boolean drawRoot) {
		baseFigure = base;
		endFigure = end;
		this.rootColor = rootColor;
		this.endColor = endColor;
		this.drawRoot = drawRoot;

		rules = inirules.clone();
		for(Transform t : rules.elems){
			t.position = Vec2.mul(t.position, baseFigure.length);
		}
		if(!drawRoot)
			rootTransform.position = Vec2.add(rootTransform.position, new Vec2(0,baseFigure.length));
		this.rootTransform = rootTransform;
		
		children = new LinkedList<FractalElem2>();
	}
	public Fractal2(Transform rootTransform, FractalRules inirules, Figure base, boolean drawRoot){
		this(rootTransform,inirules,base,base,Color.GRAY,Color.GRAY,drawRoot);
	}
	public Fractal2(Transform rootTransform, FractalRules inirules, Figure base, Figure end, boolean drawRoot){
		this(rootTransform,inirules,base,end,Color.GRAY,Color.GRAY,drawRoot);
	}
	public Fractal2(Transform rootTransform, FractalRules inirules, Figure base,Color c, boolean drawRoot){
		this(rootTransform,inirules,base,base,c,c,drawRoot);
	}
	public Fractal2(Transform rootTransform, FractalRules inirules, Figure base, Figure end,Color c, boolean drawRoot){
		this(rootTransform,inirules,base,end,c,c,drawRoot);
	}
	public Fractal2(Transform rootTransform, FractalRules inirules, Figure base,Color rootColor,Color endColor, boolean drawRoot){
		this(rootTransform,inirules,base,base,rootColor,endColor,drawRoot);
	}//そういえばコンストラクタ派生機能ってない？
	
	public void generate(int depth){
//		if(root.hasChild())
//			root.clearChild();
//		root.createChild(depth, depth, rootColor, endColor);
		FractalElem2 root = new FractalElem2(rootTransform, rules, 0);//, depth, rootColor);
		for(FractalElem2 e : root.createChild(depth)){
			children.add(e);
		}
		if(drawRoot)
			children.add(root);
		currentDepth = depth;
	}
	public void generate(){
		generate(currentDepth);
	}
	
	public synchronized void draw(Graphics g, Transform t){
//		root.absTransform = t;
//		root.clearChild();
//		generate(currentDepth);

//		root.updateTransform(t);
		for(FractalElem2 e : children)
			e.updateTransform(t);
		draw(g);
	}
	public void draw(Graphics g){
		Color ord = g.getColor();
		for(FractalElem2 e : children){
			g.setColor(getChildColor(rootColor, endColor, e.depth, currentDepth));
			if(e.depth < currentDepth)
				e.draw(g,baseFigure);
			else
				e.draw(g, endFigure);
		}
		g.setColor(ord);
	}
	private Color getChildColor(Color root, Color end, int depth, int parentDepth){
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
	public void setRootPosition(Vec2 posi){
		
//		root.absTransform.position = Vec2.add(root.absTransform.position, posi);
//		root.clearChild();
//		generate(currentDepth);
//		root.updateTransform(new Transform(posi,0,1f), new Vec2(0,0));
		for(FractalElem2 e : children)
			e.updateTransform(new Transform(posi,0,1), new Vec2(0,0));
	}
	public void scaling(float amount, Vec2 pivot){
		Scaling newScaling = new Scaling(amount);
		for(FractalElem2 e : children)
			e.updateTransform(new Transform(new Vec2(0,0),0,newScaling), pivot);
//		root.updateTransform(new Transform(new Vec2(0,0),0,newScaling), pivot);
	}
	public void rotate(float angle, Vec2 pivot){
		for(FractalElem2 e : children)
			e.updateTransform(new Transform(new Vec2(0,0),angle,1), pivot);
//		root.updateTransform(new Transform(new Vec2(0,0),angle, 1), pivot);
	}
	public Rectangle getBounds(){
		//Rectangle r = base.getBounds(absTransform);//hasChild() ? base.getBounds() : end.getBounds();		
		//hasChild() ? base.getBounds() : end.getBounds();
		Vec2 min = null;
		Vec2 max = null;
		for(FractalElem2 e : children){
			Rectangle r2 = baseFigure.getBounds(e.absTransform);//e.getBounds(baseFigure, endFigure, min, max);
			if(min == null || max == null){
				min = new Vec2(r2.x, r2.y);
				max = new Vec2(r2.x + r2.width, r2.y + r2.height);
			}else
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
	
	public Transform getRootTransform(){
//		return root.absTransform.clone();
		return rootTransform;
	}
//	private Vec2 getPivot() {
//		Vec2 pivot = root.absTransform.position;
//		if(!drawRoot){
//			pivot = Vec2.add(
//					pivot, 
//					Vec2.rotate(
//							new Vec2(0,-baseFigure.length),
//							Math.toRadians(root.absTransform.rotation)
//					)
//			);
//		}
//		return pivot;
//	}
}
