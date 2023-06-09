package com.java.config;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.java.form.Person;

@Configuration
public class PersonConfig {
	
	@Bean
	public FlatFileItemReader<Person> reader(){
		FlatFileItemReader<Person> reader=new FlatFileItemReader<Person>();
		reader.setResource(new FileSystemResource("\\C:\\springBatch_practice\\take1\\personDate.csv"));
		
		//TODO
		//readerの具体的な実装を記述する
		return reader;
	}
}
