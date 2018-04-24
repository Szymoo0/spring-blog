package pl.sglebocki.spring.blog.services;

import java.security.Principal;
import java.util.Collection;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;

public interface PostsService {

	public Collection<PostShowDTO> getPostsLowerThanIdWithAdditionalInfo(int fromId, int number, Principal principal);
	public Collection<PostShowDTO> getTheBestPosts(int fromPosition, int number);
	public PostShowDTO getPostById(long postId);
	public PostShowDTO getPostByIdWithAdditionalInfo(int postId, Principal principal);
	public PostSaveDTO getPostByIdToModify(String name, Integer postId);
	public void saveOrUpdatePost(String userName,PostSaveDTO post);
	public Collection<PostShowDTO> getPostsByUserName(String userName, DatePeriodDTO datePeriod);
	public void delatePostById(String userName, long postId);
	
}
