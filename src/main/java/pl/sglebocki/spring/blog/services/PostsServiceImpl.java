package pl.sglebocki.spring.blog.services;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.sglebocki.spring.blog.aspects.ImageManipulationTransaction;
import pl.sglebocki.spring.blog.dao.ImageDAO;
import pl.sglebocki.spring.blog.dao.PostsDAO;
import pl.sglebocki.spring.blog.dao.TransactionRollbackException;
import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
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
	public Collection<PostShowDTO> getPostsLowerThanId(Optional<String> optionalUsername, long fromId, int numberOfPosts) {
		Collection<PostEntity> postEntityCollection = postsDAO.getPostsLowerThanId(fromId, numberOfPosts);
		return getDTOcollectionFromEntityCollection(optionalUsername, postEntityCollection);
	}

	@Override
	public PostShowDTO getPostById(Optional<String> optionalUsername, long postId) {
		PostEntity postEntity = postsDAO.getPostById(postId);
		return getDTOobjectFromEntityObject(optionalUsername, postEntity);
	}
	
	@Override
	public PostShowDTO getPostByIdWithAuthentication(String uesrname, Integer postId) {
		PostEntity postEntity = postsDAO.getPostByIdWithAuthentication(uesrname, postId);
		return getDTOobjectFromEntityObject(Optional.of(uesrname), postEntity);
	}

	@Override
	@ImageManipulationTransaction
	public void saveOrUpdatePost(String username, PostSaveDTO post) {
		PostEntity postEntity;
		if(checkIfPostExists(post)) {
			postEntity = createNewEntity(username, post);
		} else {
			postEntity = updateExistingEntity(username, post);
		}
		postsDAO.saveOrUpdatePostContent(username, postEntity);
	}
	
	private boolean checkIfPostExists(PostSaveDTO post) {
		return post.getId() != 0;
	}
	
	private PostEntity createNewEntity(String username, PostSaveDTO post) {
		PostEntity postEntity = postsDAO.getPostById(post.getId());
		Optional<String> imageLink = imageDAO.saveImageAndGetImageLink(username, post.getImage());
		imageDAO.registerImageLinkToDelete(postEntity.getImageName());
		postEntity.setTitle(post.getTitle());
		postEntity.setContent(post.getContent());
		postEntity.setLastModificationTime(new Date());
		postEntity.setImageName(imageLink.orElse(null)); // TODO set default image
		return postEntity;
	}
	
	private PostEntity updateExistingEntity(String username, PostSaveDTO post) {
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
		return getDTOcollectionFromEntityCollection(Optional.of(userName), usersPosts);
	}

	@Override
	@ImageManipulationTransaction
	public void delatePostById(String username, long postId) {
		postsDAO.delatePostById(username, postId);
	}
	
	private PostShowDTO getDTOobjectFromEntityObject(Optional<String> optionalUsername, PostEntity postEntity)  {
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
		retVal.setAdditionalInfo(postsDAO.getPostAdditionalInfo(optionalUsername, postEntity.getId()));
		return retVal;
	}
	
	private Collection<PostShowDTO> getDTOcollectionFromEntityCollection(Optional<String> optionalUsername, Collection<PostEntity> postEntityCollection)  {
		return postEntityCollection
				.stream()
				.map(entityObject -> this.getDTOobjectFromEntityObject(optionalUsername, entityObject))
				.collect(Collectors.toList());
	}
}
