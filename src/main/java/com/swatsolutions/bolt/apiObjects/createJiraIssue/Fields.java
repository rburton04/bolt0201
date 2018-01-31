package com.swatsolutions.bolt.apiObjects.createJiraIssue;

import java.util.Arrays;

public class Fields {

	//items for creating an issue
	private Project project;
	private String summary;
	private IssueType issuetype;
	private Priority priority;
	private String[] labels;
	private String environment;
	private String description;


	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public IssueType getIssuetype() {
		return issuetype;
	}

	public void setIssuetype(IssueType issuetype) {
		this.issuetype = issuetype;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	@Override
	public String toString() {
		return "Fields{" +
				"project=" + project +
				", summary='" + summary + '\'' +
				", issuetype=" + issuetype +
				", priority=" + priority +
				", labels=" + Arrays.toString(labels) +
				", environment='" + environment + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
