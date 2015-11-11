package fractal;

import transform.Transform;

public class FractalRules {
	public Transform[] elems;
	public FractalRules(Transform... transforms) {
		elems = new Transform[transforms.length];
		int i = 0;
		for(Transform t : transforms){
			elems[i++] = t;
		}
	}
	public int getLength(){
		return elems.length;
	}
	public FractalRules clone(){
		Transform[] newElem = new Transform[elems.length];
		for(int i = 0; i < newElem.length; i++){
			newElem[i] = elems[i].clone();
		}
		return new FractalRules(newElem);
	}
}
