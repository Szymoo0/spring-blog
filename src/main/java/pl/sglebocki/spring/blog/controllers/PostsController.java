package pl.sglebocki.spring.blog.controllers;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.sglebocki.spring.blog.dto.DatePeriodDTO;
import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.dto.PostSaveDTO;
import pl.sglebocki.spring.blog.services.PostsService;

@Controller
@RequestMapping("/posts")
public class PostsController {

	@Autowired 
	private PostsService postsService;
	
	@Value("${behave.posts.the_best_number}")
	private int theBestPostOnPage;
	
	private static final int THE_BEAT_POSTS = -1;
	
	@RequestMapping("/create")
	public String createPost(Model model) {
		PostSaveDTO createdPost = new PostSaveDTO();
		model.addAttribute("createdPost", createdPost);
		model.addAttribute("theBestPosts", postsService.getTheBestPosts(THE_BEAT_POSTS, theBestPostOnPage));
		
		return "create-post";
	}
	
	@RequestMapping("/save")
	public String savePost(
			@Valid @ModelAttribute("createdPost") PostSaveDTO postToSave, 
			BindingResult bindingResult, 
			Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
		postsService.saveOrUpdatePost(principal.getName(), postToSave);
		return "redirect:/";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/my-posts")
	public String myPosts(Model model, 
						@RequestParam(value="fromDate", required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
						@RequestParam(value="toDate", required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
						Principal principal) {
		
		// todo : gdzies indziej ten kod dac
		
		if(toDate == null) toDate = new Date();
		if(fromDate == null) {
			fromDate = new Date(toDate.getTime());
			fromDate.setMonth(fromDate.getMonth()-1);
		}
		
		DatePeriodDTO datePeriod = new DatePeriodDTO(fromDate, toDate);

		model.addAttribute("datePeriod", datePeriod);
		model.addAttribute("posts", postsService.getPostsByUserName(principal.getName(), datePeriod));
		model.addAttribute("theBestPosts", postsService.getTheBestPosts(THE_BEAT_POSTS, theBestPostOnPage));
		
		return "/my-posts";
	}
	
	@PostMapping("/delete")
	public String deletePost(@RequestParam("postId") Integer postId, Principal principal) {
		postsService.delatePostById(principal.getName(), postId);
		
		return "redirect:/posts/my-posts";
	}
	
	@RequestMapping("/edit/{postId}")
	public String myPostsEdit(@PathVariable("postId") Integer postId, Model model, Principal principal) {
		PostShowDTO post = postsService.getPostByIdWithAuthentication(principal.getName(), postId);
        if (post == null) {
            return "redirect:/";
        }
		model.addAttribute("createdPost", post);
		model.addAttribute("modification", true);
		model.addAttribute("theBestPosts", postsService.getTheBestPosts(THE_BEAT_POSTS, theBestPostOnPage));
		
		return "/create-post";
	}
	
}
