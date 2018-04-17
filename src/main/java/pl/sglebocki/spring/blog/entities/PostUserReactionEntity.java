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
@IdClass(CompositeKey.class)
public class PostUserReactionEntity {
	
	public enum Reaction {
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
	@Column(name="reaction")
	private Reaction reaction;
	
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
	public Reaction getReaction() {
		return reaction;
	}
	public void setReaction(Reaction reaction) {
		this.reaction = reaction;
	}
	
}
