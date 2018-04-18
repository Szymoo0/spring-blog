package pl.sglebocki.spring.blog.dto.ajax;

public class AjaxPostReactionsResponseDTO {

	private long likes;
	private long dislikes;
	private String userReaction;
	
	public AjaxPostReactionsResponseDTO() {
	}
	
	public AjaxPostReactionsResponseDTO(long likes, long dislikes, String userReaction) {
		this.likes = likes;
		this.dislikes = dislikes;
		this.userReaction = userReaction;
	}
	
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public long getDislikes() {
		return dislikes;
	}
	public void setDislikes(long dislikes) {
		this.dislikes = dislikes;
	}
	public String getUserReaction() {
		return userReaction;
	}
	public void setUserReaction(String userReaction) {
		this.userReaction = userReaction;
	}

}
