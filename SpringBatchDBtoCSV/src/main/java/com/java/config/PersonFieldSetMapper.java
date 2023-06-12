package com.java.config;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.java.form.Person;

public class PersonFieldSetMapper implements FieldSetMapper<Person>{
	public Person mapFieldSet(FieldSet fieldSet) {
		Person person =new Person();
		
		person.setPersonID(fieldSet.readString(0));
		person.setFirstName(fieldSet.readString(1));
		person.setLastName(fieldSet.readString(2));
		person.setGender(fieldSet.readString(3));
		person.setAge(fieldSet.readString(4));
		person.setCountry(fieldSet.readString(5));
		
		return person;
	}
}
