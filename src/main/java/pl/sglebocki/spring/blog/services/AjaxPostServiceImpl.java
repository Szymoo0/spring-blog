package pl.sglebocki.spring.blog.services;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.sglebocki.spring.blog.dao.PostsDAO;
import pl.sglebocki.spring.blog.dao.TransactionRollbackException;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;
import pl.sglebocki.spring.blog.entities.PostAdditionalInfo;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity.ReactionType;

@Service
@Transactional(rollbackOn={TransactionRollbackException.class})
class AjaxPostServiceImpl implements AjaxPostService {

	@Autowired
	private PostsDAO postsDAO;
	
	@Override
	public AjaxPostReactionsResponseDTO processPostReactionChangeRequest(Principal principal, AjaxPostReactionsChangeRequestDTO changeReactionRequest) {
		ReactionType newReactionType = getReactionTypeFromDTO(changeReactionRequest);
		postsDAO.changeUserPostReaction(principal.getName(), changeReactionRequest.getPostId(), newReactionType);
		Collection<PostAdditionalInfo> postAdditionalInfoCollection = postsDAO.getPostAdditionalInfo(Arrays.asList(changeReactionRequest.getPostId()), Optional.of(principal));
		if(postAdditionalInfoCollection.size() != 1) {
			throw new TransactionRollbackException("Work on it"); // TODO work on it
		}
		PostAdditionalInfo postAdditionalInfo = postAdditionalInfoCollection.iterator().next();
		AjaxPostReactionsResponseDTO returnJSONvalue = new AjaxPostReactionsResponseDTO(
				postAdditionalInfo.getLikes(), 
				postAdditionalInfo.getDislikes(), 
				newReactionType.toString().toLowerCase());
		return returnJSONvalue;
	}
	
	private ReactionType getReactionTypeFromDTO(AjaxPostReactionsChangeRequestDTO changeReactionRequest) {
		try {
			return PostUserReactionEntity.ReactionType.valueOf(changeReactionRequest.getReactionType().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new TransactionRollbackException(e);
		}
	}

}
