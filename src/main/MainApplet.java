package main;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import figure.*;
import fractal.*;
import transform.*;

public class MainApplet extends Applet{
	Fractal2 fractal;

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
		Figure tri = new Triangle((int)(200 / Math.sqrt(3) * 2),200);
		Figure tri2 = new Triangle(50,200,true);
		
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
		Figure arrow = new Arrow(200);
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
				tri2,circle,
				Color.ORANGE, Color.MAGENTA,
				true);
		flower.generate(4);
		Fractal tf = new Fractal(
				new Transform(new Vec2(0,0),0,0.7f),
				tforce,
				tri,
				false);
		tf.generate(10);
		FractalRules nCircle = new FractalRules(
				new Transform(new Vec2(0,-0.05f),0,0.9f));
		Figure dragonBase = new Triangle(200, 100, true);
		FractalRules dragon = new FractalRules(
				new Transform(new Vec2(-1f,0),45,1.4142f * 0.5f),
				new Transform(new Vec2(0,-1f),135, 1.4142f * 0.5f)
				);
		FractalRules dragon2 = new FractalRules(
				new Transform(new Vec2(0,0),90,0.5f),
				new Transform(new Vec2(0.5f,-0.5f),180,0.5f),
				new Transform(new Vec2(0.5f,-0.5f),-90,0.5f),
				new Transform(new Vec2(0,-1f),180,0.5f)
				);
		FractalRules toge = new FractalRules(
				new Transform(new Vec2(0,0),0,0.333f),
				new Transform(new Vec2(0,-0.333f),-60,0.333f),
				new Transform(new Vec2(-0.333f * 0.866f, -0.333f - 0.1666f),60,0.333f),
				new Transform(new Vec2(0,-0.333f - 0.1666f * 2),0,0.333f)
				);
		FractalRules snow = new FractalRules(
				new Transform(new Vec2(0,0),180,0.3333f),
				new Transform(new Vec2(0.5f / 1.73f,-0.5f),60,0.3333f),
				new Transform(new Vec2(-0.5f / 1.73f,-0.5f),-60,0.3333f)
				);
		Fractal2 cohho = new Fractal2(
				new Transform(),
				toge,
				l,
				false
				);
		cohho.generate(2);
		FractalRules triangle = new FractalRules(
				new Transform(new Vec2(0,0),30,1),
				new Transform(new Vec2(0,0),-270,1),
				new Transform(new Vec2(0.5f,-0.861f),150,1)
				);
		fractal = new Fractal2(
				new Transform(new Vec2(500,900),0,1f),
				dragon2,
				l,
				false
				);
//		fractal = new Fractal(
//				new Transform(new Vec2(500,900),0,1f),
//				standardTree,
//				rect,oval,
//				new Color(192,64,0),new Color(32,192,32),
//				true
//				);
//		fractal = new Fractal(
//				new Transform(new Vec2(500,900),0,1f),
//				tforce,
//				tri,face,
//				false
//				);
//		fractal = new Fractal(
//				new Transform(new Vec2(500,900),0,1f),
//				nCircle,
//				circle,face,
//				Color.RED,Color.BLACK,
//				true
//				);
		LocalDateTime t1 = LocalDateTime.now();
		fractal.generate(8);
		LocalDateTime t2 = LocalDateTime.now();
		System.out.println("generated : " + Duration.between(t1, t2));
		
		this.addMouseListener(new MouseAdapter(){
			private Vec2 prev;
			public void mousePressed(MouseEvent e){
				if(e.isControlDown()){
					if(e.isShiftDown()){
						toImage();
					}
					else
						screenShot();
				}
				else{
					prev = new Vec2(e.getX(),e.getY());
					if(e.isShiftDown())
						fractal.generate();
				}
			}
			public void mouseReleased(MouseEvent e){
				Vec2 move = new Vec2(e.getX() - prev.x,e.getY() - prev.y);
				fractal.setRootPosition(move);
				repaint();
				area = fractal.getBounds();
			}
		});
		this.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent me) {
				if(me.isControlDown()){
					float angle = -2 * me.getWheelRotation();
					fractal.rotate(angle, new Vec2(me.getX(), me.getY()));//fractal.getRootTransform().position);
				}else{
					float amount = 1 - me.getWheelRotation() * 0.5f;
					fractal.scaling(amount, new Vec2(me.getX(), me.getY()));					
				}
				repaint();
				area = fractal.getBounds();
			}
		});
		area = fractal.getBounds();
	}
	Rectangle area;
	public void paint(Graphics g){
		LocalDateTime t1 = LocalDateTime.now();
		fractal.draw(g);
		//FractalElem.await();
		LocalDateTime t2 = LocalDateTime.now();
		System.out.println("drawed : " + Duration.between(t1, t2));
		Color ord = g.getColor();
		g.setColor(Color.RED);
		g.drawRect(area.x, area.y, area.width, area.height);
		g.setColor(Color.BLACK);
		g.drawString(area.width + " * " + area.height, 0, 20);
		g.setColor(ord);
	}
	private void toImage(){
		Rectangle r = fractal.getBounds();
		java.awt.image.BufferedImage ss = new java.awt.image.BufferedImage(
				r.width,
				r.height,
				java.awt.image.BufferedImage.TYPE_3BYTE_BGR);
		
		Transform t = fractal.getRootTransform();
		t.position = Vec2.sub(t.position, new Vec2(r.x, r.y));
		
		Graphics g = ss.getGraphics();
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0,0,r.width,r.height);
		g.setColor(java.awt.Color.BLACK);
		fractal.draw(g, t);
		String timeStamp = System.currentTimeMillis() + "";
		try {
			String dirname = "C:\\Users\\kitch_000\\Pictures\\fractal";
			File dir = new File(dirname);
			if(!dir.exists())
				dir.mkdir();
			StringBuilder sb = new StringBuilder();
			sb.append(dirname).append("\\img").append(timeStamp).append(".png");
			File file = new File(sb.toString());
			file.createNewFile();
			javax.imageio.ImageIO.write(ss,"PNG",file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("saved!!");		
	}
	private void screenShot(){
		java.awt.image.BufferedImage ss = new java.awt.image.BufferedImage(
				this.getWidth(),
				this.getHeight(),
				java.awt.image.BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics g = ss.getGraphics();
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(java.awt.Color.BLACK);
		fractal.draw(g);
		String timeStamp = System.currentTimeMillis() + "";
		try {
			String dirname = "C:\\Users\\kitch_000\\Pictures\\fractal";
			File dir = new File(dirname);
			if(!dir.exists())
				dir.mkdir();
			StringBuilder sb = new StringBuilder();
			sb.append(dirname).append("\\img").append(timeStamp).append(".png");
			File file = new File(sb.toString());
			file.createNewFile();
			javax.imageio.ImageIO.write(ss,"PNG",file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("saved");
	}
}
