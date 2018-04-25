package pl.sglebocki.spring.blog.entities;

public class PostAdditionalInfo {

	private Long postId;
	private Long likes;
	private Long dislikes;
	private String userReaction;
	
	public PostAdditionalInfo(Long postId) {
		this(postId, 0L, 0L);
	}
	
	public PostAdditionalInfo(Long postId, Long likes, Long dislikes) {
		this.postId = postId;
		this.likes = likes;
		this.dislikes = dislikes;
		this.userReaction = "";
	}
	
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getLikes() {
		return likes;
	}
	public void setLikes(Long likes) {
		this.likes = likes;
	}
	public Long getDislikes() {
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
