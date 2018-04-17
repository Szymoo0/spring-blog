package pl.sglebocki.spring.blog.services;

import java.util.Collection;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;

public interface PostsService {

	public Collection<PostShowDTO> getNewestPosts(int number);
	public PostShowDTO getPostById(int postId);
	public PostShowDTO getPostByIdWithAuthentication(String name, Integer postId);
	public void saveOrUpdatePost(String userName,PostSaveDTO post);
	public Collection<PostShowDTO> getPostsByUserName(String userName, DatePeriodDTO datePeriod);
	public void delatePostById(String userName, int postId);
	
}
