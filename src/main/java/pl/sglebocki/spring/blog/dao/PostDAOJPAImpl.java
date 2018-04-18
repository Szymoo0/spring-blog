package pl.sglebocki.spring.blog.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostReactionsDTO;
import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;
import pl.sglebocki.spring.blog.entities.UserEntity;

@Repository
class PostDAOJPAImpl implements PostsDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ImageDAOImpl imageSaver;
	
	@Override
	public Collection<PostEntity> getNewestPosts(int number) {
		String queryString = "from PostEntity posts order by posts.id desc";
		TypedQuery<PostEntity> newestPostQuery = entityManager.createQuery(queryString, PostEntity.class).setMaxResults(number);
		return newestPostQuery.getResultList();
	}

	@Override
	public PostEntity getPostById(long postId) {
		return entityManager.find(PostEntity.class, postId);
	}
	
	@Override
	public PostEntity getPostByIdWithAuthentication(String username, long postId) {
		checkIfPostBelongToUser(username, postId);
		return getPostById(postId);
	}
	
	@Override
	public void saveOrUpdatePostContent(String username, PostEntity postToMerge) {
		if(postToMerge.getAuthor() == null) {
			UserEntity author = entityManager.find(UserEntity.class, username);
			if(author == null) {
				throw new TransactionRollbackException("User with username " + username + " doesn't exists.");
			}
			postToMerge.setAuthor(author);
		}
		if(postToMerge.getId() != 0) {
			checkIfPostBelongToUser(username, postToMerge.getId());
		}
		entityManager.merge(postToMerge);
	}
	
	@Override
	public void delatePostById(String username, long postId) {
		checkIfPostBelongToUser(username, postId);
		PostEntity postToDelete = entityManager.find(PostEntity.class, postId);
		imageSaver.registerImageLinkToDelete(postToDelete.getImageName());
		entityManager.remove(postToDelete);
		removeUserReactionsFromPost(postId);
	}
	
	private void removeUserReactionsFromPost(long postId) {
		String removeStringQuery = "delete from PostUserReactionEntity reactions where reactions.post.id = :postId";
		Query removeQuery = entityManager.createQuery(removeStringQuery);
		removeQuery.setParameter("postId", postId);
		removeQuery.executeUpdate();
	}
	
	@Override
	public Collection<PostEntity> getPostsByUserName(String username, DatePeriodDTO datePeriod) {
		String queryString = "select p from PostEntity p join p.author a where a.username = :username " +
							"and p.lastModificationTime between :fromDate and :todate " + 
							"order by p.id desc";
		TypedQuery<PostEntity> query = entityManager.createQuery(queryString, PostEntity.class);
		query.setParameter("username", username);
		query.setParameter("fromDate", datePeriod.getFromDate());
		query.setParameter("todate", datePeriod.getToDate());
		return query.getResultList();
	}
	
	@Override
	public PostReactionsDTO getPostAdditionalInfo(Optional<String> username, long postId) {
		String queryString = "select " + 
							"count(CASE WHEN ur.post.id = :postId and ur.reactionType = :like THEN 1 END), " +
							"count(CASE WHEN ur.post.id = :postId and ur.reactionType = :dislike THEN 1 END) " + 
							"from PostUserReactionEntity ur";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("like", PostUserReactionEntity.ReactionType.LIKE);
		query.setParameter("dislike", PostUserReactionEntity.ReactionType.DISLIKE); 
		query.setParameter("postId", postId);
		Object[] queryResponse = (Object[])query.getSingleResult();
		PostReactionsDTO returnValue = new PostReactionsDTO();
		returnValue.setLikes((Long)queryResponse[0]);
		returnValue.setDislikes((Long)queryResponse[1]);
		username.ifPresent(e -> returnValue.setUserReaction(getUserReactionType(e, postId)));
		return returnValue;
	}
	
	private String getUserReactionType(String username, long postId) {
		String dbQuery = "from PostUserReactionEntity react where react.post.id = :postId and react.user.username = :username";
		TypedQuery<PostUserReactionEntity> query = entityManager.createQuery(dbQuery, PostUserReactionEntity.class);
		query.setParameter("postId", postId);
		query.setParameter("username", username);
		List<PostUserReactionEntity> reactionList = query.getResultList();
		if(reactionList.size() == 1) {
			return reactionList.get(0).getReactionType().toString().toLowerCase();
		}
		return null;
	}
	
	private void checkIfPostBelongToUser(String username, long postId) {
		PostEntity post = entityManager.find(PostEntity.class, postId);
		if(post == null) {
			throw new TransactionRollbackException("Post with id " + postId + " doesn't exists.");
		}
		if(!post.getAuthor().getUsername().equals(username)) {
			throw new TransactionRollbackException("Post with id " + postId + " doesn't belongs to " + username + ".");
		}
	}
	
}
