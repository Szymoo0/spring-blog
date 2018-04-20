package pl.sglebocki.spring.blog.dao;

import java.util.Collection;
import java.util.Optional;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostReactionsDTO;
import pl.sglebocki.spring.blog.entities.PostEntity;

public interface PostsDAO {
	
	public enum PostPickerStrategy {
		ID_DESCENDING,
		FROM_THE_BEST_TO_WORST,
	}
	
	public Collection<PostEntity> getPostsByStrategy(int fromPost, int number, PostPickerStrategy strategy);
	public PostEntity getPostById(long postId);
	public PostEntity getPostByIdWithAuthentication(String username, long postId);
	public void saveOrUpdatePostContent(String username, PostEntity post);
	public void delatePostById(String username, long postId);
	public Collection<PostEntity> getPostsByUserName(String username, DatePeriodDTO datePeriod);
	public PostReactionsDTO getPostAdditionalInfo(Optional<String> username, long postId);
	
}
