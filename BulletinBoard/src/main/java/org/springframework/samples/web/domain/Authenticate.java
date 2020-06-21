package org.springframework.samples.web.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class Authenticate {
	@NotEmpty
	private String userId;
	@NotEmpty
	private String password;
	
	public Authenticate(){
	
	}
	
	public Authenticate(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Authenticate [userId=" + userId + ", password=" + password + "]";
	}
	
	
	
}
