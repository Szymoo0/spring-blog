package pl.sglebocki.spring.blog.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

public class UserSaveDTO {

	@NotNull
	@Pattern(regexp="[a-zA-Z0-9]{7,}")
	private String username;
	@NotNull
	@Pattern(regexp="[a-zA-Z0-9@#$%^&+=]{7,}")
	private String password;
	private MultipartFile avatar;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MultipartFile getAvatar() {
		return avatar;
	}
	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

}
