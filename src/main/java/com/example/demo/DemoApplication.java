package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =   SpringApplication.run(DemoApplication.class, args);
		EntryPoint entryPoint = context.getBean(EntryPoint.class);
		//entryPoint.pushToRemote("Initial Commit", "yadavgulabchand143", "master");
		entryPoint.createRepository();
		//entryPoint.cloneRepository();
		//entryPoint.createBranch("master", "develop");
		//entryPoint.pullFromRemote();
		//entryPoint.getCurrentBranchDetails();
		//entryPoint.switchBranch("master");
	}

}
