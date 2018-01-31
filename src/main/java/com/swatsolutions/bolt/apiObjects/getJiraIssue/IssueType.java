package com.swatsolutions.bolt.apiObjects.getJiraIssue;

public class IssueType {

	//items for creating an issue
	//only one needed
	private String id;
	private String name;

	//other items
	private String self;
	private String description;
	private String iconUrl;
	private boolean subtask;
	private int avatarId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public boolean isSubtask() {
		return subtask;
	}

	public void setSubtask(boolean subtask) {
		this.subtask = subtask;
	}

	public int getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}

	@Override
	public String toString() {
		return "IssueType{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", self='" + self + '\'' +
				", description='" + description + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				", subtask=" + subtask +
				", avatarId=" + avatarId +
				'}';
	}
}
