package pl.sglebocki.spring.blog.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.services.PostsService;

@Controller
public class PublicPostsController {

	@Value("${behave.posts.initial_posts_number}")
	private int initialPostNumberOnPage;
	
	@Value("${behave.posts.the_best_number}")
	private int theBestPostOnPage;
	
	private static final int NEWEST_POST_ID = -1;
	private static final int THE_BEAT_POSTS = -1;
	
	@Autowired 
	PostsService postsService;
	
	@RequestMapping("/")
	public String index(Model model, Principal principal) {
		model.addAttribute("posts", postsService.getPostsLowerThanId(getOptionalUsername(principal), NEWEST_POST_ID, initialPostNumberOnPage));
		model.addAttribute("theBestPosts", postsService.getTheBestPosts(THE_BEAT_POSTS, theBestPostOnPage));

		return "index";
	}
	
	@RequestMapping("/post/{postId}")
	public String showPost(@PathVariable("postId") Integer postId, Model model, Principal principal) {
		PostShowDTO post = postsService.getPostById(getOptionalUsername(principal), postId);
		if(post == null) {
			throw new NotFoundException(String.format("Resource named: '/post/%d' doesn't exists.", postId));
		}
		model.addAttribute("posts", Arrays.asList(post));
		model.addAttribute("theBestPosts", postsService.getTheBestPosts(THE_BEAT_POSTS, theBestPostOnPage));
		model.addAttribute("singlePostMainPage", true);
		
		return "index";
	}
	
	private Optional<String> getOptionalUsername(Principal principal) {
		if (principal == null) {
			return Optional.empty();
		}
		return Optional.of(principal.getName());
	}

}
