package pl.sglebocki.spring.blog.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.services.PostsService;

public class PublicPostsControllerTest {
	
	private MockMvc mockMvc;
	private PublicPostsController uutControler;
	private PostsService postsServiceStub;
	private Principal testPrincipal;
	
	@Before
	public void setup() {
		testPrincipal = new Principal() {
			@Override
			public String getName() {
				return "Szymoo0";
			}
		};
		uutControler = new PublicPostsController();
		postsServiceStub = mock(PostsService.class);
		uutControler.postsService = postsServiceStub;
		mockMvc = MockMvcBuilders.standaloneSetup(uutControler).build();
	}
	
	@Test
	public void indexPageRequest() throws Exception {
		// given
		// when then
		mockMvc.perform(get("/").principal(testPrincipal))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}
	
	@Test
	public void existingPostRequest() throws Exception {
		// given
		when(postsServiceStub.getPostByIdWithAdditionalInfo(1, testPrincipal)).thenReturn(new PostShowDTO());
		// when then
		mockMvc.perform(get("/post/1").principal(testPrincipal))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
	}
	
	@Test
	public void existingPostAttributesCheck() throws Exception {
		// given
		when(postsServiceStub.getPostByIdWithAdditionalInfo(1, testPrincipal)).thenReturn(new PostShowDTO());
		// when then
		mockMvc.perform(get("/post/1").principal(testPrincipal))
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(model().attribute("singlePostMainPage", true));
	}
	
	@Test
	public void notExistingPostRequest() throws Exception {
		// given
		when(postsServiceStub.getPostByIdWithAdditionalInfo(1, testPrincipal)).thenReturn(null);
		// when then
		mockMvc.perform(get("/post/2").principal(testPrincipal))
			.andExpect(status().isNotFound());		
	}

}
