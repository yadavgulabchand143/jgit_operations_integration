package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.helper.GitHelper;

@Component
public class GitPullAndPushOperation {
	
	@Autowired
	private GitHelper gitHelper;

	@Value("${git.username}")
	private String gitUserName ;

	@Value("${git.password}")
	private String gitPassword ;

	@Value("${git.local.dir}")
	private String localDirPath ;

	@Value("${git.remote.dir}")
	private String REMOTE_REPO_URL ;

	public void pullFromRemoteRepository() throws IOException, GitAPIException {
		System.out.println("BEGIN ::: Inside  pullFromRemoteRepository() method of GitPullAndPushOperation");
		try (Repository repository =  gitHelper.openRepository().getRepository()) {
			try (Git git = new Git(repository)) {
				git.pull().call();
			}
			System.out.println("Pulled from remote repository to local repository at " + repository.getDirectory());
		} catch (Exception e) {
			System.out.println("Exception occured while taking pull from remote into local");
			e.printStackTrace();
		}
	}

	public void pushToRemoteRepository(String commitMessage, String authorName, String pushBranchName) {
		System.out.println("BEGIN ::: Inside  pushToRemoteRepository() method of GitPullAndPushOperation");
		try {
			Git git = gitHelper.openRepository();

			git.add().addFilepattern(".").call();
			git.commit().setMessage(commitMessage).setAuthor("author", authorName).call();

			/*** git remote add origin git@github.com:yadavgulabchand143/test_repo.git ***/
			RemoteAddCommand remoteAddCommand = git.remoteAdd();
			remoteAddCommand.setName("origin");
			remoteAddCommand.setUri(new URIish(REMOTE_REPO_URL));
			remoteAddCommand.call();

			/*** git push -u origin master ***/
			PushCommand pushCommand = git.push();
			pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassword));
			pushCommand.add(pushBranchName);
			pushCommand.setRemote("origin");
			pushCommand.call();
			System.out.println("END ::: Inside  pushToRemoteRepository() method of GitPullAndPushOperation");
		} catch (Exception e) {
			System.out.println("Exception occurred while pushing code into remote file");
			e.printStackTrace();
		}
	}
	
	public void pushFilesInRemote(String sourceFileLocation, String destinationFileLocation,
			String pushBranchName, String commitMessage, String authorName) {

		System.out.println("BEGIN ::: Inside  pushFilesInRemote() method of GitPullAndPushOperation");
		try {

			Path sourcepath = Paths.get(sourceFileLocation);
			Path destnationPath = Paths.get(destinationFileLocation);
			Files.copy(sourcepath, destnationPath);

			try (Git git = gitHelper.openRepository()) {
				git.add().addFilepattern(".").call();
				git.commit().setMessage(commitMessage).setAuthor("author", authorName).call();

				/*** git remote add origin git@github.com:yadavgulabchand143/test_repo.git ***/
				RemoteAddCommand remoteAddCommand = git.remoteAdd();
				remoteAddCommand.setName("origin");
				remoteAddCommand.setUri(new URIish(REMOTE_REPO_URL));
				remoteAddCommand.call();

				/*** git push -u origin master ***/
				PushCommand pushCommand = git.push();
				pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassword));
				pushCommand.add(pushBranchName);
				pushCommand.setRemote("origin");
				pushCommand.call();
				System.out.println("END ::: Inside  pushFilesInRemote() method of GitPullAndPushOperation");
			}

		} catch (Exception e) {
			System.out.println("Exception occurred while pushing code into remote file");
			e.printStackTrace();
		}

	}
	
	/***
	 * Check status of modified file.
	 * 
	 * Equivalent of --> $ git status
	 * 
	 ***/
	public void checkGitFileStatus() {
		System.out.println("BEGIN ::: Inside  checkGitFileStatus() method of GitPullAndPushOperation");
		try {
			Git git = new Git(gitHelper.openRepository().getRepository());
			System.out.println("Printing status of local repository  ::: ");
			Status status = git.status().call();
			System.out.println("Added: " + status.getAdded());
            System.out.println("Changed: " + status.getChanged());
            System.out.println("Conflicting: " + status.getConflicting());
            System.out.println("ConflictingStageState: " + status.getConflictingStageState());
            System.out.println("IgnoredNotInIndex: " + status.getIgnoredNotInIndex());
            System.out.println("Missing: " + status.getMissing());
            System.out.println("Modified: " + status.getModified());
            System.out.println("Removed: " + status.getRemoved());
            System.out.println("Untracked: " + status.getUntracked());
            System.out.println("UntrackedFolders: " + status.getUntrackedFolders());
            System.out.println("END ::: Inside  checkGitFileStatus() method of GitPullAndPushOperation");
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
	public void gitCommitFiles(String commitMessage) {
		System.out.println("BEGIN ::: Inside  gitCommitFiles() method of GitPullAndPushOperation");
		try {
			System.out.println("\n>>> Committing changes\n");
			Git git = new Git(gitHelper.openRepository().getRepository());
			git.add().addFilepattern(".").call();
			RevCommit revCommit = git.commit().setAll(true).setMessage(commitMessage).call();
			System.out.println("Commit = " + revCommit.getFullMessage());
		} catch (Exception e) {
			System.out.println("Exception occurred while committing modified file");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  gitCommitFiles() method of GitPullAndPushOperation");
	}

}
