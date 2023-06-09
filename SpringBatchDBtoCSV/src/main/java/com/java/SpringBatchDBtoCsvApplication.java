package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchDBtoCsvApplication {

	public static void main(String[] args) throws Exception{
		System.exit(SpringApplication.exit(SpringApplication.run(SpringBatchDBtoCsvApplication.class, args)));
	}

}
