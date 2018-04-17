package pl.sglebocki.spring.blog.dto;

import java.util.Date;

public class PostShowDTO {

	// post specific
	private long id;
	private String title;
	private String content;
	private Date creationTime;
	private Date lastModificationTime;
	private String image;
	// user specific
	private String username;
	private String userAvatar;
	// user reactions
	private PostReactionsDTO additionalInfo;
	
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	public PostReactionsDTO getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(PostReactionsDTO additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	
}
