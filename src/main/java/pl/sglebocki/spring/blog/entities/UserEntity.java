package pl.sglebocki.spring.blog.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserEntity {

	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="avatar_url")
	private String avatar;
	
	@OneToMany(mappedBy="author",
			   fetch=FetchType.LAZY,
			   cascade=CascadeType.ALL)
	private Set<PostEntity> posts;
	
	public UserEntity() {
	}
	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}
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
	public Set<PostEntity> getPosts() {
		return posts;
	}
	public void setPosts(Set<PostEntity> posts) {
		this.posts = posts;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		return "UserEntity [username=" + username + ", password=" + password + "]";
	}


}
