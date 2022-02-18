package gamejframe;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import entity.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.io.*;

public class GameJPanel extends JPanel  implements KeyListener{

	private Image[] background;
	private AirPlane[] airplane;
	public int gameover = 0;
	Fighter fighter;
	public  int fighterScore = 0;
	public  int fighterHp = 200;
	public  String[] fighterString = {"Game is runing!","Collided! Collided!","Hit! Hit! Hit!","Game Over!"};
	public  String sysString = fighterString[0];
	Attack[] attack1;  // user's attack
	Attack attack;
	Attack2[] attack2; // enemy's attack
	// gem objects
    private BaoShi[] baoshi = new BaoShi[1];
    // pause flag
    private boolean pause_status = false;
    private boolean restart = false;
    // Host name or ip
    String host = "localhost";
    String userName;
    

    public GameJPanel() {
    	
    	// instance game object 
    	fighter = new Fighter();
    	attack = new Attack(fighter);
    	attack1 = new Attack[0];    	
    	attack2 = new Attack2[0];
    	
    	
        baoshi[0] = new BaoShi();
        airplane =new AirPlane[0];
    	background = new Image[2];
    	background[0] = new ImageIcon("image/background.png").getImage();
    	background[1]= new ImageIcon("image/background2.png").getImage();
}

    
    
    
    public void action() {
    	new Thread() {
    		@Override
    		public void run() {
    			super.run();
    			Socket socket = null;
				try {
					socket = new Socket(host, 8000);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			while(true) {
    				while (pause_status) {
    					try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
    				}
    
    				if (restart) {
    					fighter = new Fighter();
    			    	attack = new Attack(fighter);
    			    	attack1 = new Attack[0];    	
    			    	attack2 = new Attack2[0]; 
    			    	baoshi = new BaoShi[1];
    			        baoshi[0] = new BaoShi();
    			        airplane =new AirPlane[0];
    			    	background = new Image[2];
    			    	background[0] = new ImageIcon("image/background.png").getImage();
    			    	background[1]= new ImageIcon("image/background2.png").getImage();
    			    	set_restart(false);
    				}
    			    // move policy 
    				stepAction();
    				// airplane object generated
    				EnterAction();
    				// cross border processing
    				outEntity();
    				// collision and hit processing
    				wardAction();   				
    				// repaint
    				repaint();
    				fighterScore = fighter.getCount();
					fighterHp = fighter.getHP();
					
					// game over flag
    				if(fighter.getHP()<=0 && gameover >= 1) {
    					sysString = fighterString[3];
    					gameover = -1;
    					break;
    				}
    				// print score
    				if(index % 300 == 0) 
    				{
    					sysString = fighterString[0];
    					System.out.println("Score:" + fighter.getCount());
    					System.out.println("Hp: " + fighter.getHP());
    				}
    				try {
    					Thread.sleep(10);
    				}catch(InterruptedException e)
    				{
    					e.printStackTrace();
    				}
    			}
    			System.out.println("Game Over: " + fighter.getHP());
    			userName = JOptionPane.showInputDialog("Please input a user name:");
    			if (userName != null) {
	    			try {
	    		        // Establish connection with the server
	    		        
	
	    		        // Create an output stream to the server
	    		        ObjectOutputStream toServer =
	    		          new ObjectOutputStream(socket.getOutputStream());
	
	    		        // Get text field
	    		        String player_id = userName;
	    		        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	    		        Date dNow = new Date( );
	    		        String cur_time = "" + ft.format(dNow);
	    		        
	    		        int score = fighter.getCount();
	
	    		        // Create a Student object and send to the server
	    		        Network_trans_object s = new Network_trans_object(player_id, score, cur_time, 1);		        
	    		        toServer.writeObject(s);
	    		        //toServer.close();
	    		      }
	    		      catch (IOException ex) {
	    		        ex.printStackTrace();
	    		      }
    			
    			}
    			
    		}
    	}.start();
    }
        
    public void set_pause(boolean pause_status) {
    	this.pause_status = pause_status;
    }
    public void set_restart(boolean restart) {
    	this.restart = restart;
    }
   // paint method
    int a = 0;
    public void paint(Graphics g)
    {
    	
    	a++;
    	g.drawImage(background[0],0,a,GameJFrame.WIDTH, GameJFrame.HEIGHT,null);
    	g.drawImage(background[1],0,a-GameJFrame.HEIGHT,GameJFrame.WIDTH, GameJFrame.HEIGHT,null);
    	g.drawImage(background[1],0,a-2*GameJFrame.HEIGHT,GameJFrame.WIDTH, GameJFrame.HEIGHT,null);
    	g.drawImage(background[1],0,a-3*GameJFrame.HEIGHT,GameJFrame.WIDTH, GameJFrame.HEIGHT,null);
    	if(a == 3*GameJFrame.HEIGHT) {
    		a = 0;
    	}
    	fighter.paintFighter(g);
	    for(int i = 0;i<attack1.length;i++)
 	    {
 	    	attack1[i].paintAttack(g);
 	    }
    	
    	if(fighter.isQ()==true) {
    		fighter.paintfire(g);
    		fighter.setQ(false);
    	}

    	for (int i = 0; i < baoshi.length; i++) {
			baoshi[i].paintBaoShi(g);
		}
		
    	int count = 0;
    	for(int i = 0;i < airplane.length;i++) {
    		airplane[i].paintairplane(g);		
    	}
    	
    	// game over paint
    	if(fighter.isOver()==true) {
    		if(gameover >= 1) {
    			sysString = fighterString[3];
    			fighter.paintGameOver(g);
    		}
    		fighter.paintBoom(g);
    		Timer timer = new Timer();// instance new time object
            timer.schedule(new TimerTask() {
                public void run() {
                	fighter.setOver(false);
                    this.cancel();
                }
            }, 2000);// millisecond
 		
    	}
    	
    	// enemy airplane is attacked 
    	if(BeAttacked) {
    		sysString = fighterString[2];
			count++;
			airplane[selected].paintairplane1(g);
			Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                	BeAttacked=false;
                    this.cancel();
                }
            }, 300);// millisecond
            
            if(count>5) {
    			airplane[selected].paintboom(g);
    			Timer timer1 = new Timer();
                timer1.schedule(new TimerTask() {
                    public void run() {
                    	
                        this.cancel();
                    }
                }, 300);// millisecond
                count=0;
    		}    
                     
		}
    	for(int i = 0;i < attack2.length;i++) {
    		attack2[i].paintAttack2(g);
    	}
	}
    
    //new airplane generated
	int index = 0;
    protected void EnterAction() {
    	index++;
		if(index%300==0) {
			// instance enemy airplane every 300 pixel
			AirPlane a1 = new AirPlane();
			// expand capacity
			airplane = Arrays.copyOf(airplane,airplane.length+1);
			// add object
			airplane[airplane.length-1] = a1;
		}
		
		for(int i=0 ;i < airplane.length ; i++ )
		{
			if(index%80==0) {
				// instance new enemy airplane bullet
				Attack2 b2 = new Attack2(airplane[i]); 
				// expand capacity
				attack2 = Arrays.copyOf(attack2,attack2.length+1);
				// add object
				attack2[attack2.length-1] = b2;
			}
		}
		
		// gem generated
		if(index%150 == 0)
		{
			BaoShi b = new BaoShi();
			baoshi = Arrays.copyOf(baoshi, baoshi.length+1);
		    baoshi[baoshi.length-1] = b;
			
		}
		
		// user's bullet
		if(index % 20 == 0) {
		Attack b1 = new Attack(fighter);
	    attack1 = Arrays.copyOf(attack1,attack1.length+1);
	    attack1[attack1.length-1] = b1;
	}
	
	// different gem generated
	if(index%800 == 0)
		{	
			BaoShi a = new BaoShi1();
			baoshi = Arrays.copyOf(baoshi, baoshi.length+1);
		    baoshi[baoshi.length-1] = a;
		}
		
		if(index%150 == 0)
		{
			BaoShi b = new BaoShi2();
			baoshi = Arrays.copyOf(baoshi, baoshi.length+1);
		    baoshi[baoshi.length-1] = b;
		}
		
		if(index%120 == 0)
		{
			BaoShi c = new BaoShi3();
			baoshi = Arrays.copyOf(baoshi, baoshi.length+1);
		    baoshi[baoshi.length-1] = c;
		}
		
     }

     // cross border processing
     protected void outEntity() {
    	// gem out of border
    	for (int i = 0; i < baoshi.length; i++) {
			if(baoshi[i].getY() > (BaoShi.HEIGHT + GameJFrame.HEIGHT)){
				BaoShi g = baoshi[i];
				baoshi[i] = baoshi[baoshi.length-1];
				baoshi[baoshi.length-1]=g;
				baoshi = Arrays.copyOf(baoshi, baoshi.length-1);
			}
		}
    	
    	// enemy bullet out of border
    	for (int i = 0; i < attack2.length; i++) {
			if(attack2[i].getY() > 800){
				Attack2 b3 = attack2[i];
				attack2[i] = attack2[attack2.length-1];
				attack2[attack2.length-1]=b3;
				attack2 = Arrays.copyOf(attack2, attack2.length-1);
			}
		}
    	
   	
    	// enemy airplane out of border
    	for (int i = 0; i < airplane.length; i++) {
			if(airplane[i].getY() > AirPlane.Air_HEIGHT +800){
				AirPlane b4 = airplane[i];
				airplane[i] = airplane[airplane.length-1];
				airplane[airplane.length-1]=b4;
				airplane = Arrays.copyOf(airplane, airplane.length-1);
			}
		}
    	// user's bullet out of border
    	for (int i = 0; i < attack1.length; i++) {
			if(attack1[i].getY() < Attack.b){
				Attack b5 = attack1[i];
				attack1[i] = attack1[attack1.length-1];
				attack1[attack1.length-1]=b5;
				attack1 = Arrays.copyOf(attack1, attack1.length-1);
			}
		}
    	
    	
    	// enemy was destroyed and disappeared
    	for (int i = 0; i < airplane.length; i++) {
			if(airplane[i].getHp()<0){
				fighter.addCount(airplane[i].getCount());
				AirPlane b4 = airplane[i];
				airplane[i] = airplane[airplane.length-1];
				airplane[airplane.length-1]=b4;
				airplane = Arrays.copyOf(airplane, airplane.length-1);
			}
		}
	 }
     
        // enemy was attacked flag
        boolean BeAttacked = false;
        // selected attacked enemy 
	    int selected;
        public void wardAction() {
    		
    		
    		for(int i = 0;i < baoshi.length;i++)
    		{
    			boolean f = fighter.getX_gun() <( baoshi[i].getX() + BaoShi.WIDTH) && (fighter.getX_gun() + Fighter.WIDTH) > baoshi[i].getX()
    					                                                            &&(baoshi[i].getY() + BaoShi.HEIGHT) > fighter.getY_gun()
    					                                                            &&baoshi[i].getY() < (fighter.getY_gun() +Fighter.HEIGHT);
                if(f)
                {
                	fighter.addCount(baoshi[i].getCount());
                	BaoShi g1 = baoshi[i];
    				baoshi[i] = baoshi[baoshi.length-1];
    				baoshi[baoshi.length-1]=g1;
    				baoshi = Arrays.copyOf(baoshi, baoshi.length-1);
                }
    		} 
    		
    		// bullet hit enemy
    		for(int i = 0 ;i < airplane.length ;i++ ) {
    			for(int j = 0 ;j < attack1.length ;j++ )
    			{
    				boolean f = airplane[i].getX()-50<attack1[j].getX()&&airplane[i].getX()+AirPlane.Air_WIDTH>attack1[j].getX()&&
    					    attack1[j].getY()<airplane[i].getY()+AirPlane.Air_HEIGHT&&attack1[j].getY()>airplane[i].getY()+60;
    					    
    			    if(f) {
    				airplane[i].Hp -= Attack.hurt1;
    				if(airplane[i].Hp > 0)		
    				{
    					BeAttacked = true;
    					selected = i;
    				}
    			}
    			}
    				
    			   if(airplane[i].Hp <= 0) BeAttacked = false;
    			   
    		}
    		
    		// bullet hit user
    		for(int i = 0;i < attack2.length ; i++ )
    		{
    			boolean f = fighter.getX_gun()- 40 < attack2[i].getX() && 
    			fighter.getX_gun()+Fighter.WIDTH>attack2[i].getX()&&
			    attack2[i].getY() < fighter.getY_gun()+Fighter.HEIGHT&&
			    attack2[i].getY() + 20 >fighter.getY_gun();
			    
			    
			    if(f)
			    {
			    	fighter.setHP(fighter.getHP() - Attack2.hurt2);
			    	Attack2 b = attack2[i];
					attack2[i] = attack2[attack2.length-1];
					attack2[attack2.length-1]=b;
					attack2 = Arrays.copyOf(attack2, attack2.length-1);	   
					fighter.setOver(true);
			    }
			    if(fighter.getHP()<=0) {
			    	gameover = gameover + 1;
			    }
    		}
    		
    		// user collision with enemy airplane
    		for(int i = 0;i < airplane.length; i++ )
    		{
    			boolean f = fighter.getX_gun()  < airplane[i].getX() + AirPlane.Air_WIDTH
    				     && fighter.getX_gun() + Fighter.WIDTH > airplane[i].getX()
    					 && fighter.getY_gun() < airplane[i].getY() + AirPlane.Air_HEIGHT
    					 && fighter.getY_gun() + Fighter.HEIGHT > airplane[i].getY()
    					 || fighter.getX_gun() + Fighter.WIDTH > airplane[i].getX()
    				     && fighter.getX_gun()  > airplane[i].getX() + AirPlane.Air_WIDTH
    				     && fighter.getY_gun() < airplane[i].getY() + AirPlane.Air_HEIGHT
    					 && fighter.getY_gun() + Fighter.HEIGHT > airplane[i].getY();
    		
    			
    			if(index % 200 == 0  && f) {
    				sysString = fighterString[1];
    				System.out.println("Collided! Collided!");	
    			}
    			   
    		}
    	}
        
        // move policy 
        private void stepAction()
        {
        	for(int i = 0;i<attack1.length;i++)
    		{
    			attack1[i].step();
    		}

    	
        	for (int i = 0; i <baoshi.length; i++) {
    			baoshi[i].step();
    		}
        	for(int i = 0;i < airplane.length;i++) {
        		airplane[i].step();
    	    }
        	for(int i = 0;i < attack2.length; i++ )
        	{
        		attack2[i].step();
        	}
        }
    
    
    public void keyPressed(KeyEvent e) {
		// get current x, y coordinate
		int x = fighter.getX_gun();
		int y = fighter.getY_gun();
		// up
		if(e.getKeyCode()==KeyEvent.VK_UP) {

			fighter.setY_gun(y-20);
			fighter.setQ(true);

		}
		// upper limit of user
		if(fighter.getY_gun()<=0) {
			fighter.setY_gun(y);
		}
		// down
		if(e.getKeyCode()==KeyEvent.VK_DOWN) {

			fighter.setY_gun(y+20);
			

		}
		// down move limit
		if(fighter.getY_gun()>=GameJFrame.HEIGHT-100) {
			fighter.setY_gun(y);
		}
		// right
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				fighter.setX_gun(x+20);
			fighter.setQ(true);
			
		}
		// right limit
		if(fighter.getX_gun()>=GameJFrame.WIDTH-50) {
			fighter.setX_gun(x);
		}
		// left
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			fighter.setX_gun(x-20);
			fighter.setQ(true);
			
		}
		// left limit
		if(fighter.getX_gun()<=0) {
			fighter.setX_gun(x);
		}
	}
							
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
