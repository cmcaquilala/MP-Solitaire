package application.model;

public class Player implements Comparable<Object> {

	private String name;
	private Integer score;
	
	public Player(String name, Integer score) {
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public int compareTo(Object o) {
		int compareScore = ((Player) o).getScore();
		
		return compareScore-this.score;
	}
}
