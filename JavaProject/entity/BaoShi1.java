package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import gamejframe.GameJFrame;

public class BaoShi1 extends BaoShi{

		protected int x,y;
		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public static final int WIDTH = 20;
		public static final int HEIGHT = 20;
		
		protected BufferedImage image;
		
		protected BufferedImage image1,image2;
		protected BufferedImage[] i;
		
	    protected int count = 50;
		
		Random rd = new Random();
	

	   int index = 0;

	   public void step(){
		   image = i[index++/50%i.length];
		   y += 7; 
	   }

       public BaoShi1(){
   		
		   initBaoShi();	
		   i = new BufferedImage[]{image1,image2};

		   x = rd.nextInt(580);
		   y = -rd.nextInt(580)-100-GameJFrame.HEIGHT;
	  }
	  protected void initBaoShi() {
		  try {
		 	   image1 = ImageIO.read(new File("image/c2.png"));
			   image2 = ImageIO.read(new File("image/c0.png"));
		      } catch (Exception e) {
	      e.printStackTrace();
	     }
	  }

	  public void paintBaoShi(Graphics g){
			
			g.drawImage(image,x,y,WIDTH,HEIGHT,null );						
	  }

	  public int getCount()
	  {
		return count;
		  
	  }
}

