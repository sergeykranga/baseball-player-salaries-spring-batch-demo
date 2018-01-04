package demo.baseball.stats.player;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class PlayerReader extends FlatFileItemReader<Player> {
	
	public PlayerReader() {
		setResource(new ClassPathResource("Master.csv"));
		setLineMapper(new DefaultLineMapper<Player>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "playerID", "nameFirst", "nameLast" });
                setIncludedFields(0, 13, 14);
                setLinesToSkip(1);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Player>() {{
                setTargetType(Player.class);
            }});
        }});
	}
}
