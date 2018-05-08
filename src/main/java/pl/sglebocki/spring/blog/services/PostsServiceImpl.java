package pl.sglebocki.spring.blog.services;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import pl.sglebocki.spring.blog.aspects.ImageManipulationTransaction;
import pl.sglebocki.spring.blog.dao.ImageDAO;
import pl.sglebocki.spring.blog.dao.PostsDAO;
import pl.sglebocki.spring.blog.dao.TransactionRollbackException;
import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostReactionsDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.entities.PostAdditionalInfo;
import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;

@Service
@Transactional(rollbackOn={TransactionRollbackException.class})
class PostsServiceImpl implements PostsService {

	@Autowired
	private PostsDAO postsDAO;
	
	@Autowired
	private ImageDAO imageDAO;

	@Override
	public Collection<PostShowDTO> getPostsLowerThanIdWithAdditionalInfo(int fromId, int numberOfPosts, Principal principal) {
		Collection<PostEntity> postEntityCollection = getPostEntities(fromId, numberOfPosts, PostsDAO.PostPickerStrategy.ID_DESCENDING);
		Collection<PostAdditionalInfo> postAdditionalInfoCollection = getPostAdditionalInfos(postEntityCollection, principal);
		Collection<Pair<PostEntity, PostAdditionalInfo>> postWithInfoCollection = getPairsCollection(postEntityCollection, postAdditionalInfoCollection);
		return getDTOcollectionFromEntityPairCollection(postWithInfoCollection);
	}

	private Collection<PostEntity> getPostEntities(int fromId, int numberOfPosts, PostsDAO.PostPickerStrategy idDescending) {
		return postsDAO.getPostsByStrategy(
				fromId,
				numberOfPosts,
				idDescending);
	}

	private Collection<PostAdditionalInfo> getPostAdditionalInfos(Collection<PostEntity> postEntityCollection, Principal principal) {
		return postsDAO.getPostsAdditionalInfo(
					postEntityCollection.stream()
					.map(e -> e.getId())
					.collect(Collectors.toList()),
					Optional.ofNullable(principal));
	}

	private Collection<Pair<PostEntity, PostAdditionalInfo>> getPairsCollection(Collection<PostEntity> postEntityCollection, Collection<PostAdditionalInfo> postAdditionalInfoCollection) {
		return postEntityCollection.stream()
				.map(e -> Pair.of(e, postAdditionalInfoCollection.stream()
						.filter(f -> f.getPostId() == e.getId())
						.findFirst()
						.orElse(new PostAdditionalInfo(e.getId())))
				).collect(Collectors.toList());
	}

	@Override
	public Collection<PostShowDTO> getTheBestPosts(int fromPosition, int numberOfPosts) {
		Collection<PostEntity> postEntityCollection = getPostEntities(fromPosition, numberOfPosts, PostsDAO.PostPickerStrategy.FROM_THE_BEST_TO_WORST);
		return getDTOcollectionFromEntityCollection(postEntityCollection);
	}

	@Override
	public PostShowDTO getPostById(long postId) {
		return postsDAO.findById(postId)
				.map(entity -> getDTOobjectFromEntityObject(entity))
				.orElse(null);
	}
	
	@Override
	public PostShowDTO getPostByIdWithAdditionalInfo(long postId, Principal principal) {
		PostEntity postEntity = postsDAO.findById(postId).orElse(null);
		if (postEntity == null) {
			return null;
		}
		Collection<PostAdditionalInfo> postAdditionalInfoCollection = postsDAO.getPostsAdditionalInfo(
				Arrays.asList(postEntity.getId()),
				Optional.ofNullable(principal));
		if(postAdditionalInfoCollection.size() != 1) {
			return null;
		}
		return getDTOobjectFromEntityPairObject(Pair.of(postEntity, postAdditionalInfoCollection.iterator().next()));
	}

	@Override
	public PostSaveDTO getPostByIdToModify(long postId) {
		PostEntity postEntity = postsDAO.findByIdWithAuth(postId).orElseThrow(() -> new TransactionRollbackException("No post with id " + postId));
		PostSaveDTO post = new PostSaveDTO();
		post.setId(postEntity.getId());
		post.setTitle(postEntity.getTitle());
		post.setContent(postEntity.getContent());
		post.setPresentImageUrl(postEntity.getImageName());			
		return post;
	}

	@Override
	@ImageManipulationTransaction
	public void saveOrUpdatePost(String username, PostSaveDTO post) {
		PostEntity postEntity;
		if(checkIfPostExists(post)) {
			postEntity = updateExistingEntity(username, post);
		} else {
			postEntity = createNewEntity(username, post);
		}
		postsDAO.saveOrUpdatePost(username, postEntity);
	}
	
	private boolean checkIfPostExists(PostSaveDTO post) {
		return post.getId() != 0;
	}
	
	private PostEntity updateExistingEntity(String username, PostSaveDTO post) {
		PostEntity postEntity = postsDAO.findById(post.getId()).orElseThrow(() -> new TransactionRollbackException("Post with id " + post.getId() + " not found"));
		Optional<String> imageLink = imageDAO.saveImageAndGetImageLink(username, post.getImage());
		postEntity.setTitle(post.getTitle());
		postEntity.setContent(post.getContent());
		postEntity.setLastModificationTime(new Date());
		registerPresentImageToDeleteIfNessesery(postEntity, post, imageLink);
		postEntity.setImageName(imageLink.orElse(post.getPresentImageUrl())); // TODO set default image
		return postEntity;
	}
	
	private void registerPresentImageToDeleteIfNessesery(PostEntity postEntity, PostSaveDTO post, Optional<String> imageLink) {
		if(post.isDeletePresentImageIfExists()) {
			imageDAO.registerImageLinkToDelete(postEntity.getImageName());
			post.setPresentImageUrl(null);
		}
		imageLink.ifPresent(e -> imageDAO.registerImageLinkToDelete(postEntity.getImageName()) );
	}
	
	private PostEntity createNewEntity(String username, PostSaveDTO post) {
		PostEntity postEntity = new PostEntity();
		Date creationDate = new Date();	
		Optional<String> imageLink = imageDAO.saveImageAndGetImageLink(username, post.getImage());
		postEntity.setTitle(post.getTitle());
		postEntity.setContent(post.getContent());
		postEntity.setCreationTime(creationDate);
		postEntity.setLastModificationTime(creationDate);
		postEntity.setImageName(imageLink.orElse(null)); // TODO set default image
		return postEntity;
	}

	@Override
	public Collection<PostShowDTO> getPostsByUserName(String userName, DatePeriodDTO datePeriod) {
		Collection<PostEntity> usersPosts = postsDAO.getPostsByUserName(userName, datePeriod);
		return getDTOcollectionFromEntityCollection(usersPosts);
	}

	@Override
	@ImageManipulationTransaction
	public void delatePostById(String username, long postId) {
		postsDAO.delatePostById(username, postId);
	}
	
	private PostShowDTO getDTOobjectFromEntityObject(PostEntity postEntity)  {
		if (postEntity == null) { 
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
		return retVal;
	}
	
	private Collection<PostShowDTO> getDTOcollectionFromEntityCollection(Collection<PostEntity> postEntityCollection)  {
		return postEntityCollection
				.stream()
				.map(this::getDTOobjectFromEntityObject)
				.collect(Collectors.toList());
	}

	private PostShowDTO getDTOobjectFromEntityPairObject(Pair<PostEntity, PostAdditionalInfo> postEntityPair)  {
		PostEntity postEntity = postEntityPair.getFirst();
		PostAdditionalInfo additionalInfo = postEntityPair.getSecond();
		PostShowDTO retVal = getDTOobjectFromEntityObject(postEntity);
		if (retVal != null) {
			PostReactionsDTO addtitionalInfoDTO = new PostReactionsDTO(additionalInfo.getLikes(), additionalInfo.getDislikes());
			addtitionalInfoDTO.setUserReaction(additionalInfo.getUserReaction());
			retVal.setAdditionalInfo(addtitionalInfoDTO);
		}
		return retVal;
	}
	
	private Collection<PostShowDTO> getDTOcollectionFromEntityPairCollection(Collection<Pair<PostEntity, PostAdditionalInfo>> postEntityPairCollection)  {
		return postEntityPairCollection
				.stream()
				.map(this::getDTOobjectFromEntityPairObject)
				.collect(Collectors.toList());
	}

}
