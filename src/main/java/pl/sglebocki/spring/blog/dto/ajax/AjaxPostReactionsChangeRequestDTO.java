package pl.sglebocki.spring.blog.dto.ajax;

public class AjaxPostReactionsChangeRequestDTO {

	private long postId;
	private String reactionType;
	
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public String getReactionType() {
		return reactionType;
	}
	public void setReactionType(String reactionType) {
		this.reactionType = reactionType;
	}
	
}
