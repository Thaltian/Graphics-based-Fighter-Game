package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import gamejframe.GameJFrame;

public class BaoShi3 extends BaoShi{
	
		protected int x,y;
		public int getX() {
			return x;
		}


		public void setX(int x) {
			this.x = x;
		}


		public int getY() {
			return y;
		}
		
		public int getCount()
		{
			return count;  
		}

		public static final int WIDTH = 20;
		public static final int HEIGHT = 20;

		protected BufferedImage image;

		protected BufferedImage image1,image2;
		protected BufferedImage[] i;
		

	    protected int count = 10;
		
		Random tr = new Random();
	
	
	
	
	int index = 0;

	// move 
	public void step(){
		image = i[index++/50%i.length];

		y += 2; 
	}

	
	public BaoShi3(){
		initBaoShi();	
		i = new BufferedImage[]{image1,image2};

		x = tr.nextInt(580);
		y = -tr.nextInt(580)-100-GameJFrame.HEIGHT;
	}
	protected void initBaoShi() {
		try {
			image1 = ImageIO.read(new File("image/b2.png"));
			image2 = ImageIO.read(new File("image/b0.png"));
		} catch (Exception e) {
			e.printStackTrace();
	  }
	}
	
	public void paintBaoShi(Graphics g){		
			g.drawImage(image,x,y,WIDTH,HEIGHT,null );						
	}
		
		
}



