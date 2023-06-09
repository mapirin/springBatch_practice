package com.java.processor;

import org.springframework.batch.item.ItemProcessor;

import com.java.form.Person;

public class CSVChangeProcessor implements ItemProcessor<Person,Person>{
	
	@Override
	public Person process(Person person) throws Exception{
		return person;
	}
}
