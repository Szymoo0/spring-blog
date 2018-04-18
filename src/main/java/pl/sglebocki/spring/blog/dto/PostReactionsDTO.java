package pl.sglebocki.spring.blog.dto;

public class PostReactionsDTO {
	private Long likes;
	private Long dislikes;
	private String userReaction;
	
	public PostReactionsDTO() {
		this(0, 0, null);
	}
	public PostReactionsDTO(long likes, long dislikes, String userReaction) {
		this.likes = likes;
		this.dislikes = dislikes;
		this.userReaction = userReaction;
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
	public String getUserReaction() {
		return userReaction;
	}
	public void setUserReaction(String userReaction) {
		this.userReaction = userReaction;
	}
	
}
