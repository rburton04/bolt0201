package com.swatsolutions.bolt.apiObjects.getJiraIssue;

public class Priority {

	//items for creating an issue
	//only one needed
	private String name;
	private String id;

	//other items
	private String self;
	private String iconUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Override
	public String toString() {
		return "Priority{" +
				"name='" + name + '\'' +
				", id='" + id + '\'' +
				", self='" + self + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				'}';
	}
}
