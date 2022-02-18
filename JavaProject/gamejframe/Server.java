package gamejframe;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

import entity.*;

import java.sql.*;

public class Server extends JFrame implements Runnable{
 private JTextArea show_info;
 private int clientNo = 0;

 public Server() {
   show_info = new JTextArea(10,10);
   JScrollPane sp = new JScrollPane(show_info);
   this.add(sp);
   this.setTitle("Game MultiThreadServer");
   this.setSize(400,200);
   Thread t = new Thread(this);
   t.start();
 }
 @Override
 public void run() {
 	try {
 		 ServerSocket serverSocket = new ServerSocket(8000);
         show_info.append("Game MultiThreadServer started at " + new Date() + '\n');
         while (true) {
             // Listen for a new connection request
             Socket socket = serverSocket.accept();
             // Increment clientNo
             clientNo++;  
             show_info.append("Starting thread for client " + clientNo +
                 " at " + new Date() + '\n');
             // Create and start a new thread for the connection
             new Thread(new HandleAClient(socket, clientNo)).start();
           }
 	}
 	catch(IOException ex) {
 	     ex.printStackTrace();
 	}
 }
 // Define the thread class for handling new connection
 class HandleAClient implements Runnable {
   private Socket socket; // A connected socket
   private int clientNum;
   private ObjectOutputStream outputToFile;
   private ObjectInputStream inputFromClient;
   private ArrayList<Object> rank_to_sent = new ArrayList<>();
   /** Construct a thread */
   public HandleAClient(Socket socket, int clientNum) {
     this.socket = socket;
     this.clientNum = clientNum;
   }

   /** Run a thread */
   public void run() {
       // Continuously serve the client
       try {
    	   while(true) {
	    	   // Create an input stream from the socket
    		   try {
	    	   inputFromClient = new ObjectInputStream(socket.getInputStream());
	    	   
	    	   }
    		   catch (EOFException e){
    		   }
    		   ObjectOutputStream outputToFile = new ObjectOutputStream(socket.getOutputStream());
    		   
	    	   // Read from input
	    	   Object object = inputFromClient.readObject();
	    	   // Write to the file
	    	   Network_trans_object s = (Network_trans_object)object;
	    	   System.out.println("got object " + s.toString());
	    	   if (s.getRequest_code() == 1) {
	    		   show_info.append("Saving record for " + s.getGame_id() + '\n');
	    		   save_record(s);
	    		   
	    	   }
	    	   else if (s.getRequest_code() == 2) {
	    		   show_info.append("Sending ranking!");
	    		   rank_to_sent = send_rank();
	    		   Network_trans_object wrap_box = new Network_trans_object(rank_to_sent);
	    		   outputToFile.writeObject(wrap_box);
	    		   outputToFile.writeObject(null);
	        	   outputToFile.flush();
	        	   System.out.println("Rank has been sent!");
	    	   } 
    	   
    	   }
       }
    	   catch(ClassNotFoundException ex) {
    	     ex.printStackTrace();
    	   }
    	   catch(IOException ex) {
    	     ex.printStackTrace();
    	   }
    	   finally {
    	     try {
    	       //inputFromClient.close();
    	       //outputToFile.close();
    	     }
    	     catch (Exception ex) {
    	       ex.printStackTrace();
    	     }
    	  }
   }
 }
 
 /**
  * The main method is only needed for the IDE with limited
  * JavaFX support. Not needed for running from the command line.
  */
 public static void main(String[] args) {
   Server mts = new Server();
   mts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   mts.setVisible(true);
 }
 
 public void save_record(Network_trans_object save_object) {
	 Connection connection =null;
	 System.out.println(save_object.getCur_time());
	 try {
			connection = DriverManager.getConnection
			  ("jdbc:sqlite:Game.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);;
		}
		System.out.println("Database connected");
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		ResultSet resultSet = null;
		try {
			statement.execute
					  ("INSERT INTO Rank (Name, BestScore, Date)"
					  		+ "VALUES ( '"+ save_object.getGame_id() + ", " + save_object.getScore() + ", " 
							  + save_object.getCur_time() + " );");
			System.out.println("A new game record is stored");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			e.getErrorCode();
			if (e.getErrorCode() == 19) {
				try {
					int bestscore = 0;
					
					resultSet = statement.executeQuery("select BestScore from rank where Name = '"+ save_object.getGame_id() + "';");
					while (resultSet.next()) {
						bestscore = Integer.valueOf(resultSet.getString(1));
						System.out.println("This user bestscore: " + bestscore);}
					if (bestscore > save_object.getScore()) {
						System.out.println("No more than best score! Will not be recorded!");
					}
					else {
						System.out.println("update!" + save_object.getGame_id());
						statement.execute("UPDATE Rank SET BestScore = " + 
					        save_object.getScore() + " WHERE Name = '" + save_object.getGame_id() + "';");
						statement.execute("UPDATE Rank SET Date = " + 
						        save_object.getGame_id() + " WHERE Name = '" + save_object.getGame_id() + "';");
					}
				}
				catch (SQLException e2) {
					e2.getMessage();
				}
			}
			//System.out.println("i am here:" + e.getErrorCode());
		}
//		try {
//			//System.out.println(x);}
//			resultSet = statement.executeQuery(
//					"SELECT * FROM Rank;");
//			while (resultSet.next())
//				System.out.println(resultSet.getString(1) + "\t" +
//					    resultSet.getString(2) + "\t" + resultSet.getString(3)+ "\t");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.exit(0);
//		}	
 }
 public ArrayList<Object> send_rank() {
	 Connection connection = null;
	 ArrayList<Object> rank = new ArrayList<>();
	 try {
			connection = DriverManager.getConnection
			  ("jdbc:sqlite:Game.db");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Database connected");
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		ResultSet resultSet = null;
		try {
			//System.out.println(x);}
			resultSet = statement.executeQuery(
					"SELECT * FROM Rank order by BestScore desc;");
			
			while (resultSet.next()) {
				RankEntry single_entry = new RankEntry("",0,"");
				single_entry.setPlayer(resultSet.getString(1));
			    single_entry.setScore(Integer.valueOf(resultSet.getString(2)));
			    single_entry.setTime(resultSet.getString(3));
				System.out.println(resultSet.getString(1) + "\t" +
					    resultSet.getString(2) + "\t" + resultSet.getString(3)+ "\t");
				rank.add(single_entry);}
			
				
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}finally{
			return rank;
		}
 }
 
 
 
}
   
