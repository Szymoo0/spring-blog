package pl.sglebocki.spring.blog.controllers;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
public class PostsController {

	@Autowired 
	private PostsService postsService;
	
	@RequestMapping("/posts/create")
	public String createPost(Model model) {
		PostSaveDTO createdPost = new PostSaveDTO();
		model.addAttribute("createdPost", createdPost);
		
		return "create-post";
	}
	
	@RequestMapping("/posts/save")
	public String savePost(@Valid @ModelAttribute("createdPost") PostSaveDTO postToSave, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
		postsService.saveOrUpdatePost(principal.getName(), postToSave);
		return "redirect:/";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/posts/my-posts")
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
		
		return "/my-posts";
	}
	
	@PostMapping("/posts/delete")
	public String deletePost(@RequestParam("postId") Integer postId, Principal principal) {
		postsService.delatePostById(principal.getName(), postId);
		
		return "redirect:/posts/my-posts";
	}
	
	@RequestMapping("/posts/edit/{postId}")
	public String myPostsEdit(@PathVariable("postId") Integer postId, Model model, Principal principal) {
		PostShowDTO post = postsService.getPostByIdWithAuthentication(principal.getName(), postId);
        if (post == null) {
            return "redirect:/";
        }
		model.addAttribute("createdPost", post);
		model.addAttribute("modification", true);
		
		return "/create-post";
	}
	
}
