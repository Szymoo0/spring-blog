package pl.sglebocki.spring.blog.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.services.PostsService;

@Controller
public class MainController {

	private static final int NUMBER_OF_POSTS_ON_PAGE = 10; // todo zrobic cos z tym
	
	@Autowired 
	private PostsService postsService;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("posts", postsService.getNewestPosts(NUMBER_OF_POSTS_ON_PAGE));
		
		return "index";
	}
	
	@RequestMapping("/post/{postId}")
	public String showPost(@PathVariable("postId") Integer postId, Model model) {
		PostShowDTO post = postsService.getPostById(postId);
		if(post == null) {
			return "redirect:/"; // todo dac deafultowy tekscik jakis
		}
		model.addAttribute("posts", Arrays.asList(post));
		
		return "index";
	}

}
