package pl.sglebocki.spring.blog.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.services.PostsService;

public class PublicPostsControllerTest {
	
	Principal principal;
	PublicPostsController controller;
	
	@Before
	public void setup() {
		PostsService postsService = new PostsService() {
			Collection<PostShowDTO> postsList;
			PostShowDTO post;
			{
				post = new PostShowDTO();
				post.setId(1);
				postsList = Arrays.asList(post);
			}
			
			@Override
			public Collection<PostShowDTO> getPostsLowerThanId(Optional<String> optionalUsername, int fromId, int number) {
				return null;
			}
			@Override
			public Collection<PostShowDTO> getTheBestPosts(int fromPosition, int number) {
				return postsList;
			}
			@Override
			public PostShowDTO getPostById(Optional<String> optionalUsername, long postId) {				
				if(post.getId() == postId) return post;
				return null;
			}
			@Override
			public PostSaveDTO getPostByIdToModify(String name, Integer postId) {
				return null;
			}
			@Override
			public void saveOrUpdatePost(String userName, PostSaveDTO post) {
			}
			@Override
			public Collection<PostShowDTO> getPostsByUserName(String userName, DatePeriodDTO datePeriod) {
				return null;
			}
			@Override
			public void delatePostById(String userName, long postId) {
			}
		};
		
		principal = new Principal() {
			@Override
			public String getName() {
				return "Szymoo0";
			}
		};
		
		controller = new PublicPostsController();
		controller.postsService = postsService;
	}
	
	@Test
	public void indexPageRequest() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/").principal(principal))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}
	
	@Test
	public void existingPostRequest() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/post/1").principal(principal))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}
	
	@Test
	public void existingPostAttributesCheck() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/post/1").principal(principal))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attribute("singlePostMainPage", true));
	}
	
	@Test
	public void notExistingPostRequest() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/post/2").principal(principal))
			.andExpect(status().isNotFound());		
	}

}
