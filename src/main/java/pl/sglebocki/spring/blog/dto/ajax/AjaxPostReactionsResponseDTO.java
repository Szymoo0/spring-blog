package pl.sglebocki.spring.blog.dto.ajax;

public class AjaxPostReactionsResponseDTO {

	private long likes;
	private long dislikes;
	
	public AjaxPostReactionsResponseDTO() {
	}
	
	public AjaxPostReactionsResponseDTO(long likes, long dislikes) {
		this.likes = likes;
		this.dislikes = dislikes;
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

}
