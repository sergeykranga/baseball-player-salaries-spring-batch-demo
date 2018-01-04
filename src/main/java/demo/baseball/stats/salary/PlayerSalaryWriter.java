package demo.baseball.stats.salary;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import demo.baseball.stats.PlayerStats;

public class PlayerSalaryWriter implements ItemWriter<PlayerSalary> {

	@Autowired
	private PlayerStats playerStats;
	
	@Override
	public void write(final List<? extends PlayerSalary> items) throws Exception {
		for (PlayerSalary playerSalary : items) {
			this.playerStats.addPlayerSalary(playerSalary);
		}
	}

}
