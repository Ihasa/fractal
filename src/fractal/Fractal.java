package fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import figure.Drawable;
import figure.Figure;
import transform.Scaling;
import transform.Transform;
import transform.Vec2;

public class Fractal implements Drawable{
	private FractalElem root;
	private Figure baseFigure;
	private Drawable endFigure;
	private int currentDepth;
	private boolean drawRoot;
	public Fractal(Transform rootTransform, FractalRules inirules, Figure base, Drawable end, boolean drawRoot) {
		baseFigure = base;
		endFigure = end;
		this.drawRoot = drawRoot;

		FractalRules rules = inirules.clone();
		for(Transform t : rules.elems){
			t.position = Vec2.mul(t.position, baseFigure.length);
		}
		if(!drawRoot)
			rootTransform.position = Vec2.add(rootTransform.position, new Vec2(0,baseFigure.length));
		root = new FractalElem(rootTransform, rules, 0, 1, rules.rootColor);
	}
	public Fractal(Transform rootTransform, FractalRules inirules, Figure base, boolean drawRoot){
		this(rootTransform,inirules,base,base,drawRoot);
	}

	public void generate(int depth){
		if(root.hasChild())
			root.clearChild();
		root.createChild(depth, depth);
		currentDepth = depth;
	}
	public void generate(){
		generate(currentDepth);
	}
	
	public synchronized void draw(Graphics g, Transform t){
//		root.absTransform = t;
//		root.clearChild();
//		generate(currentDepth);
		root.updateTransform(t);
		draw(g);
	}
	public void draw(Graphics g){
		if(drawRoot){
			root.draw(g, baseFigure, endFigure);
		}
		else{
			for(FractalElem e : root.children){
				e.draw(g, baseFigure,endFigure);
			}
		}
	}
	
	public void setRootPosition(Vec2 posi){
//		root.absTransform.position = Vec2.add(root.absTransform.position, posi);
//		root.clearChild();
//		generate(currentDepth);
		root.updateTransform(new Transform(posi,0,1f), new Vec2(0,0));
	}
	public void scaling(float amount, Vec2 pivot){
		Scaling newScaling = new Scaling(amount);
		root.updateTransform(new Transform(new Vec2(0,0),0,newScaling), pivot);
	}
	public void rotate(float angle, Vec2 pivot){
		root.updateTransform(new Transform(new Vec2(0,0),angle, 1), pivot);
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
