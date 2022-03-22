package com.ssafy.ws.dto;

public class User {

	private String id;
	private String name;
	private String pass;
	private String recId;
	
	public User() {}

	public User(String id, String name, String pass, String recId) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.recId = recId;
	}

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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", name=").append(name).append(", pass=").append(pass)
				.append(", recId=").append(recId).append("]");
		return builder.toString();
	}
	
}
