package com.example.demo;

public class Test {

	public static void main(String[] args) {
		
		try {

			GitPullAndPushOperation gitPull = new GitPullAndPushOperation();
//			gitPull.pushFilesInRemote("D:\\Demo_Project\\Test\\test.txt", 
//					"D:\\Demo_Project\\demo\\src\\main\\resources\\test.txt", 
//					"testbranch", "Initial Commit", "yadavgulabchand143");
			gitPull.pushToRemoteRepository("Inital JGIT Commit","yadavgulabchand143","master");
			//gitPull.pullFromRemoteRepository();
			
//			CreateNewRepository newRepo = new CreateNewRepository();
//			newRepo.createRepositoryInLocal();
			
//			GitBranchOperation git = new GitBranchOperation();
//			git.CreateNewBranch("master", "refs/heads/", "test");
//			git.listBranches();
//			git.switchBranchInLocal("refs/heads/", "master");
			//git.getCuurentBranchName();
			//git.findAndCheckoutBranch("develop");
			
			//CloneRepository cloneRepo = new  CloneRepository();
			//cloneRepo.cloneSpecificRepository("develop");
			//cloneRepo.getCurrentRespositoryDetails();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
