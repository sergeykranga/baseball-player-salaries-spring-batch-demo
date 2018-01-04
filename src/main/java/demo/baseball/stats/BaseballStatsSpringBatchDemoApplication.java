package demo.baseball.stats;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.baseball.stats.player.Player;
import demo.baseball.stats.player.PlayerReader;
import demo.baseball.stats.player.PlayerWriter;
import demo.baseball.stats.salary.PlayerSalary;
import demo.baseball.stats.salary.PlayerSalaryReader;
import demo.baseball.stats.salary.PlayerSalaryWriter;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableBatchProcessing
@Configuration
public class BaseballStatsSpringBatchDemoApplication extends DefaultBatchConfigurer {

	public static void main(final String[] args) {
		SpringApplication.run(BaseballStatsSpringBatchDemoApplication.class, args);
	}
	
	@Override
	public void setDataSource(final DataSource dataSource) {
		// no dataSource
	}
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
	
	@Bean
    public PlayerReader playerReader() {
        return new PlayerReader();
    }
	
	@Bean
	public PlayerWriter playerWriter() {
		return new PlayerWriter();
	}
	
	@Bean
	public PlayerSalaryReader playerSalaryReader() {
		return new PlayerSalaryReader();
	}
	
	@Bean
	public PlayerSalaryWriter playerSalaryWriter() {
		return new PlayerSalaryWriter();
	}
	
	@Bean
	public PlayerStats playerStats() {
		return new PlayerStats();
	}
	
	@Bean
	public JobCompletionNotificationListener jobCompletionNotificationListener() {
		return new JobCompletionNotificationListener();
	}
	
	@Bean
    public Job job() {
        return this.jobBuilderFactory.get("salaryCalculator")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionNotificationListener())
                .start(step1())
                .next(step2())
                .build();
    }
	
	@Bean
    public Step step1() {
        return this.stepBuilderFactory.get("masterStep")
                .<Player, Player> chunk(500)
                .reader(playerReader())
                .writer(playerWriter())
                .build();
    }
	
	@Bean
    public Step step2() {
        return this.stepBuilderFactory.get("salaryStep")
                .<PlayerSalary, PlayerSalary> chunk(500)
                .reader(playerSalaryReader())
                .writer(playerSalaryWriter())
                .build();
    }
}
