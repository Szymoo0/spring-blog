package pl.sglebocki.spring.blog.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

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
	private String presentImageUrl;
	private boolean deletePresentImageIfExists;
	private MultipartFile image;

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
	public String getPresentImageUrl() {
		return presentImageUrl;
	}
	public void setPresentImageUrl(String presentImageUrl) {
		this.presentImageUrl = presentImageUrl;
	}
	public boolean isDeletePresentImageIfExists() {
		return deletePresentImageIfExists;
	}
	public void setDeletePresentImageIfExists(boolean deletePresentImageIfExists) {
		this.deletePresentImageIfExists = deletePresentImageIfExists;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
