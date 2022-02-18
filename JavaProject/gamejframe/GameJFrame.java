package gamejframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import entity.*;


public class GameJFrame extends JFrame{

	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	
    private GameJPanel panel;
	private JPanel totalPanel;
	private JPanel informationPanel;
	private JPanel Panel1;
	private JPanel Panel1a;
	private JLabel nameLabel;
	private JLabel rankJLabel;
	private JLabel scoreJLabel;
	private JLabel hpJLabel;
	private JPanel Panel2;
	private JPanel blankJPanel2;
	private JPanel blankJPanel2a;
	private JLabel systemJLabel;
	private JLabel outputJLabel;
	private JPanel rank_name_panel;
	private JButton startButton;
	private JButton continueButton;
	private JButton exitButton;
	private JButton pauseButton;
	private JButton rankButton;
	private JPanel Button_Panel;
	boolean flag = false;
	private boolean startIndex = true;
	//String userName;

	
	
	public GameJFrame()
	{
		// start button action
		class startListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				// user information 
				if(startIndex) {
					//userName = JOptionPane.showInputDialog("Please input a user name:"); 
					startIndex = false;
				}
				if (flag == true && panel.gameover == -1) {
					panel.gameover = 0;
					panel.fighterHp = 200;
					panel.fighterScore = 0;
					panel.set_restart(true);
					panel.action();
				}
				//panel.set_pause(false);
				flag = true;
				
			}
		}
		// pause button action
		class pauseListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				panel.set_pause(true);
			}
		}
		
		// continue button action
		class continueListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				panel.set_pause(false);
			}
		}
		
		// rank button action
				class rankListener implements ActionListener {
					public void actionPerformed(ActionEvent arg0) {
						new FullRank();
					}
				}
			
//		// background picture panel
//		JPanel blankJPanel1 = new JPanel();
//		blankJPanel1.setLayout(new BorderLayout());
//		BackgroundPanel backgroundPanel = new BackgroundPanel(); 
//		blankJPanel1.add(backgroundPanel);
		
		// total panel
		totalPanel = new JPanel(new BorderLayout());
		panel = new GameJPanel();//game panel
		this.setFocusable(true);
		
		// information panel
		informationPanel = new JPanel();
		informationPanel.setLayout(new GridLayout(3, 1));
		
		// 1.score and Hit point and rank panel
		Panel1 = new JPanel(new BorderLayout(0,0));
		Panel1a = new JPanel(new GridLayout(4,1));
		rank_name_panel = new JPanel();
		nameLabel = new JLabel("               Score & Rank");
		//rank_name_panel.add(nameLabel);
		//rank_name_panel.setBackground(new Color(253,162,41));
		scoreJLabel = new JLabel("Score:                  " + 0);
		hpJLabel = new JLabel("Health Point:             " + 200);
		rankJLabel = new JLabel("Rank:                     " + "1st");
		rankButton = new JButton("Ranking");
		Font myfont = new Font("Courier New",Font.BOLD,15);
		rankButton.setFocusable(false);
		rankButton.addActionListener(new rankListener());
		rankButton.setBackground(new Color(0, 0, 17));
		rankButton.setForeground(Color.red);
		rankButton.setFont(myfont);
		Panel1a.add(nameLabel);
		Panel1a.add(scoreJLabel);
		Panel1a.add(hpJLabel);
		//Panel1a.add(rankJLabel);
		Panel1a.add(rankButton);
		Panel1a.setBackground(new Color(253,162,41));
		
		Panel1.add(Panel1a,BorderLayout.CENTER);
		
		// 2.system information panel
		blankJPanel2 = new JPanel(new BorderLayout());	
		blankJPanel2a = new JPanel(new GridLayout(3,1));
		systemJLabel = new JLabel("         System Information    ");
		outputJLabel = new JLabel("Game is runing!   ");
		blankJPanel2a.add(systemJLabel);
		blankJPanel2a.add(outputJLabel);
		
		blankJPanel2a.setBackground(new Color(253,162,41));
		blankJPanel2.add(blankJPanel2a,BorderLayout.CENTER);

		// 3.button area
		startButton = new JButton("Start");
		continueButton = new JButton("Continue");	
		exitButton = new JButton("Exit");
		pauseButton = new JButton("Pause");
		continueButton.setFocusable(false);
		pauseButton.setFocusable(false);
		exitButton.setFocusable(false);
		Button_Panel = new JPanel(new GridLayout(2,2));
		startButton.setBackground(new Color(0, 0, 17));
		
		startButton.setFont(myfont);
		continueButton.setFont(myfont);
		
		pauseButton.setFont(myfont);
		exitButton.setFont(myfont);
		continueButton.setBackground(new Color(0, 0, 17));
		pauseButton.setBackground(new Color(0, 0, 17));
		exitButton.setBackground(new Color(0, 0, 17));
		startButton.setForeground(Color.red);
		pauseButton.setForeground(Color.red);
		continueButton.setForeground(Color.red);
		exitButton.setForeground(Color.red);
		//startButton.setBorder(BorderFactory.createLineBorder(Color.blue));
		Button_Panel.add(startButton);
		Button_Panel.add(continueButton);
		Button_Panel.add(pauseButton);
		Button_Panel.add(exitButton);
		startButton.addActionListener(new startListener());
		continueButton.addActionListener(new continueListener());
		pauseButton.addActionListener(new pauseListener());
		exitButton.addActionListener(new ExitListener());
		
		
		//informationPanel.add(blankJPanel1); //background picture
		informationPanel.add(Panel1);
		informationPanel.add(blankJPanel2);
		informationPanel.add(Button_Panel);

		totalPanel.add(panel, BorderLayout.CENTER);//game panel
		totalPanel.add(informationPanel, BorderLayout.EAST);//information panel
		this.add(totalPanel);
		
		this.addKeyListener(panel);
		this.setSize(WIDTH+225,HEIGHT);
		this.setResizable(false);
	    this.setLocationByPlatform(true);
	    Image img = new ImageIcon("image/fengmian.jpg").getImage();
        this.setIconImage(img);	
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        this.setTitle("Java_Project_ht2210_yh3751");
		this.setVisible(true);
		
		while (!flag){
			try {
				this.setFocusable(true);
				Thread.sleep(10);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.setFocusable(true);
		panel.action();
		startButton.setFocusable(false);
		

		update();
	}
	
	// update score and hit point
	public void update() {		
		while(true) {
		scoreJLabel.setText("Score:                    " + panel.fighterScore);
		hpJLabel.setText("Health Point:               " + panel.fighterHp);	
		outputJLabel.setText(panel.sysString);
		}
	}
	
	public static void main(String[] args) {
		new GameJFrame();	
	}
}

class ExitListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
	}
}
