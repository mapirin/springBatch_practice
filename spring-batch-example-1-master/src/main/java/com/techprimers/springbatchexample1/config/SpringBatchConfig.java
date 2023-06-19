package com.techprimers.springbatchexample1.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.techprimers.springbatchexample1.model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<User> itemReader, ItemProcessor<User, User> itemProcessor, ItemWriter<User> itemWriter) {

		Step step = stepBuilderFactory.get("ETL-file-load").<User, User>chunk(100).reader(itemReader)
				.processor(itemProcessor).writer(itemWriter).build();

		return jobBuilderFactory.get("ETL-Load").incrementer(new RunIdIncrementer()).start(step).build();
	}

	/**
	 * java/resourse内のCSVファイルを読み取る BeanのnameはCSV-Reader 1行目のヘッダーをスキップ
	 * ItemReaderにLineMapperメソッドをセットして戻す
	 * 
	 * @return flatFileItemReader
	 */
	@Bean
	public FlatFileItemReader<User> itemReader() {

		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	/**
	 * CSVのLine読み取り用Mapper １． デリミタは","を使用 厳格モードは使用しない リソースのIDを配列にセット
	 * 
	 * ２． リソースのIDを含むMapperクラスをラップしてクラスを明示
	 * 
	 * LineMapperに１をセット LineMapperに２をセット
	 * 
	 * @return defaultLineMapper
	 */
	@Bean
	public LineMapper<User> lineMapper() {

		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id", "name", "dept", "salary");

		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}

	/**
	 * FlatFileItemWriterにて出力するファイル名のフルパスを設定
	 * デリミタを","に設定
	 * FieldExtractorを使用して出力ファイルに対するBeanのIDを設定
	 * LineAggregatorにセット
	 * Writerにセット
	 * @return
	 */
	@Bean
	public FlatFileItemWriter<User> writer() {
		FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
		writer.setResource(new FileSystemResource("\\C:\\Users\\neo-matrix506\\Documents\\batch_out\\springBatch_practice\\users.csv"));
		DelimitedLineAggregator<User> lineAggregator = new DelimitedLineAggregator<User>();
		lineAggregator.setDelimiter(",");
		
		BeanWrapperFieldExtractor<User> fieldExtractor=new BeanWrapperFieldExtractor<User>();
		fieldExtractor.setNames(new String[]{"id","name","dept","salary"});
		lineAggregator.setFieldExtractor(fieldExtractor);
		
		writer.setLineAggregator(lineAggregator);
		return writer;
	}

}
