package pl.sglebocki.spring.blog.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;
import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;
import pl.sglebocki.spring.blog.entities.UserEntity;

@Repository
public class AjaxPostDAOImpl implements AjaxPostDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public AjaxPostReactionsResponseDTO processPostReactionChangeRequest(String username, AjaxPostReactionsChangeRequestDTO changeReactionRequest) {
		long postId = changeReactionRequest.getPostId();
		// TODO try cath here
		PostUserReactionEntity.Reaction reactionToChange = PostUserReactionEntity.Reaction.valueOf(changeReactionRequest.getReactionType().toUpperCase());
		
		TypedQuery<PostUserReactionEntity> query = entityManager.createQuery("from PostUserReactionEntity react where react.post.id = :postId and react.user.username = :username", PostUserReactionEntity.class);
		query.setParameter("postId", postId);
		query.setParameter("username", username);
		
		
		List<PostUserReactionEntity> reactionList = query.getResultList();
		
		PostUserReactionEntity reaction = null;
		if(reactionList.size() == 1) {
			reaction = reactionList.get(0);
		}
		
		if(reaction != null) {
			if (reaction.getReaction() == reactionToChange) {
				entityManager.remove(reaction);
			} else {
				reaction.setReaction(reactionToChange);
			}
		} else {
			reaction = new PostUserReactionEntity();
			// TODO try cath here GET SINGLE RESULT DOE NOT RETURN NULL - IT GIVES AN ERROR
			UserEntity user = entityManager.createQuery("from UserEntity user where user.username = '" + username + "'", UserEntity.class).getSingleResult();
			PostEntity post = entityManager.createQuery("from PostEntity post where post.id = " + postId, PostEntity.class).getSingleResult();
			reaction.setUser(user);
			reaction.setPost(post);
			reaction.setReaction(reactionToChange);
			entityManager.persist(reaction);
		}

		return getActualReactionStatus(postId);
	}

	private AjaxPostReactionsResponseDTO getActualReactionStatus(long postId) {
		String queryString = "select " + 
				"count(CASE WHEN ur.post.id = :postId and ur.reaction = :like THEN 1 END), " +
				"count(CASE WHEN ur.post.id = :postId and ur.reaction = :dislike THEN 1 END) " + 
				"from PostUserReactionEntity ur";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("like", PostUserReactionEntity.Reaction.LIKE);
		query.setParameter("dislike", PostUserReactionEntity.Reaction.DISLIKE); 
		query.setParameter("postId", postId);
		Object[] queryResponse = (Object[])query.getSingleResult();
		return new AjaxPostReactionsResponseDTO((Long)queryResponse[0], (Long)queryResponse[1]);
	}

}
