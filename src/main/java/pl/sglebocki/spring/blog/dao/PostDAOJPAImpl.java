package pl.sglebocki.spring.blog.dao;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.entities.PostAdditionalInfo;
import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;
import pl.sglebocki.spring.blog.entities.UserEntity;

@Repository
class PostDAOJPAImpl implements PostsDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ImageDAOImpl imageSaver;
	
	private Map<PostsDAO.PostPickerStrategy, PostDAOsearchStrategy> strategyMap;
	
	@PostConstruct
	void postConstruct() {
		IdentityHashMap<PostsDAO.PostPickerStrategy, PostDAOsearchStrategy> unmodifiableMap = new IdentityHashMap<>();
		unmodifiableMap.put(PostsDAO.PostPickerStrategy.BY_ID, new GetByIdStrategy(entityManager));
		unmodifiableMap.put(PostsDAO.PostPickerStrategy.ID_DESCENDING, new IdDescendingStrategy(entityManager));
		unmodifiableMap.put(PostsDAO.PostPickerStrategy.FROM_THE_BEST_TO_WORST, new FromTheBestToWorstStrategy(entityManager));
		strategyMap = Collections.unmodifiableMap(unmodifiableMap);
	}

	@Override
	public Collection<PostEntity> getPostsByStrategy(int fromPost, int number, PostPickerStrategy strategy) {
		TypedQuery<PostEntity> query = strategyMap.get(strategy).apply(fromPost, number);
		return query.getResultList();
	}
	
	@Override
	public Collection<PostAdditionalInfo> getPostAdditionalInfo(Collection<Long> postIdCollection, Optional<Principal> principal) {
		Collection<PostAdditionalInfo> additionalInfoCollection = loadCustomAdditionalInfo(postIdCollection);
		principal.ifPresent(p -> loadPrincipalSpecificAdditionalInfo(additionalInfoCollection, p));
		return additionalInfoCollection;
	}
	
	private Collection<PostAdditionalInfo> loadCustomAdditionalInfo(Collection<Long> postIdCollection) {
		String queryString = "select " +
				 "new pl.sglebocki.spring.blog.entities.PostAdditionalInfo " +
				 "( " +
				 	"ur.post.id, " +
				 	"count(CASE WHEN ur.reactionType = :like THEN 1 END), " +
				 	"count(CASE WHEN ur.reactionType = :dislike THEN 1 END)  " +
				 ") " +
				 "from PostUserReactionEntity ur " +
				 "where ur.post.id in :postIdCollection " + 
				 "group by ur.post.id";
		TypedQuery<PostAdditionalInfo> query = entityManager.createQuery(queryString, PostAdditionalInfo.class);
		query.setParameter("like", PostUserReactionEntity.ReactionType.LIKE);
		query.setParameter("dislike", PostUserReactionEntity.ReactionType.DISLIKE); 
		query.setParameter("postIdCollection", postIdCollection);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	private void loadPrincipalSpecificAdditionalInfo(Collection<PostAdditionalInfo> additionalInfoCollection, Principal principal) {
		Collection<Long> postIdCollection = additionalInfoCollection.stream().map(e -> e.getPostId()).collect(Collectors.toList());
		String queryString = "select ur.post.id, ur.reactionType from PostUserReactionEntity ur where ur.post.id in :postIdCollection";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("postIdCollection", postIdCollection);
		Collection<Object[]> userReactions = query.getResultList();
		userReactions.forEach(e -> {
			additionalInfoCollection.stream()
			.filter(f -> f.getPostId() == e[0])
			.findAny()
			.ifPresent(f -> f.setUserReaction(e[1].toString().toLowerCase()));
		});		
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
