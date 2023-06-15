package com.java.config;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
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
		//builderではなくFlatFileReaderの実装に修正する
		//詳細はSpring batch リファレンスを参照
//		return new FlatFileItemReaderBuilder<Person>()
//				.name(null)
//				.resource(new FileSystemResource("\\C:\\springBatch_practice\\take1\\personDate.csv"))
//				.delimited()
//				.names(new String[] {"personID","firstName","lastName","gender","age","country"})
//				.fieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){
//					{
//						setTargetType(Person.class);
//					}
//				}).build();
		FlatFileItemReader<Person> itemReader=new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("\\C:\\springBatch_practice\\take1\\personDate.csv"));
		
		DefaultLineMapper<Person> lineMapper=new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
		//Mapperクラスの実装が不要となるBeanWrapperFieldSetMapperを使用
//		lineMapper.setFieldSetMapper(new PersonFieldSetMapper());
		
		
		lineMapper.setFieldSetMapper(fieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		itemReader.open(new ExecutionContext());
		
		//TODO
		//読み取り処理の記述が必要
		return itemReader;
	}
	
	/**
	 * Mapperクラスの実装が不要となるBeanWrapperFieldSetMapperを使用するためのメソッド
	 * @return
	 */
	@Bean
	public FieldSetMapper<Person> fieldSetMapper() {
		BeanWrapperFieldSetMapper<Person> fieldMapper=new BeanWrapperFieldSetMapper<>();
		fieldMapper.setPrototypeBeanName("person");
		fieldMapper.setTargetType(Person.class);
		
		return fieldMapper;
	}
	
	/**
	 * FieldSetMapperのプロトタイプ用メソッド
	 * @return
	 */
	@Bean
	@Scope("prototype")
	public Person person() {
		return new Person();
	}
}
