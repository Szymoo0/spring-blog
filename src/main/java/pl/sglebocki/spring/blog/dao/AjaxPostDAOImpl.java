package pl.sglebocki.spring.blog.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;
import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity.ReactionType;
import pl.sglebocki.spring.blog.entities.UserEntity;

@Repository
public class AjaxPostDAOImpl implements AjaxPostDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public AjaxPostReactionsResponseDTO getPostReactions(long postId) {
		String queryString = "select " + 
				"count(CASE WHEN ur.post.id = :postId and ur.reactionType = :like THEN 1 END), " +
				"count(CASE WHEN ur.post.id = :postId and ur.reactionType = :dislike THEN 1 END) " + 
				"from PostUserReactionEntity ur";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("like", PostUserReactionEntity.ReactionType.LIKE);
		query.setParameter("dislike", PostUserReactionEntity.ReactionType.DISLIKE); 
		query.setParameter("postId", postId);
		Object[] queryResponse = (Object[])query.getSingleResult();
		return new AjaxPostReactionsResponseDTO((Long)queryResponse[0], (Long)queryResponse[1]);
	}
	
	@Override
	public AjaxPostReactionsResponseDTO processPostReactionChangeRequest(String username, AjaxPostReactionsChangeRequestDTO changeReactionRequest) {
		long postId = changeReactionRequest.getPostId();
		PostUserReactionEntity.ReactionType newReactionType = getReactionTypeFromDTO(changeReactionRequest); 

		Optional<PostUserReactionEntity> userReactionEntity = getUserReactionEntity(username, postId);
		if(userReactionEntity.isPresent()) {
			processExistingReaction(userReactionEntity.get(), newReactionType);
		} else {
			addNewReaction(username, postId, newReactionType);
		}

		return getPostReactions(postId);
	}

	private void addNewReaction(String username, long postId, ReactionType newReactionType) {
		PostUserReactionEntity newReaction = new PostUserReactionEntity();
		List<UserEntity> userList = entityManager.createQuery("from UserEntity user where user.username = '" + username + "'", UserEntity.class).getResultList();
		List<PostEntity> postList = entityManager.createQuery("from PostEntity post where post.id = " + postId, PostEntity.class).getResultList();
		if(hasListOneElement(userList) && hasListOneElement(postList)) {
			newReaction.setUser(userList.get(0));
			newReaction.setPost(postList.get(0));
		} else {
			throw new TransactionRollbackException("Can't find user or post to change reaction status.");
		}
		newReaction.setReaction(newReactionType);
		entityManager.persist(newReaction);
	}

	private ReactionType getReactionTypeFromDTO(AjaxPostReactionsChangeRequestDTO changeReactionRequest) {
		try {
			return PostUserReactionEntity.ReactionType.valueOf(changeReactionRequest.getReactionType().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new TransactionRollbackException(e);
		}
	}
	
	private Optional<PostUserReactionEntity> getUserReactionEntity(String username, long postId) {
		String dbQuery = "from PostUserReactionEntity react where react.post.id = :postId and react.user.username = :username";
		TypedQuery<PostUserReactionEntity> query = entityManager.createQuery(dbQuery, PostUserReactionEntity.class);
		query.setParameter("postId", postId);
		query.setParameter("username", username);
		List<PostUserReactionEntity> reactionList = query.getResultList();
		if(hasListOneElement(reactionList)) {
			return Optional.of(reactionList.get(0));
		}
		return Optional.empty();
	}
	
	private void processExistingReaction(PostUserReactionEntity userReactionEntity, ReactionType newReactionType) {
		if (userReactionEntity.getReactionType() == newReactionType) {
			entityManager.remove(userReactionEntity);
		} else {
			userReactionEntity.setReaction(newReactionType);
		}
	}
	
	private boolean hasListOneElement(List<?> list) {
		return list.size() == 1;
	}

}
