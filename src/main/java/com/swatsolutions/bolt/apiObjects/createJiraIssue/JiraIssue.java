package com.swatsolutions.bolt.apiObjects.createJiraIssue;

public class JiraIssue {

	//issue creation:
	private Fields fields;

	public enum PriorityOptions {Highest, High, Medium, Low, Lowest}
	public enum IssueTypeOptions {Bug, Task, Story, Epic}

	public JiraIssue(PriorityOptions priority, IssueTypeOptions issueType, String projectId, String summary, String[] labels, String environment, String description){
		fields = new Fields();

		IssueType issueTypeObj = new IssueType();
		Priority priorityObj = new Priority();
		Project projectObj = new Project();

		priorityObj.setName(priority.toString());
		issueTypeObj.setName(issueType.toString());
		projectObj.setId(projectId);

		fields.setPriority(priorityObj);
		fields.setIssuetype(issueTypeObj);
		fields.setProject(projectObj);

		fields.setSummary(summary);
		fields.setLabels(labels);
		fields.setEnvironment(environment);
		fields.setDescription(description);

		setFields(fields);
	}

	public Fields getFields() {
		return fields;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}


	@Override
	public String toString() {
		return "JiraIssue{" +
				"fields=" + fields +
				'}';
	}
}
