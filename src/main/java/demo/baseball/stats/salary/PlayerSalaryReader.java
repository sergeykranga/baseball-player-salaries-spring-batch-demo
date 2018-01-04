package demo.baseball.stats.salary;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class PlayerSalaryReader extends FlatFileItemReader<PlayerSalary> {
	
	public PlayerSalaryReader() {
		setResource(new ClassPathResource("Salaries.csv"));
		setLineMapper(new DefaultLineMapper<PlayerSalary>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "playerID", "salary" });
                setIncludedFields(3, 4);
                setLinesToSkip(1);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<PlayerSalary>() {{
                setTargetType(PlayerSalary.class);
            }});
        }});
	}
}
