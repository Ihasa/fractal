package main;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.time.Duration;
import java.time.LocalDateTime;

import figure.*;
import fractal.*;
import transform.*;

public class MainApplet extends Applet{
	Fractal fractal;

	public void init(){
		Figure.component = this;
		Figure square = new Rect(800,800);
		float rate = 0.48f;
		int x = (int)(800 * (1 - rate) * 0.5f);
		FractalRules windows = new FractalRules(
				new Transform(new Vec2(-x,-x),0,rate),
				new Transform(new Vec2(-x,x),0,rate),
				new Transform(new Vec2(x,-x),0,rate),
				new Transform(new Vec2(x,x),0,rate)
		);
		FractalRules suriken = new FractalRules(
				new Transform(new Vec2(0,0),4,0.99f)
				);
		FractalRules tower = new FractalRules(
				new Transform(new Vec2(0,-10),0,0.9f));
		FractalRules boxTree = new FractalRules(
				new Transform(new Vec2(-90,25),0,0.5f),
				new Transform(new Vec2(90,-25),0,0.5f),
				new Transform(new Vec2(0,-75),0,0.5f));
		Figure rect = new Rect(30, 240);
		FractalRules tree = new FractalRules(
				new Transform(new Vec2(-70,0),-45,0.6f),
				new Transform(new Vec2(70, -100), 45,0.6f),
				new Transform(new Vec2(0, -200), 0,0.9f)
		);
		FractalRules dia = new FractalRules(
				new Transform(new Vec2(0,200),45,0.35f),
				new Transform(new Vec2(0,-200),45,0.35f),
				new Transform(new Vec2(200,0),45,0.35f),
				new Transform(new Vec2(-200,0),45,0.35f)
				);
		Figure oval = new Oval(50,250);
		FractalRules test = new FractalRules(
				new Transform(new Vec2(0,0),0,0.9f)
				);
		FractalRules tforce = new FractalRules(
				new Transform(new Vec2(0,-0.5f),0,0.5f),
				new Transform(new Vec2((float)Math.sqrt(3) / 6,0),0,0.5f),
				new Transform(new Vec2((float)-Math.sqrt(3) / 6,0),0,0.5f)
				);
		Figure tri = new Triangle(200,(int)(Math.sqrt(3) * 100),true);
		Figure tri2 = new Triangle(50,200);
		
		FractalRules tforce2 = new FractalRules(
				new Transform(new Vec2(0,(int)(Math.sqrt(3) * 400)),180,0.5f));
		FractalRules tforce3 = new FractalRules(
				new Transform(new Vec2(0,(int)(Math.sqrt(3) * 400)),180,0.5f),
				new Transform(new Vec2(0,0),0,0.5f),
				new Transform(new Vec2(200,(int)(Math.sqrt(3) * 200)),0,0.5f),
				new Transform(new Vec2(-200,(int)(Math.sqrt(3) * 200)),0,0.5f)
				);
		FractalRules sida = new FractalRules(
				new Transform(new Vec2(100,-400),5,0.8f),
				new Transform(new Vec2(-400,400),-50,0.42f,0.56f),
				new Transform(new Vec2(400,450),60,0.25f,0.5f),
				new Transform(new Vec2(0,500),0,0.1f,0.3f)
				);
		FractalRules tree2 = new FractalRules(
				new Transform(new Vec2(0,(int)(-Math.sqrt(3) * 400)),0,0.75f,0.9f),
				new Transform(new Vec2(-600,-200),-40,0.5f,0.75f),
				new Transform(new Vec2(600,-100),30,0.4f,0.8f)
				);
		Line l = new Line(200, -90);
		FractalRules uzu = new FractalRules(
				new Transform(new Vec2(0,-1),20,0.99f));
		FractalRules standardTree = new FractalRules(
				new Transform(new Vec2(0,-0.5f),-45,0.5f),
				new Transform(new Vec2(0,-0.75f),45,0.5f),
				new Transform(new Vec2(0,-1),0,0.75f)
				);
		FractalRules triTree = new FractalRules(
				new Transform(new Vec2(0,-0.5f),-40,0.75f),
				new Transform(new Vec2(0,-0.5f),40,0.75f),
				new Transform(new Vec2(0,-0.5f),0,0.95f)
				);
		
		FractalRules mountain = new FractalRules(
				new Transform(new Vec2(-0.25f,0),0,0.8f),
				new Transform(new Vec2(0.25f,0),0,0.8f));

		
		FractalRules binTree = new FractalRules(
				new Transform(new Vec2(0,-1),-45,0.6f),
				new Transform(new Vec2(0,-1),45,0.6f));
		FractalRules binTree2 = new FractalRules(
				new Transform(new Vec2(0,-1),-20,0.75f),
				new Transform(new Vec2(0,-1),20,0.75f));
		FractalRules windows2 = new FractalRules(
				new Transform(new Vec2(-0.3f,0),0,0.4f),
				new Transform(new Vec2(-0.3f,-0.6f),0,0.4f),
				new Transform(new Vec2(0.3f,0),0,0.4f),
				new Transform(new Vec2(0.3f,-0.6f),0,0.4f)
				);
		Figure circle = new Oval(200,200);
		Image image = new Image(44, 285,"eda.png");
		Image face = new Image(200,200,"face.png");
		Diamond diamond = new Diamond(50,200);
		FractalRules tritri = new FractalRules(
				new Transform(new Vec2(0.4f,-0.5f),60,0.5f,0.75f),
				new Transform(new Vec2(-0.4f,-0.5f),-60,0.5f,0.75f));
		
		Fractal flower = new Fractal(
				new Transform(new Vec2(0,0),0,0.5f),
				binTree,//new FractalRules(new Transform(new Vec2(0,-0.1f),0,0.8f)),
				l,circle,
				true);
		flower.generate(3);
		fractal = new Fractal(
				new Transform(new Vec2(500,900),0,1f),
				binTree,
				image,
				true
				);
		LocalDateTime t1 = LocalDateTime.now();
		fractal.generate(15);
		LocalDateTime t2 = LocalDateTime.now();
		System.out.println("generated : " + Duration.between(t1, t2));
		
		this.addMouseListener(new MouseAdapter(){
			private Vec2 prev;
			public void mousePressed(MouseEvent e){
				prev = new Vec2(e.getX(),e.getY());
				if(e.isShiftDown())
					fractal.generate();
			}
			public void mouseReleased(MouseEvent e){
				Vec2 move = new Vec2(e.getX() - prev.x,e.getY() - prev.y);
				fractal.setRootPosition(move);
				repaint();
			}
		});
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent me) {
				float amount = 1 - me.getWheelRotation() * 0.5f;
				fractal.scaling(amount, new Vec2(me.getX(), me.getY()));
				repaint();
			}
		});

	}
	public void paint(Graphics g){
		LocalDateTime t1 = LocalDateTime.now();
		fractal.draw(g);
		FractalElem.await();
		LocalDateTime t2 = LocalDateTime.now();
		System.out.println("drawed : " + Duration.between(t1, t2));
	}
}
