package pl.sglebocki.spring.blog.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.sglebocki.spring.blog.dao.PostsDAO;
import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;

@Service
public class PostsServiceImpl implements PostsService {

	@Autowired
	private PostsDAO postsDAO;

	@Override
	public Collection<PostShowDTO> getNewestPosts(int numberOfPosts) {
		return postsDAO.getNewestPosts(numberOfPosts);
	}

	@Override
	public PostShowDTO getPostById(int postId) {
		return postsDAO.getPostById(postId);
	}
	
	@Override
	public PostShowDTO getPostByIdWithAuthentication(String name, Integer postId) {
		return postsDAO.getPostByIdWithAuthentication(name, postId);
	}

	@Override
	public void saveOrUpdatePost(String username, PostSaveDTO post) {
		postsDAO.saveOrUpdatePostContent(username, post);
	}

	@Override
	public Collection<PostShowDTO> getPostsByUserName(String userName, DatePeriodDTO datePeriod) {
		return postsDAO.getPostsByUserName(userName, datePeriod);
	}

	@Override
	public void delatePostById(String username, int postId) {
		postsDAO.delatePostById(username, postId);
	}
}
