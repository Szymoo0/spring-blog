package pl.sglebocki.spring.blog.dto;

public class PostReactionsDTO {
	private Long likes;
	private Long dislikes;
	
	public PostReactionsDTO() {
		this(0, 0);
	}
	public PostReactionsDTO(long likes, long dislikes) {
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
