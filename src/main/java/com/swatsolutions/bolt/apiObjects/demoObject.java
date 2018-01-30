package com.swatsolutions.bolt.apiObjects;

public class demoObject {

	private String self;
	private int id;
	private String description;
	private String iconUrl;
	private String name;
	private String subtask;
	private int avatarId;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubtask() {
		return subtask;
	}

	public void setSubtask(String subtask) {
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
		return "Self: " + self + " Id: " + id + " Description: " + description + " IconUrl: " + iconUrl + " Name: " +
				name + " Subtask: " + subtask + " AvatarId: " + avatarId;
	}
}
