package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;

import com.example.demo.helper.GitHelper;

public class GitPullAndPushOperation {

	@Value("${git.username}")
	private String gitUserName = "yadavgulabchand143";

	@Value("${git.password}")
	private String gitPassword = "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";

	@Value("${git.local.dir}")
	private String localDirPath = "D:\\Demo_Project\\demo";

	@Value("${git.remote.dir}")
	private String REMOTE_REPO_URL = "https://github.com/yadavgulabchand143/jgit_operations_integration.git";

	public void pullFromRemoteRepository() throws IOException, GitAPIException {
		System.out.println("BEGIN ::: Inside  pullFromRemoteRepository() method of GitPullAndPushOperation");
		try (Repository repository =  GitHelper.openRepository().getRepository()) {
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
			Git git = Git.open(new File(localDirPath));

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

		System.out.println("BEGIN ::: Inside  pushToRemoteRepository() method of GitPullAndPushOperation");
		try {

			Path sourcepath = Paths.get(sourceFileLocation);
			Path destnationPath = Paths.get(destinationFileLocation);
			Files.copy(sourcepath, destnationPath);

			try (Git git = Git.open(new File(localDirPath))) {
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
			}

		} catch (Exception e) {
			System.out.println("Exception occurred while pushing code into remote file");
			e.printStackTrace();
		}

	}
}
