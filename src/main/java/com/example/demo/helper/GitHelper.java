package com.example.demo.helper;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitHelper {
	
	@Value("${git.local.dir}")
	private String localDirPath ;

	public static boolean repositoryExists(File directory) {
		System.out.println("BEGIN ::: Inside  repositoryExists() method of GitHelper");
		boolean gitDirExists = false;
		try {
			FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
			repositoryBuilder.findGitDir(directory);

			if (repositoryBuilder.getGitDir() != null) {
				gitDirExists = true;
			}

		} catch (Exception e) {
			System.out.println("Exception occured while checking repository exists or not");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  repositoryExists() method of GitHelper");
		return gitDirExists;
	}

	/*** Open the Repository ***/
	public Git openRepository() throws IOException {
		System.out.println("BEGIN ::: Inside  openRepository() method of GitHelper");
		Git git = null;
		try {
			System.out.println("Open Local Directory Path " + localDirPath);
			git = Git.open(new File(localDirPath));
			
		} catch (Exception e) {
			System.out.println("Exception occured while opening repository");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  openRepository() method of GitHelper");
		return git;
	}
}

