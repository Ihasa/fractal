package main;

import java.applet.Applet;
import java.awt.Button;

import fractal.*;
import figure.*;
import transform.*;

public class FractalEditor extends Applet {
	private Fractal fractal;
	public void init(){
		fractal = new Fractal(
				new Transform(new Vec2(500,900),0,1),
				new FractalRules(),
				new Rect(10,100),
				false);
		
		Button generate = new Button("generate");
		generate.addActionListener( (e)->{
			fractal.generate(1);
		});
	}
}
