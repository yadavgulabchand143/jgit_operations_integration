package com.example.demo;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.helper.GitHelper;

@Component
public class CloneRepository {
	
	@Autowired
	private GitHelper gitHelper;
	
	@Value("${git.username}")
	private String gitUserName;
	
	@Value("${git.password}")
	private String gitPassword;
	
	@Value("${git.local.dir}")
	private String localDirPath ;
	
	@Value("${git.remote.dir}")
	private String REMOTE_REPO_URL;
	
	/***
	 * Git clone remote repo into local directory.
	 * 
	 * Equivalent of --> $ git clone https://github.com/yadavgulabchand143/weather-app.git
	 ***/
	public Git cloneDefaultRepository() {
		System.out.println("BEGIN ::: Inside  cloneDefaultRepository() method of CloneRepository");
		Git result = null;
		try {
			/*** prepare a new folder for the cloned repository ***/
			File cloneLocalDirPath = new File(localDirPath);
			if (cloneLocalDirPath.exists()) {
				cloneLocalDirPath.mkdir();
			}
			cloneLocalDirPath.delete();

			boolean gitDirExists = GitHelper.repositoryExists(cloneLocalDirPath);

			if (gitDirExists) {
				System.out.println("Existing Clone Directory" + cloneLocalDirPath);
				result = Git.open(cloneLocalDirPath);
			} else {
				System.out.println("Clone new Directory" + cloneLocalDirPath);
				result = Git.cloneRepository().setURI(REMOTE_REPO_URL)
						.setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassword))
						.setDirectory(cloneLocalDirPath).call();
				System.out.println("Cloning " + REMOTE_REPO_URL + " into " + cloneLocalDirPath);
			}
			System.out.println("Having repository: " + result.getRepository().getDirectory());

		} catch (Exception e) {
			System.out.println("Exception occurred while cloning default repo");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  cloneDefaultRepository() method of CloneRepository");
		return result;
	}
	
	public Git cloneSpecificRepository(String branchName) {
		System.out.println("BEGIN ::: Inside  cloneSpecificRepository() method of CloneRepository");
		Git result = null;
		try {
			/*** prepare a new folder for the cloned repository ***/
			File cloneLocalDirPath = new File(localDirPath);
			if (cloneLocalDirPath.exists()) {
				cloneLocalDirPath.mkdir();
			}
			cloneLocalDirPath.delete();

			boolean gitDirExists = GitHelper.repositoryExists(cloneLocalDirPath);

			if (gitDirExists) {
				System.out.println("Existing Clone Directory" + cloneLocalDirPath);
				result = Git.open(cloneLocalDirPath);
			} else {
				result = Git.cloneRepository().setURI(REMOTE_REPO_URL)
						.setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassword))
						.setDirectory(cloneLocalDirPath).setBranch(branchName).call();
				System.out.println("Cloning " + REMOTE_REPO_URL + " into " + cloneLocalDirPath);
			}
			System.out.println("Having repository: " + result.getRepository().getDirectory());
		} catch (Exception e) {
			System.out.println("Exception occurred while cloning specific repository");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  cloneSpecificRepository() method of CloneRepository");
		return result;
	}
	
	public void getCurrentRespositoryDetails() {
		System.out.println("BEGIN ::: Inside  getCurrentRespositoryDetails() method of CloneRepository");
		try {
			Git git = new Git(gitHelper.openRepository().getRepository());
			System.out.println("Repository Details: " + git.getRepository().getDirectory());
		}catch (Exception e) {
			System.out.println("Exception occurred while getting repository details");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  getCurrentRespositoryDetails() method of CloneRepository");			
	}
}
