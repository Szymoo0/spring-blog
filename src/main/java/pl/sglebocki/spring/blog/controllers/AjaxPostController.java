package pl.sglebocki.spring.blog.controllers;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.sglebocki.spring.blog.dto.PostShowDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;
import pl.sglebocki.spring.blog.services.AjaxPostService;
import pl.sglebocki.spring.blog.services.PostsService;

@Controller
@RequestMapping("/posts/ajax")
public class AjaxPostController {

	@Autowired
	private AjaxPostService ajaxPostService;
	
	@Autowired 
	private PostsService postsService;
	
	private static final int ADDITIONAL_LOAD = 3; // TODO zrobic cos z tym
	
	@PostMapping(value="change-reaction", produces="application/json")
	@ResponseBody
	public AjaxPostReactionsResponseDTO changeReactionRequest(
			@RequestBody @Valid AjaxPostReactionsChangeRequestDTO changeReactionRequest, 
			BindingResult bindingResult,
			Principal principal) {
		if(bindingResult.hasErrors()) {
			return null; // TODO return error code here
		}
		return ajaxPostService.processPostReactionChangeRequest(principal.getName(), changeReactionRequest);
	}
	
	@GetMapping(value="load-more-posts/{fromPostId}")
	public String changeReactionRequestt(
			@PathVariable("fromPostId") Integer fromPostId,
			Model model,
			Principal principal) {

		
		Collection<PostShowDTO> postsToShow = postsService.getPostsLowerThanId(getOptionalUsername(principal), fromPostId, ADDITIONAL_LOAD);
		
		System.out.println(postsToShow.size());
		
		model.addAttribute("posts", postsToShow);
		
		System.out.println(fromPostId);
		
		return "jsp-includes/posts";
	}
	
	private Optional<String> getOptionalUsername(Principal principal) {
		if (principal == null) {
			return Optional.empty();
		}
		return Optional.of(principal.getName());
	}
}
