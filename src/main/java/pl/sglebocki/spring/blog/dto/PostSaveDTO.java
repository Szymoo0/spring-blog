package pl.sglebocki.spring.blog.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import pl.sglebocki.spring.blog.entities.PostEntity;

public class PostSaveDTO {

	private long id;
	@NotNull
	@Size(min=2)
	private String title;
	@NotNull
	@Size(min=2)
	private String content;
	private Date creationTime;
	private Date lastModificationTime;
	private String username;
	private String avatarURL;
	
	private MultipartFile image;
	String imageShow;
	
	public PostSaveDTO() {}
	public PostSaveDTO(PostEntity post) {
		id = post.getId();
		title = post.getTitle();
		content = post.getContent();
		creationTime = post.getCreationTime();
		lastModificationTime = post.getLastModificationTime();
		username = post.getAuthor().getUsername();
		avatarURL = post.getAuthor().getAvatar();
		imageShow = post.getImageName();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getLastModificationTime() {
		return lastModificationTime;
	}
	public void setLastModificationTime(Date lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatarURL() {
		return avatarURL;
	}
	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}
	
	
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
	
	public String getImageShow() {
		return imageShow;
	}
	public void setImageShow(String imageShow) {
		this.imageShow = imageShow;
	}
	
	@Override
	public String toString() {
		return "PostMvc [id=" + id + ", title=" + title + ", content=" + content + ", creationTime=" + creationTime
				+ ", lastModificationTime=" + lastModificationTime + ", username=" + username + ", avatarURL="
				+ avatarURL + "]";
	}

}
