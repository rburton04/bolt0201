package com.swatsolutions.bolt.apiObjects.getJiraIssue;

public class Project {

	//items for creating an issue
	private String id;

	//other items
	private String self;
	private String key;
	private String name;
	private String projectTypeKey;
	//avatarUrls

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectTypeKey() {
		return projectTypeKey;
	}

	public void setProjectTypeKey(String projectTypeKey) {
		this.projectTypeKey = projectTypeKey;
	}

	@Override
	public String toString() {
		return "Project{" +
				"id='" + id + '\'' +
				", self='" + self + '\'' +
				", key='" + key + '\'' +
				", name='" + name + '\'' +
				", projectTypeKey='" + projectTypeKey + '\'' +
				'}';
	}
}
