package com.java.config;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.java.form.Person;

@Configuration
public class PersonConfig {
	
	/**
	 * csvファイルを読み込むクラス
	 * 
	 * @return
	 */
	@Bean
	public FlatFileItemReader<Person> reader(){
		//TODO
		//builderではなくFlatFileReaderの実装に修正する
		//詳細はSpring batch リファレンスを参照
		return new FlatFileItemReaderBuilder<Person>()
				.name(null)
				.resource(new FileSystemResource("\\C:\\springBatch_practice\\take1\\personDate.csv"))
				.delimited()
				.names(new String[] {"personID","firstName","lastName","gender","age","country"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){
					{
						setTargetType(Person.class);
					}
				}).build();
	}
}
