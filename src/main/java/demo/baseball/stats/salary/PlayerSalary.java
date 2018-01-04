package demo.baseball.stats.salary;

import lombok.Data;

@Data
public class PlayerSalary {
	private String playerID;
	private String salary = "0";
	
	public int getSalary() {
		return Integer.parseInt(this.salary);
	}
}
