package de.patrickdassler.suzkos.utility.httpbodies;

public class SUZ_Task {

	private String id; 
	private String title;
	private String type;
	private String workingDirectory;
	private String taskmanager;
	private String[] contributors;
	
	public SUZ_Task() {
		
	}
	
	public SUZ_Task(String id, String title, String type, String workingDirectory, String taskmanager, String[] contributors) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
		this.workingDirectory = workingDirectory;
		this.taskmanager = taskmanager;
		this.contributors = contributors;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTaskmanager() {
		return taskmanager;
	}
	public void setTaskmanager(String taskmanager) {
		this.taskmanager = taskmanager;
	}
	public String[] getContributors() {
		return contributors;
	}
	public void setContributors(String[] contributors) {
		this.contributors = contributors;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
}
