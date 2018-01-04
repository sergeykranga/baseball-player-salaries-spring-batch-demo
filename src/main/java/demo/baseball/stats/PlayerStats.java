package demo.baseball.stats;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import demo.baseball.stats.player.Player;
import demo.baseball.stats.salary.PlayerSalary;

public class PlayerStats {
	private final Map<Player, List<PlayerSalary>> stats = new ConcurrentHashMap<>();
	
	public synchronized void addPlayer(final Player player) {
		this.stats.putIfAbsent(player, new ArrayList<>());
	}
	
	public synchronized void addPlayerSalary(final PlayerSalary playerSalary) {
		this.stats.entrySet()
			.stream()
			.filter(player -> player.getKey().getPlayerId().equals(playerSalary.getPlayerID()))
			.forEach(entry -> entry.getValue().add(playerSalary));
	}
	
	public synchronized Map<Player, Long> getPlayerTotalSalaries() {
		Map<Player, Long> totalPlayerSalaries = new LinkedHashMap<>();
		
		List<Entry<Player, List<PlayerSalary>>> list = new ArrayList<>(this.stats.entrySet());
		list.sort((entry1, entry2) -> Long.compare(getTotalSalary(entry1.getValue()), getTotalSalary(entry2.getValue())));
		
		for (Map.Entry<Player, List<PlayerSalary>> entry : list) {
			totalPlayerSalaries.put(entry.getKey(), getTotalSalary(entry.getValue()));
		}
		
		return totalPlayerSalaries;
	}
	
	private long getTotalSalary(final List<PlayerSalary> salaries) {
		return salaries.stream().collect(Collectors.summarizingInt(PlayerSalary::getSalary)).getSum();
	}
	
	public synchronized Set<Player> getPlayers() {
		return this.stats.keySet();
	}
}
