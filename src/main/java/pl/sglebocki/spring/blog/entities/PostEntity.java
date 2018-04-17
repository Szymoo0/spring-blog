package pl.sglebocki.spring.blog.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="post")
public class PostEntity {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="content")
	private String content;
	
	@Column(name="creation_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	
	@Column(name="last_modification_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModificationTime;
	
	@Column(name="image")
	private String imageName;
	
	@ManyToOne(fetch=FetchType.EAGER,
			   cascade= {CascadeType.DETACH, 
					   CascadeType.MERGE,
					   CascadeType.PERSIST,
					   CascadeType.REFRESH
			   })
    @JoinColumn(name="author_username")
	private UserEntity author;
	
	public PostEntity() {
	}
	public PostEntity(long id, String title, String content, Date creationTime, Date lastModificationTime, UserEntity author) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.creationTime = creationTime;
		this.lastModificationTime = lastModificationTime;
		this.author = author;
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
	public UserEntity getAuthor() {
		return author;
	}
	public void setAuthor(UserEntity author) {
		this.author = author;
	}
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
}
