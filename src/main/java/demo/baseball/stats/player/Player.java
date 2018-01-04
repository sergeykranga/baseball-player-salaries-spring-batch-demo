package demo.baseball.stats.player;

import lombok.Data;

@Data
public class Player {
	private String playerId;
	private String nameFirst;
	private String nameLast;
	
	@Override
	public String toString() {
		return "[" + this.playerId + "] " + this.nameFirst + " " + this.nameLast;
	}
}
