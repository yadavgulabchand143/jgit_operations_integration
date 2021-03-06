package com.example.demo;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CreateNewRepository {

	@Autowired 
	Environment environment;
	
	@Value("${git.local.dir}")
	private String localDirPath ;
	

	public Repository createRepositoryInLocal() throws IOException {
		System.out.println("BEGIN ::: Inside  createRepositoryInLocal() method of CreateNewRepository");
		Repository repository = null;
		try {
			// prepare a new folder
			if(localDirPath == null) {
				localDirPath = environment.getProperty("git.local.dir");
			}
			File localPath = new File(localDirPath);
			if (localPath.exists()) {
				localPath.mkdir();
			}
			localPath.delete();
			// create the directory
			try (Git git = Git.init().setDirectory(localPath).call()) {
				System.out.println("Having repository: " + git.getRepository().getDirectory());
				repository = git.getRepository();
			}
		} catch (Exception e) {
			System.out.println("Exception occured while creating new repository");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  createRepositoryInLocal() method of CreateNewRepository");
		return repository;
	}
}
