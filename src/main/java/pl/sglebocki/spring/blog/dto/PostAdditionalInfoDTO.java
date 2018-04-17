package pl.sglebocki.spring.blog.dto;

public class PostAdditionalInfoDTO {
	private Long likes;
	private Long dislikes;
	
	public PostAdditionalInfoDTO() {
		this(0, 0);
	}
	public PostAdditionalInfoDTO(long likes, long dislikes) {
		this.likes = likes;
		this.dislikes = dislikes;
	}
	
	public long getLikes() {
		return likes;
	}
	public void setLikes(Long likes) {
		this.likes = likes;
	}
	public long getDislikes() {
		return dislikes;
	}
	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}
	
	
}
