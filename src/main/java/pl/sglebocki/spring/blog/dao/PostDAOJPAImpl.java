package pl.sglebocki.spring.blog.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.sglebocki.spring.blog.aspects.ImageManipulationTransaction;
import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostReactionsDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;
import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;
import pl.sglebocki.spring.blog.entities.UserEntity;

@Repository
@Transactional(rollbackOn={TransactionRollbackException.class})
public class PostDAOJPAImpl implements PostsDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ImageSaver imageSaver;
	
	@Override
	public Collection<PostShowDTO> getNewestPosts(int number) {
		String queryString = "from PostEntity posts order by posts.id desc";
		TypedQuery<PostEntity> newestPostQuery = entityManager.createQuery(queryString, PostEntity.class).setMaxResults(number);
		List<PostEntity> newestPosts = newestPostQuery.getResultList();
		return getDTOcollectionFromEntitycollection(newestPosts);
	}

	@Override
	public PostShowDTO getPostById(long postId) {
		PostEntity post = null;
		post = entityManager.find(PostEntity.class, postId);
		return getDTOobjectFromEntityObject(post);
	}
	
	@Override
	public PostShowDTO getPostByIdWithAuthentication(String username, long postId) {
		checkIfPostBelongToUser(username, postId);
		PostEntity post = null;
		post = entityManager.find(PostEntity.class, postId);
		return getDTOobjectFromEntityObject(post);
	}
	
	@Override
	@ImageManipulationTransaction
	public void saveOrUpdatePostContent(String userName, PostSaveDTO post) {
		PostEntity postToMerge = getPostEntityByUserAndRequestedPost(userName, post);
		Optional<String> imageLink = imageSaver.saveImageAndGetImageLink(userName, post.getImage());
		if(imageLink.isPresent()) {
			imageSaver.registerImageLinkToDelete(postToMerge.getImageName());
			postToMerge.setImageName(imageLink.get());
		}
		entityManager.merge(postToMerge);
	}
	
	private PostEntity getPostEntityByUserAndRequestedPost(String username, PostSaveDTO post) {
		PostEntity searchedEntity = null;
		if (doesPostExist(post)) {
			searchedEntity = buildExistingPostEntity(username, post);
		} else {
			searchedEntity = buildNotExistingPostEntity(username, post);
		}
		return searchedEntity;
	}
	
	private boolean doesPostExist(PostSaveDTO post) {
		return post.getId() != 0;
	}
	
	private PostEntity buildExistingPostEntity(String username, PostSaveDTO post) {
		checkIfPostBelongToUser(username, post.getId());
		PostEntity existingEntity;
		existingEntity = entityManager.find(PostEntity.class, post.getId());
		if(existingEntity == null) {
			throw new TransactionRollbackException("Post with id " + post.getId() + " doesn't exists.");
		}
		existingEntity.setTitle(post.getTitle());
		existingEntity.setContent(post.getContent());
		existingEntity.setLastModificationTime(new Date());
		return existingEntity;
	}
	
	private PostEntity buildNotExistingPostEntity(String username, PostSaveDTO post) {
		Date creationDate = new Date();
		UserEntity author = entityManager.find(UserEntity.class, username);		
		PostEntity notExistingEntity = new PostEntity();
		notExistingEntity.setTitle(post.getTitle());
		notExistingEntity.setContent(post.getContent());
		notExistingEntity.setCreationTime(creationDate);
		notExistingEntity.setLastModificationTime(creationDate);
		notExistingEntity.setAuthor(author);
		return notExistingEntity;
	}

	@Override
	public Collection<PostShowDTO> getPostsByUserName(String username, DatePeriodDTO datePeriod) {
		String queryString = "select p from PostEntity p join p.author a where a.username = :username " +
							"and p.lastModificationTime between :fromDate and :todate " + 
							"order by p.id desc";
		TypedQuery<PostEntity> query = entityManager.createQuery(queryString, PostEntity.class);
		query.setParameter("username", username);
		query.setParameter("fromDate", datePeriod.getFromDate());
		query.setParameter("todate", datePeriod.getToDate());
		return getDTOcollectionFromEntitycollection(query.getResultList());
	}

	@Override
	@ImageManipulationTransaction
	public void delatePostById(String username, long postId) {
		checkIfPostBelongToUser(username, postId);
		PostEntity postToDelete = entityManager.find(PostEntity.class, postId);
		imageSaver.registerImageLinkToDelete(postToDelete.getImageName());
		entityManager.remove(entityManager.find(PostEntity.class, postId));
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

	private PostShowDTO getDTOobjectFromEntityObject(PostEntity postEntity)  {
		if(postEntity == null) {
			return null;
		}
		PostShowDTO retVal = new PostShowDTO();
		retVal.setId(postEntity.getId());
		retVal.setTitle(postEntity.getTitle());
		retVal.setContent(postEntity.getContent());
		retVal.setCreationTime(postEntity.getCreationTime());
		retVal.setLastModificationTime(postEntity.getLastModificationTime());
		retVal.setUsername(postEntity.getAuthor().getUsername());
		retVal.setUserAvatar(postEntity.getAuthor().getAvatar());
		retVal.setImage(postEntity.getImageName());
		retVal.setAdditionalInfo(getPostAdditionalInfo(postEntity.getId()));
		return retVal;
	}
	
	private PostReactionsDTO getPostAdditionalInfo(long postId) {
		PostReactionsDTO returnValue = new PostReactionsDTO();
		String queryString = "select " + 
							"count(CASE WHEN ur.post.id = :postId and ur.reactionType = :like THEN 1 END), " +
							"count(CASE WHEN ur.post.id = :postId and ur.reactionType = :dislike THEN 1 END) " + 
							"from PostUserReactionEntity ur";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("like", PostUserReactionEntity.ReactionType.LIKE);
		query.setParameter("dislike", PostUserReactionEntity.ReactionType.DISLIKE); 
		query.setParameter("postId", postId);
		Object[] queryResponse = (Object[])query.getSingleResult();
		returnValue.setLikes((Long)queryResponse[0]);
		returnValue.setDislikes((Long)queryResponse[1]);
		return returnValue;
	}
	
	private Collection<PostShowDTO> getDTOcollectionFromEntitycollection(Collection<PostEntity> postEntityCollection)  {
		return postEntityCollection.stream().map(this::getDTOobjectFromEntityObject).collect(Collectors.toList());
	}
	
}
