package com.swatsolutions.bolt.apiObjects.getJiraIssue;

import java.util.Arrays;

public class Fields {

	//items for creating an issue
	private Project project;
	private String summary;
	private IssueType issueType;
	private Priority priority;
	private String[] labels;
	private String environment;
	private String description;


	//other items:

	//private String timespent; - null
	//project
	private String[] fixVersions;
	//private String aggregatetimespent; - null
	//private String resolution; - null
	//private String resolutiondate; - null
	private String workratio;
	//watches
	private String lastViewed;
	private String created;
	//priority
	private String aggregatetimeoriginalestimate;
	//private String timeestimate; - null
	private String[] versions;
	private String[] issuelinks;
	//assignee
	private String updated;
	//status
	private String[] components;
	//private String timeoriginalestimate; - null
	//timetracking
	//security - null
	private String[] attachment;
	//aggregatetimeestimate - null
	//creator
	private String[] subtasks;
	//reporter
	//aggregateprogress
	private String duedate;
	//progress
	//votes
	//comment
	//worklog


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

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
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

	public String[] getFixVersions() {
		return fixVersions;
	}

	public void setFixVersions(String[] fixVersions) {
		this.fixVersions = fixVersions;
	}

	public String getWorkratio() {
		return workratio;
	}

	public void setWorkratio(String workratio) {
		this.workratio = workratio;
	}

	public String getLastViewed() {
		return lastViewed;
	}

	public void setLastViewed(String lastViewed) {
		this.lastViewed = lastViewed;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getAggregatetimeoriginalestimate() {
		return aggregatetimeoriginalestimate;
	}

	public void setAggregatetimeoriginalestimate(String aggregatetimeoriginalestimate) {
		this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
	}

	public String[] getVersions() {
		return versions;
	}

	public void setVersions(String[] versions) {
		this.versions = versions;
	}

	public String[] getIssuelinks() {
		return issuelinks;
	}

	public void setIssuelinks(String[] issuelinks) {
		this.issuelinks = issuelinks;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String[] getComponents() {
		return components;
	}

	public void setComponents(String[] components) {
		this.components = components;
	}

	public String[] getAttachment() {
		return attachment;
	}

	public void setAttachment(String[] attachment) {
		this.attachment = attachment;
	}

	public String[] getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(String[] subtasks) {
		this.subtasks = subtasks;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	@Override
	public String toString() {
		return "Fields{" +
				"project=" + project +
				", summary='" + summary + '\'' +
				", issuetype=" + issueType +
				", priority=" + priority +
				", labels=" + Arrays.toString(labels) +
				", environment='" + environment + '\'' +
				", description='" + description + '\'' +
				", fixVersions=" + Arrays.toString(fixVersions) +
				", workratio='" + workratio + '\'' +
				", lastViewed='" + lastViewed + '\'' +
				", created='" + created + '\'' +
				", aggregatetimeoriginalestimate='" + aggregatetimeoriginalestimate + '\'' +
				", versions=" + Arrays.toString(versions) +
				", issuelinks=" + Arrays.toString(issuelinks) +
				", updated='" + updated + '\'' +
				", components=" + Arrays.toString(components) +
				", attachment=" + Arrays.toString(attachment) +
				", subtasks=" + Arrays.toString(subtasks) +
				", duedate='" + duedate + '\'' +
				'}';
	}
}
