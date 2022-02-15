package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages="com.example.demo")
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =   SpringApplication.run(DemoApplication.class, args);
		EntryPoint entryPoint = context.getBean(EntryPoint.class);
		entryPoint.pushToRemote("handle Exception", "yadavgulabchand143", "develop");
		
	}

}
