package fractal;

import java.awt.Color;

import transform.Transform;

public class FractalRules {
	public Transform[] elems;
	public Color rootColor;
	public Color endColor;
	public FractalRules(Color root, Color end, Transform... transforms) {
		elems = new Transform[transforms.length];
		int i = 0;
		for(Transform t : transforms){
			elems[i++] = t;
		}
		rootColor = root;
		endColor = end;
	}
	public FractalRules(Transform... transforms){
		this(Color.GRAY,Color.GRAY,transforms);
	}
	public int getLength(){
		return elems.length;
	}
	public FractalRules clone(){
		Transform[] newElem = new Transform[elems.length];
		for(int i = 0; i < newElem.length; i++){
			newElem[i] = elems[i].clone();
		}
		return new FractalRules(rootColor, endColor, newElem);
	}
}
