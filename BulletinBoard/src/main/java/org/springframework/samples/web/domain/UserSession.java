package org.springframework.samples.web.domain;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class UserSession implements Serializable {
	private User user;

	public UserSession(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
}