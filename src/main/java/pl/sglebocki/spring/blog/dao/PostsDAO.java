package pl.sglebocki.spring.blog.dao;

import java.util.Collection;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;

public interface PostsDAO {

	public Collection<PostShowDTO> getNewestPosts(int number);
	public PostShowDTO getPostById(long postId);
	public PostShowDTO getPostByIdWithAuthentication(String username, long postId);
	public void saveOrUpdatePostContent(String username, PostSaveDTO post);
	public Collection<PostShowDTO> getPostsByUserName(String username, DatePeriodDTO datePeriod);
	public void delatePostById(String username, long postId);
	
}
