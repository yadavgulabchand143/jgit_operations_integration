package com.example.demo;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.revwalk.RevCommit;

import com.example.demo.helper.GitHelper;

public class GitOperation {

	
	/***
	 * Check status of modified file.
	 * 
	 * Equivalent of --> $ git status
	 * 
	 ***/
	public void checkGitFileStatus() {
		try {
			Git git = new Git(GitHelper.openRepository().getRepository());
			System.out.println("\n>>> Printing status of local repository\n");
			Status status = git.status().call();
			System.out.println("Modified file = " + status.getModified());
		} catch (Exception e) {
			System.out.println("Exception occurred while checking  status of modified file");
			e.printStackTrace();
		}
	}

	/***
	 * Stage modified files and Commit changes .
	 * 
	 * Equivalent of --> $ git commit -a
	 * 
	 ***/
	public void commitModifiedFiles() {
		try {
			System.out.println("\n>>> Committing changes\n");
			Git git = new Git(GitHelper.openRepository().getRepository());
			git.add().addFilepattern(".").call();
			RevCommit revCommit = git.commit().setAll(true).setMessage("Adding commit from JGIT").call();
			System.out.println("Commit = " + revCommit.getFullMessage());
		} catch (Exception e) {
			System.out.println("Exception occurred while committing modified file");
			e.printStackTrace();
		}
	}
}



