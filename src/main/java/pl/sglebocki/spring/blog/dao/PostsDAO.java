package pl.sglebocki.spring.blog.dao;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.entities.PostAdditionalInfo;
import pl.sglebocki.spring.blog.entities.PostEntity;
import pl.sglebocki.spring.blog.entities.PostUserReactionEntity;

public interface PostsDAO {
	
	public enum PostPickerStrategy {
		BY_ID,
		ID_DESCENDING,
		FROM_THE_BEST_TO_WORST,
	}
	
	public Collection<PostEntity> getPostsByStrategy(int fromPost, int number, PostPickerStrategy strategy);
	public Collection<PostAdditionalInfo> getPostAdditionalInfo(Collection<Long> postIdCollection, Optional<Principal> username);
	public PostEntity getPostById(long postId);
	public PostEntity getPostByIdWithAuthentication(String username, long postId);
	public void saveOrUpdatePostContent(String username, PostEntity post);
	public void delatePostById(String username, long postId);
	public Collection<PostEntity> getPostsByUserName(String username, DatePeriodDTO datePeriod);
	public void changeUserPostReaction(String username, Long postId, PostUserReactionEntity.ReactionType newReactionType);
}
