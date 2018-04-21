package pl.sglebocki.spring.blog.services;

import java.util.Collection;
import java.util.Optional;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;

public interface PostsService {

	public Collection<PostShowDTO> getPostsLowerThanId(Optional<String> optionalUsername, int fromId, int number);
	public Collection<PostShowDTO> getTheBestPosts(int fromPosition, int number);
	public PostShowDTO getPostById(Optional<String> optionalUsername, long postId);
	public PostSaveDTO getPostByIdToModify(String name, Integer postId);
	public void saveOrUpdatePost(String userName,PostSaveDTO post);
	public Collection<PostShowDTO> getPostsByUserName(String userName, DatePeriodDTO datePeriod);
	public void delatePostById(String userName, long postId);
	
}
