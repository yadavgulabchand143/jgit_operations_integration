package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntryPoint {
	
	@Autowired 
	CreateNewRepository createNewRepository;
	@Autowired 
	CloneRepository cloneRepository;
	@Autowired 
	GitPullAndPushOperation gitPullAndPushOperation;
	@Autowired 
	GitBranchOperation gitBranchOperation;
	
	/**
	 * 
	 * create a new repository in local and initialize with GIT repository
	 * 
	 */
	public void createRepository(){
		try {
			createNewRepository.createRepositoryInLocal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * clone repository from remote to local and clone the default repository from the remote EX: master branch
	 */
	public void cloneRepository() {
		cloneRepository.cloneDefaultRepository();
	}
	
	/**
	 * 
	 * clone specific repository from remote to local
	 * @param branchName
	 * 
	 */
	public void cloneRepository(String branchName) {
		cloneRepository.cloneSpecificRepository(branchName);
	}
	
	/**
	 * 
	 * Pull the updates from remote to local
	 * 
	 */
	public void pullFromRemote() {
		try {
			gitPullAndPushOperation.pullFromRemoteRepository();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Push the local changes to remote repository
	 * @param commiteMessage
	 * @param authorName
	 * @param pushBranchName
	 */
	public void pushToRemote(String commiteMessage, String authorName, String pushBranchName ){
		gitPullAndPushOperation.pushToRemoteRepository(commiteMessage, authorName, pushBranchName);
	}
	
	/**
	 * 
	 * push the file present in sourceFileLocation to destinationFileLocation and
	 *  then push the changes to remote repository 
	 * @param sourceFileLocation
	 * @param destinationFileLocation
	 * @param pushBranchName
	 * @param commiteMessage
	 * @param authorName
	 */
	public void pushFileToRemote(String sourceFileLocation, String destinationFileLocation, String pushBranchName,
			String commiteMessage, String authorName) {
		gitPullAndPushOperation.pushFilesInRemote(sourceFileLocation, destinationFileLocation, pushBranchName,
				commiteMessage, authorName);
	}
	
	/**
	 * get the file status of current branch in local repository
	 */
	public void getGitFileStatus() {
		gitPullAndPushOperation.checkGitFileStatus();
	}
	
	/**
	 * Commit the changes from local to remote repository
	 * @param commitMessage
	 */
	public void commitChangesToRemote(String commitMessage) {
		gitPullAndPushOperation.gitCommitFiles(commitMessage);
	}
	
	/**
	 * Creating new branch in local at same time push new branch in remote repository
	 * @param fromCutBranchName
	 * @param newBranchName
	 */
	public void createBranch(String fromCutBranchName, String newBranchName) {
		gitBranchOperation.CreateNewBranch(fromCutBranchName, newBranchName);
	}
	
	/**
	 * switch branch from one branch to other branch in local if reference already exist in local
	 * @param switchBranchName
	 */
	public void switchBranch(String switchBranchName) {
		try {
			gitBranchOperation.switchBranchInLocal(switchBranchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get list of all branches present in local and  remote
	 */
	public void getListOfBranches() {
		gitBranchOperation.listBranches();
	}
	
	/**
	 * Get name of current local branch
	 */
	public void getCurrentBranchDetails() {
		gitBranchOperation.getCuurentBranchName();
	}
	
	/**
	 * checkout branch from remote if not present in local repository
	 * @param branchName
	 */
	public void checkOutBranchFromRemote(String branchName) {
		gitBranchOperation.findAndCheckoutBranch(branchName);
	}
	
	/**
	 *  TODO
	 *  delete branch from local as well as remote repository
	 * @param deleteBranchName
	 *
	 */
	public void deleteBranch(String deleteBranchName) {
		try {
			gitBranchOperation.deleteBranch(deleteBranchName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
