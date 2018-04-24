package pl.sglebocki.spring.blog.dao;

import java.util.function.BiFunction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;


interface PostDAOsearchStrategy extends BiFunction<Integer, Integer, TypedQuery<PostEntity>>{
}

class GetByIdStrategy implements PostDAOsearchStrategy {

	private EntityManager entityManager;
	
	GetByIdStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public TypedQuery<PostEntity> apply(Integer postId, Integer unused) {
		String queryString = "from PostEntity posts join fetch posts.author where :postId = posts.id";
		TypedQuery<PostEntity> postQuery = entityManager.createQuery(queryString, PostEntity.class);
		postQuery.setParameter("postId", (long)postId);
		return postQuery;
	}
	
}

class IdDescendingStrategy implements PostDAOsearchStrategy {
	
	private EntityManager entityManager;
	
	IdDescendingStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public TypedQuery<PostEntity> apply(Integer fromPostId, Integer number) {
		fromPostId = setProperFromPostIdValue(fromPostId);
		return getNewestPostsTypedQuery(fromPostId, number);
	}
	
	// if fromPostId is 'le than 0' then we are searching from the newest posts
	private Integer setProperFromPostIdValue(Integer fromPostId) {
		return fromPostId <= 0 ? Integer.MAX_VALUE : fromPostId;
	}
	
	private TypedQuery<PostEntity> getNewestPostsTypedQuery(int fromPostId, int number) {
		String queryString = "from PostEntity posts join fetch posts.author where :fromPostId > posts.id order by posts.id desc";
		TypedQuery<PostEntity> newestPostQuery = entityManager.createQuery(queryString, PostEntity.class).setMaxResults(number);
		newestPostQuery.setParameter("fromPostId", (long)fromPostId);
		return newestPostQuery;
	}
	
}

class FromTheBestToWorstStrategy implements PostDAOsearchStrategy {

	private EntityManager entityManager;
	
	FromTheBestToWorstStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public TypedQuery<PostEntity> apply(Integer fromPostPosition, Integer number) {
		TypedQuery<PostEntity> theBestPostQuery = getTypedQueryWithMaxResultLimit(number);
		if(chekIfShouldGenerateFromPostPosition(fromPostPosition)) {
			addFromPositionToTypedQuery(fromPostPosition, theBestPostQuery);
		}
		return theBestPostQuery;
	}
	
	// if fromPostId is 'le than 0' then we are searching posts from given post position
	private boolean chekIfShouldGenerateFromPostPosition(int fromPostPosition) {
		return fromPostPosition <= 0;
	}
	
	private TypedQuery<PostEntity> getTypedQueryWithMaxResultLimit(int number) {
		String queryString = "select posts from PostEntity posts, PostUserReactionEntity reactions join fetch posts.author where reactions.post.id = posts.id " +
				"group by posts.id order by " + 
				"  count(CASE WHEN reactions.reactionType = :like THEN 1 END) " + 
				"- count(CASE WHEN reactions.reactionType = :dislike THEN 1 END) " + 
				"desc, posts.id desc";
		TypedQuery<PostEntity> query = entityManager.createQuery(queryString, PostEntity.class);
		query.setParameter("like", PostUserReactionEntity.ReactionType.LIKE);
		query.setParameter("dislike", PostUserReactionEntity.ReactionType.DISLIKE); 
		query.setMaxResults(number);
		return query;
	}

	private void addFromPositionToTypedQuery(Integer fromPostPosition, TypedQuery<PostEntity> theBestPostQuery) {
		theBestPostQuery.setFirstResult(fromPostPosition);
	}

}