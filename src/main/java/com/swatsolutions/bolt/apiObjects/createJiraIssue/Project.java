package com.swatsolutions.bolt.apiObjects.createJiraIssue;

public class Project {

	//items for creating an issue
	private String id;

	//avatarUrls

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Project{" +
				"id='" + id + '\'' +
				'}';
	}
}
