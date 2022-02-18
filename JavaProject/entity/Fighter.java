package entity;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
public class Fighter {
	
	// size
	public static final int WIDTH = 80;
	public static final int HEIGHT = 80;
	// move limit
	private int x_gun = 270;
	private int y_gun = 650;
	// original picture
	private Image image = new ImageIcon("image/fighter.png").getImage();
	// flags
	private boolean q = false;
	private boolean isOver = false;
	// fire move picture
	private Image image2 = new ImageIcon("image/fighter2.png").getImage();
	// boom picture
	private Image boomImage = new ImageIcon("image/boom.gif").getImage();	
	// game over picture
	private Image gameOverImage = new ImageIcon("image/gameover.png").getImage();	
	
	private int hp = 200;
	
	private boolean r = false;

	private int count;

	public  int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public void addCount(int count)
	{
		this.count += count;
	}

	public  int getX_gun() {
		return x_gun;
	}
	public  void setX_gun(int x_gun) {
		this.x_gun = x_gun;
	}
	public  int getY_gun() {
		return y_gun;
	}
	public  void setY_gun(int y_gun) {
		this.y_gun = y_gun;
	}
	public boolean isQ() {
		return q;
	}
	public void setQ(boolean q) {
		this.q = q;
	}
	public boolean isOver() {
		return isOver;
	}
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

	public boolean isR() {
		return r;
	}

	public void setR(boolean r) {
		this.r = r;
	}

	public int getHP() {
		return hp;
	}


	public void setHP(int hp) {
		this.hp = hp;
	}

	public Image getSimage() {
		return boomImage;
	}

	public void setSimage(Image simage) {
		this.boomImage = simage;
	}

	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
  	this.image = image;
	}

    public void paintFighter(Graphics g) {
		g.drawImage(image,x_gun,y_gun,80,80,null);
		
	}
	public void paintBoom(Graphics g) {	
		g.drawImage(boomImage,x_gun,y_gun,80,80,null);
	}
	public void paintfire(Graphics g) {
		g.drawImage(image2,x_gun,y_gun+10,80,80,null);
	}
	public void paintGameOver (Graphics g) {	
		g.drawImage(gameOverImage,100,200,400,300,null);
	}
	
}

