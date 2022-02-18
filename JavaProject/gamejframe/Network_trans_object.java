package gamejframe;

import java.util.ArrayList;

public class Network_trans_object implements java.io.Serializable {
	private String game_id;
	private int score;
	private String cur_time;
	private int request_code;
	private ArrayList<Object> rank = new ArrayList<>();
	public Network_trans_object(String game_id, int score, String cur_time, int request_code) {
		this.game_id = game_id;
		this.score = score;
		this.cur_time = cur_time;
		this.request_code = request_code;
	}
	public Network_trans_object(ArrayList<Object> rank) {
		this.rank = rank;
	}
	public String getGame_id() {
		return game_id;
	}
	public int getScore() {
		return score;
	}
	public String getCur_time() {
		return cur_time;
	}	
	public int getRequest_code() {
		return request_code;
	}
	public ArrayList<Object> getRank() {
		return rank;
	}
	public void setRank(ArrayList<Object> rank) {
		this.rank = rank;
	}
	public String toString() {
		  return "game_id: " + this.game_id + ", score: " + this.score + ", cur_time: " + this.cur_time;
	  }
}
