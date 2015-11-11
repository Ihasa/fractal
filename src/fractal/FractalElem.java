package fractal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import figure.Figure;
import transform.*;

public class FractalElem {
	public Transform absTransform;
	private FractalRules rules;
	public List<FractalElem> children;
	
	private static ExecutorService pool;
	private static List<Future<?>> futures = new ArrayList<Future<?>>();
	
	private int depth;
	private int childs;
	
	static {
		pool = Executors.newSingleThreadExecutor();//newWorkStealingPool();//Executors.newFixedThreadPool(cores);
	}
	public FractalElem(Transform t, FractalRules inirules, int depth) {
		absTransform = t;
		rules = inirules;
		this.depth = depth;
		children = new ArrayList<FractalElem>();
	}
	
	public int createChild(){
		for(Transform rel : rules.elems){
			Transform childTransform = getChildTransform(rel);
			children.add(new FractalElem(childTransform, rules, depth + 1));
		}
		return children.size();
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
	public void createChild(int depth){
		if(depth == 1){
			createChild();
			childs = children.size();
		} else if(depth > 0){
			createChild();
			childs = children.size();
			depth--;
			for(FractalElem child : children){
				child.createChild(depth);
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
	public void draw(Figure base, figure.Drawable end){
		if(hasChild()){
			base.draw(absTransform);

			if(isHeavyJob()){
				//スレッドプール形式
				for(int i = 0; i < children.size(); i++){
					FractalElem e = children.get(i);
					futures.add(pool.submit(()->{
						e.draw(base, end);
					}));
//					pool.execute(()->{
//						e.draw(base, end);
//					});
				}
			}else{
				//逐次実行形式
				for(FractalElem child : children){
					child.draw(base, end);
				}
			}
			//いちいちスレッド作る形式
//			Thread[] th = new Thread[children.size()];
//			for(int i = 0; i < children.size(); i++){
//				FractalElem e = children.get(i);
//				th[i] = new Thread(()->{
//					e.draw(base, end);
//				});
//				th[i].start();
//			}
//			try{
//			for(int i = 0; i < children.size(); i++){
//				th[i].join();
//			}
//			}catch(Exception e){}			
		}else{
			end.draw(absTransform);
		}
	}
	private boolean isHeavyJob(){
		return rules.getLength() > 1 && depth == 5;
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
}
