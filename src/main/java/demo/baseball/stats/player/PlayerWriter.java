package demo.baseball.stats.player;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import demo.baseball.stats.PlayerStats;

public class PlayerWriter implements ItemWriter<Player> {
	
	@Autowired
	private PlayerStats playerStats;

	@Override
	public void write(final List<? extends Player> items) throws Exception {
		for (Player player : items) {
			this.playerStats.addPlayer(player);
		}
	}

}
