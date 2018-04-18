package pl.sglebocki.spring.blog.dao;

import java.util.Collection;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostReactionsDTO;
import pl.sglebocki.spring.blog.entities.PostEntity;

public interface PostsDAO {

	public Collection<PostEntity> getNewestPosts(int number);
	public PostEntity getPostById(long postId);
	public PostEntity getPostByIdWithAuthentication(String username, long postId);
	public void saveOrUpdatePostContent(String username, PostEntity post);
	public void delatePostById(String username, long postId);
	public Collection<PostEntity> getPostsByUserName(String username, DatePeriodDTO datePeriod);
	public PostReactionsDTO getPostAdditionalInfo(long postId);
	
}
