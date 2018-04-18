package pl.sglebocki.spring.blog.dto.ajax;

import javax.validation.constraints.NotNull;

public class AjaxPostReactionsChangeRequestDTO {

	@NotNull
	private long postId;
	@NotNull
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
