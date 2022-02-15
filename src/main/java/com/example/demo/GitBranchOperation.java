package com.example.demo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;

import com.example.demo.helper.GitHelper;

public class GitBranchOperation {

	@Value("${git.username}")
	private String gitUserName = "yadavgulabchand143";

	@Value("${git.password}")
	private String gitPassword = "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";

	String BranchPathDel = "refs/heads/";
	String BranchPathRemoteDel = "refs/remotes/origin/";
	String newBranchNameDel = "test";
	String fromCutBranchNameDel = "master";

	public void CreateNewBranch(String fromCutBranchName, String branchPath, String newBranchName) {
		System.out.println("BEGIN ::: Inside  CreateNewBranch() method of GitBranchOperation");
		try (Repository repository = GitHelper.openRepository().getRepository()) {
			try (Git git = new Git(repository)) {
				List<Ref> call = git.branchList().call();
				for (Ref ref : call) {
					System.out.println("Branch details before creating new branch :" + ref.getName() + " "
							+ ref.getObjectId().getName());
				}

				List<Ref> refs = git.branchList().call();
				for (Ref ref : refs) {
					System.out.println("Had branch: " + ref.getName());
					if (ref.getName().equals(branchPath + newBranchName)) {
						System.out.println("Removing branch if same branch name is already exist");
						git.branchDelete().setBranchNames(newBranchName).setForce(true).call();
						break;
					}
				}

				System.out.println("creating new branch name : " + newBranchName);
				git.branchCreate().setName(newBranchName).setStartPoint(branchPath + fromCutBranchName).call();

				System.out.println("checout new branch into Local : " + newBranchName);
				git.checkout().setName(newBranchName).call();

				System.out.println("Push new branch into Remote : " + newBranchName);
				PushCommand pushCommand = git.push();
				pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassword));
				pushCommand.add(newBranchName);
				pushCommand.setRemote("origin");
				pushCommand.call();

				call = git.branchList().call();
				for (Ref ref : call) {
					System.out.println("Branch Details after new branch Created: " + ref.getName() + " "
							+ ref.getObjectId().getName());
				}
				System.out.println("END ::: Inside  CreateNewBranch() method of GitBranchOperation");
			}
		} catch (Exception e) {
			System.out.println("Exception Occured while creating new branch and pushing into remote");
			e.printStackTrace();
		}
	}

	public void switchBranchInLocal(String branchPath, String switchBranchName) throws IOException, GitAPIException {
		System.out.println("BEGIN ::: Inside  switchBranchInLocal() method of GitBranchOperation");
		try (Repository repository = GitHelper.openRepository().getRepository()) {
			try (Git git = new Git(repository)) {
				List<Ref> refs = git.branchList().call();
				for (Ref ref : refs) {
					if (ref.getName().equals(branchPath + switchBranchName)) {
						git.checkout().setName(switchBranchName).call();
						break;
					}
				}
			}
			System.out.println("END ::: Inside  switchBranchInLocal() method of GitBranchOperation");
		} catch (Exception e) {
			System.out.println("Exception Occured while Switching branch");
			e.printStackTrace();
		}
	}

	public void deleteBranch(String branchPath, String deleteBranchName, String BranchPathRemote)
			throws IOException, GitAPIException {
		System.out.println("BEGIN ::: Inside  deleteBranch() method of GitBranchOperation");
		try (Repository repository = GitHelper.openRepository().getRepository()) {
			try (Git git = new Git(repository)) {
				List<Ref> call = git.branchList().call();
				for (Ref ref : call) {
					System.out.println("Branch details before deleting branch : " + ref + " " + ref.getName() + " "
							+ ref.getObjectId().getName());
				}

				// make sure the branch is not there
				List<Ref> refs = git.branchList().call();
				for (Ref ref : refs) {
					System.out.println("Had branch: " + ref.getName());
					if (ref.getName().equals(branchPath + deleteBranchName)) {
						System.out.println("Removing branch : " + deleteBranchName);
						git.branchDelete().setBranchNames(deleteBranchName).setForce(true).call();
						// TODO ::: Delete Branch from remote
						git.branchDelete().setBranchNames(BranchPathRemote + deleteBranchName).setForce(true).call();
						RefSpec refSpec = new RefSpec().setSource(null)
								.setDestination(BranchPathRemote + deleteBranchName).setForceUpdate(true);
						git.push()
								.setCredentialsProvider(
										new UsernamePasswordCredentialsProvider(gitUserName, gitPassword))
								.setRefSpecs(refSpec).setRemote("origin").call();
						break;
					}
				}
				call = git.branchList().call();
				for (Ref ref : call) {
					System.out.println(
							"Branch Deleted after : " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
				}
			}
			System.out.println("END ::: Inside  deleteBranch() method of GitBranchOperation");
		} catch (Exception e) {
			System.out.println("Exception Occured while deleting branch");
			e.printStackTrace();
		}
	}

	/***
	 * Get list of all branches (including remote) & print
	 * 
	 * Equivalent of --> $ git branch -a
	 * 
	 ***/
	public void listBranches() {
		System.out.println("BEGIN ::: Inside  listBranches() method of GitBranchOperation");
		try {
			Git git = GitHelper.openRepository();
			System.out.println("Listing local branches:");
			git.branchList().call().stream().forEach(r -> System.out.println(r.getName()));

			System.out.println(" Listing all branches including remote \n ::");
			git.branchList().setListMode(ListMode.ALL).call().stream().forEach(r -> System.out.println(r.getName()));

		} catch (Exception e) {
			
		}
		System.out.println("END ::: Inside  listBranches() method of GitBranchOperation");
	}
	
	public String getCuurentBranchName() {
		System.out.println("END ::: Inside  getCuurentBranchName() method of GitBranchOperation");
		String currentBranch = null;
		try {
			Git git = GitHelper.openRepository();
			currentBranch = git.getRepository().getFullBranch();
			System.out.println("current branch name::: "+currentBranch);
		} catch (Exception e) {
			System.out.println("Exception occurred while getting current branch name");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  getCuurentBranchName() method of GitBranchOperation");
		return currentBranch;
	}
	
	/***
	 * If develop branch present then checkout.
	 * 
	 * Equivalent of --> $ git checkout -b local-develop /remotes/origin/develop
	 ***/
	public void findAndCheckoutBranch(String branchName) {
		System.out.println("BEGIN ::: Inside  findAndCheckoutBranch() method of GitBranchOperation");
		try {
			Git git = new Git(GitHelper.openRepository().getRepository());
			// Find develop branch from remote repo.
			Optional<String> developBranch = git.branchList().setListMode(ListMode.REMOTE).call().stream()
					.map(r -> r.getName()).filter(n -> n.contains(branchName)).findAny();

			if (developBranch.isPresent()) {
				System.out.println("\n>>> Checking out develop branch\n");
				git.checkout().setCreateBranch(true).setName(branchName).setStartPoint(developBranch.get()).call();
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while finding branch and checkout");
			e.printStackTrace();
		}
		System.out.println("END ::: Inside  findAndCheckoutBranch() method of GitBranchOperation");
	}

}
