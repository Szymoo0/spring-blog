package pl.sglebocki.spring.blog.dao;

import java.util.function.BiFunction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;

class IdDescendingStrategy implements BiFunction<Integer, Integer, TypedQuery<PostEntity>> {
	
	private EntityManager entityManager;
	
	IdDescendingStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public TypedQuery<PostEntity> apply(Integer fromPostId, Integer number) {
		TypedQuery<PostEntity> newestPostQuery;
		if(chekIfShouldGenerateNewestPosts(fromPostId)) {
			newestPostQuery = getNewestPostsQuery(number);
		} else {
			newestPostQuery = getPostsFromIdQuery(fromPostId, number);
		}
		return newestPostQuery;
	}
	
	private boolean chekIfShouldGenerateNewestPosts(int fromPostId) {
		return fromPostId <= 0;
	}
	
	private TypedQuery<PostEntity> getNewestPostsQuery(int number) {
		String queryString = "from PostEntity posts order by posts.id desc";
		return entityManager.createQuery(queryString, PostEntity.class).setMaxResults(number);
	}
	
	private TypedQuery<PostEntity> getPostsFromIdQuery(int fromPostId, int number) {
		String queryString = "from PostEntity posts where :fromPostId > posts.id order by posts.id desc";
		TypedQuery<PostEntity> newestPostQuery = entityManager.createQuery(queryString, PostEntity.class).setMaxResults(number);
		newestPostQuery.setParameter("fromPostId", (long)fromPostId);
		return newestPostQuery;
	}
	
}

class FromTheBestToWorstStrategy implements BiFunction<Integer, Integer, TypedQuery<PostEntity>> {

	private EntityManager entityManager;
	
	FromTheBestToWorstStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public TypedQuery<PostEntity> apply(Integer fromPostPosition, Integer number) {
		TypedQuery<PostEntity> newestPostQuery;
		if(chekIfShouldGenerateTheBestPosts(fromPostPosition)) {
			newestPostQuery = getTheBestPostsQuery(number);
		} else {
			newestPostQuery = getTheBestPostsFromPositionQuery(fromPostPosition, number);
		}
		return newestPostQuery;
	}
	
	private boolean chekIfShouldGenerateTheBestPosts(int fromPostPosition) {
		return fromPostPosition <= 0;
	}
	
	private TypedQuery<PostEntity> getTypedQueryWithoutLimits(int number) {
		String queryString = "select posts from PostEntity posts, PostUserReactionEntity reactions where reactions.post.id = posts.id " +
				"group by posts.id order by " + 
				"  count(CASE WHEN reactions.reactionType = :like THEN 1 END) " + 
				"- count(CASE WHEN reactions.reactionType = :dislike THEN 1 END) " + 
				"desc, posts.id desc";
		TypedQuery<PostEntity> query = entityManager.createQuery(queryString, PostEntity.class);
		query.setParameter("like", PostUserReactionEntity.ReactionType.LIKE);
		query.setParameter("dislike", PostUserReactionEntity.ReactionType.DISLIKE); 
		return query;
	}

	private TypedQuery<PostEntity> getTheBestPostsQuery(int number) {
		TypedQuery<PostEntity> query = getTypedQueryWithoutLimits(number);
		query.setMaxResults(number);
		return query;
	}
	
	private TypedQuery<PostEntity> getTheBestPostsFromPositionQuery(int fromPostPosition, int number) {
		TypedQuery<PostEntity> query = getTypedQueryWithoutLimits(number);
		query.setMaxResults(number).setFirstResult(fromPostPosition);
		return query;
	}
	
}