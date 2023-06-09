package com.java.processor;

import org.springframework.batch.item.ItemProcessor;

public class CSVChangeProcessor implements ItemProcessor<String,String>{
	
	@Override
	public String process(String name) throws Exception{
		return name;
	}
}
