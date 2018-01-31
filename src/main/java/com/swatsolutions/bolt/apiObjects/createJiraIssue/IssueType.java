package com.swatsolutions.bolt.apiObjects.createJiraIssue;

public class IssueType {

	//items for creating an issue
	//only one needed
	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "issuetype{" +
				"name='" + name + '\'' +
				'}';
	}
}
