package entity;

public class RankEntry implements java.io.Serializable{
	private String player;
	private int score;
	private String time;
	public RankEntry(String player,int score,String time) {
		this.player = player;
		this.score = score;
		this.time = time;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
