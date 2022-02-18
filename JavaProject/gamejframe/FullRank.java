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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

public class FullRank extends JFrame{
	
	private JPanel totalPanel;
	private JLabel title;
	private JPanel titlePanel;
	private JPanel ranktitlePanel;
	private JPanel rankPanel;
	private JLabel ranking;
	private JLabel name;
	private JLabel score;
	private JLabel no_server;
	private JLabel date;
	private JLabel[] playerlist = new JLabel[5];
	private JLabel[] scorelist = new JLabel[5];
	private JLabel[] timelist = new JLabel[5];
	private ObjectInputStream inputFromClient;
	String host = "localhost";
	
	
	public FullRank() {
		super("Player Rank");
		totalPanel = new JPanel(new BorderLayout());
		title = new JLabel("Player Rank",JLabel.CENTER);
		ranking = new JLabel("Ranking",JLabel.CENTER);
		name = new JLabel("Name",JLabel.CENTER);
		score = new JLabel("Score",JLabel.CENTER);
		date = new JLabel("Date",JLabel.CENTER);
		ranktitlePanel = new JPanel(new GridLayout(1,4));		
		ranktitlePanel.add(ranking);
		ranktitlePanel.add(name);
		ranktitlePanel.add(score);
		ranktitlePanel.add(date);
		titlePanel = new JPanel(new GridLayout(2,1));
		titlePanel.add(title);
		titlePanel.add(ranktitlePanel);
		int t = get_rank();
		if (t == -1) {
			rankPanel = new JPanel();
			no_server = new JLabel("Can't connect to server! Please ensure the server is running!",JLabel.CENTER);
			rankPanel.add(no_server);
			totalPanel.add(titlePanel,BorderLayout.NORTH);
			totalPanel.add(rankPanel,BorderLayout.CENTER);
		}else {
			rankPanel = new JPanel(new GridLayout(t,4));
			for (int i =0; i < t; i++) {
				rankPanel.add(new JLabel("" + (i + 1),JLabel.CENTER));
				rankPanel.add(playerlist[i]);
				rankPanel.add(scorelist[i]);
				rankPanel.add(timelist[i]);
			}
		
			totalPanel.add(titlePanel,BorderLayout.NORTH);
			totalPanel.add(rankPanel,BorderLayout.CENTER);
		}
		add(totalPanel);
		
		setSize(500,200);
		this.setLocationByPlatform(true);
		setVisible(true);
		get_rank();
	}
	
	public int get_rank() {
		try {
	        // Establish connection with the server
			Socket socket = null;
			try {
	            socket = new Socket(host, 8000);}
			catch(ConnectException e){
				System.out.println("can't find server!");
				return -1;
			}

	        // Create an output stream to the server
	        ObjectOutputStream toServer =
	          new ObjectOutputStream(socket.getOutputStream());
	        Network_trans_object s = new Network_trans_object("", 0, "", 2);		        
	        toServer.writeObject(s);
	        inputFromClient = new ObjectInputStream(socket.getInputStream());
	        try {
				Object received_object = inputFromClient.readObject();
				Network_trans_object r = (Network_trans_object) received_object;
				ArrayList<Object> r_rank = r.getRank();
				for (int i = 0; i < 5; i++) {
					if (r_rank.size() < 5) {
						if(i == r_rank.size()) {
							return i;
						}
					}
					RankEntry entry = (RankEntry) r_rank.get(i);
					playerlist[i] = new JLabel(entry.getPlayer(),JLabel.CENTER);
					scorelist[i] = new JLabel(""+entry.getScore(),JLabel.CENTER);
					timelist[i] = new JLabel(entry.getTime(),JLabel.CENTER);
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}


	        // Create a Student object and send to the server
	        
	        
	      }
	      catch (IOException ex) {
	        ex.printStackTrace();
	      }
		return 5;
	}

	public static void main(String[] args) {
		FullRank test = new FullRank();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
