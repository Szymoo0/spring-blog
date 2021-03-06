package pl.sglebocki.spring.blog.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_reaction")
@IdClass(PostUserReactionCompositeKey.class)
public class PostUserReactionEntity {
	
	public enum ReactionType {
		LIKE, DISLIKE
	}
	
	@Id
	@ManyToOne
	@JoinColumn(name="post_id")
	private PostEntity post;
	@Id
	@ManyToOne
	@JoinColumn(name="username")
	private UserEntity user;
	@Column(name="reaction_type")
	private ReactionType reactionType;
	
	public PostEntity getPost() {
		return post;
	}
	public void setPost(PostEntity post) {
		this.post = post;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public ReactionType getReactionType() {
		return reactionType;
	}
	public void setReaction(ReactionType reactionType) {
		this.reactionType = reactionType;
	}
	
}
