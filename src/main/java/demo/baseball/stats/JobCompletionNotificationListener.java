package demo.baseball.stats;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	@Autowired
	private PlayerStats playerStats;
	
	@Override
	public void afterJob(final JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("Job completed");
			this.playerStats.getPlayerTotalSalaries().entrySet()
				.stream()
				.filter(entry -> entry.getValue() != 0L)
				.forEach(entry -> log.info("total salary for player " + entry.getKey() + " is " + entry.getValue()));
			log.info("there were " + this.playerStats.getPlayers().size() + " players added");
		}
	}
}
