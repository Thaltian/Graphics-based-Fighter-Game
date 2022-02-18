package entity;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class AirPlane {

	private int x;
	private int y;

	private BufferedImage image;

	private BufferedImage image1;	
	
	private BufferedImage image2;

	public int Hp=80;

	private int count = 25;

	public static final int Air_WIDTH = 100;//55
	public static final int Air_HEIGHT = 80;//40
	
	private int yspeed;
	Random rd = new Random();
	
	public AirPlane(){
		try {
			image = ImageIO.read(new File("image/airplane1.png"));
			image1 = ImageIO.read(new File("image/airplane2.png"));
			image2 = ImageIO.read(new File("image/boom.gif"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		x = rd.nextInt(450)+30;
		y = rd.nextInt(10)-Air_HEIGHT;
	
		yspeed = 1;
		
	}
	
	public void step(){
		y+=yspeed;
		
		if(this.getY() <= Air_HEIGHT )
		{
			this.setHp(80);		
		}
	}
		
	public void paintairplane(Graphics g){
		g.drawImage(image,x,y,Air_WIDTH,Air_HEIGHT,null);	
	}
	
	public void paintairplane1(Graphics g){
		g.drawImage(image1,x,y,Air_WIDTH,Air_HEIGHT,null);	
	}
	
	public void paintboom(Graphics g){
		g.drawImage(image2,x,y,Air_WIDTH,Air_HEIGHT,null);	
	}

	public  int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}



	public int getHp() {
		return Hp;
	}

	public void setHp(int hp) {
		Hp = hp;
	}

	public void Attacked(Attack2 attack)
	{
		Hp -= attack.getHurt();
	}
	
	public void Attacked(Attack attack)
	{
		Hp -= attack.getHurt();
	}
	public int getCount() {
		return count;
	}	
}
