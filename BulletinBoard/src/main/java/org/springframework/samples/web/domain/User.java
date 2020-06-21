package org.springframework.samples.web.domain;


import javax.validation.constraints.Size;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

//여기에 비즈니스 로직들을 처리해야 좋음. (도메인 클래스로)
@Alias("user") //해당하는 매퍼는 이렇게 지정하면 됨. 
public class User {
	@Size(min=4, max=12)//@NotNull @Min(4) @Max(12) null이면 안되고, 최소 4 최대 12이다. 
	private String userId;
	@NotEmpty @Size(min=4, max=12)
	private String password;
	@NotEmpty @Size(min=1, max=12)
	private String nickname;
	@NotEmpty
	private String name;
	@Email
	private String email;

	public User() {
		
	}
	public User(String userId, String password, String nickname, String name, String email) {
		super();
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.email = email;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public boolean matchPassword2(String pwd) {
		// TODO Auto-generated method stub
		if(this.password == null){
			return false;
		}
		return this.password.equals(pwd);
	}
	
	public boolean matchPassword(Authenticate authenticate) {
		// TODO Auto-generated method stub
		if(this.password == null){
			return false;
		}
		return this.password.equals(authenticate.getPassword());
	}

	public boolean matchUserId(String inputUserId) {
		// TODO Auto-generated method stub
		if (inputUserId == null){
			return false;
		}
		return inputUserId.equals(this.userId);
	}

	public User update(User updateUser) {
		// TODO Auto-generated method stub
		if (!matchUserId(updateUser.userId)){
			throw new IllegalArgumentException();
		}
		return new User(this.userId, updateUser.password, updateUser.nickname, updateUser.name, updateUser.email);
	}	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	//이 형태로 콘솔창에 출력하게 된다.
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", nickname=" + nickname + ", name=" + name
				+ ", email=" + email + "]";
	}

}
