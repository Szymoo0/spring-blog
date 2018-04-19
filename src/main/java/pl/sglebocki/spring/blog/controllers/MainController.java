package pl.sglebocki.spring.blog.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.services.PostsService;

@Controller
public class MainController {

	private static final int NUMBER_OF_POSTS_ON_PAGE = 5; // TODO zrobic cos z tym
	private static final long NEWEST_POST_ID = -1;
	
	@Autowired 
	private PostsService postsService;
	
	@RequestMapping("/")
	public String index(Model model, Principal principal) {
		model.addAttribute("posts", postsService.getPostsLowerThanId(getOptionalUsername(principal), NEWEST_POST_ID, NUMBER_OF_POSTS_ON_PAGE));
		
		return "index";
	}
	
	@RequestMapping("/post/{postId}")
	public String showPost(@PathVariable("postId") Integer postId, Model model, Principal principal) {
		PostShowDTO post = postsService.getPostById(getOptionalUsername(principal), postId);
		if(post == null) {
			return "redirect:/"; // TODO dac deafultowy tekscik jakis
		}
		model.addAttribute("posts", Arrays.asList(post));
		
		return "index";
	}
	
	private Optional<String> getOptionalUsername(Principal principal) {
		if (principal == null) {
			return Optional.empty();
		}
		return Optional.of(principal.getName());
	}

}
