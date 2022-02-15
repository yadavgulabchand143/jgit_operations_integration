package com.example.demo;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CreateNewRepository {

	@Value("${git.local.dir}")
	private static String localDirPath;

	public Repository createRepositoryInLocal() throws IOException {
		System.out.println("BEGIN ::: Inside  createRepositoryInLocal() method of CreateNewRepository");
		Repository repository = null;
		try {
			// prepare a new folder
			File localPath = new File(localDirPath);
			if (localPath.exists()) {
				localPath.mkdir();
			}
			localPath.delete();
			// create the directory
			try (Git git = Git.init().setDirectory(localPath).call()) {
				System.out.println("Having repository: " + git.getRepository().getDirectory());
			}
		} catch (Exception e) {
			System.out.println("Exception occured while creating new repository");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  createRepositoryInLocal() method of CreateNewRepository");
		return repository;
	}
}
