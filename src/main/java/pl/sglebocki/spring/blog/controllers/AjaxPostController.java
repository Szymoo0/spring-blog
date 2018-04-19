package pl.sglebocki.spring.blog.controllers;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${behave.posts.initial_posts_number}")
	private int additionalLoadPostNumber;
	
	@PostMapping(value="change-reaction", produces="application/json")
	@ResponseBody
	public AjaxPostReactionsResponseDTO changeReactionRequest(
			@RequestBody @Valid AjaxPostReactionsChangeRequestDTO changeReactionRequest, 
			BindingResult bindingResult,
			Principal principal) {
		if(principal == null)  throw new UnauthorizedException();
		if(bindingResult.hasErrors()) throw new BadRequestException(); 
		return ajaxPostService.processPostReactionChangeRequest(principal.getName(), changeReactionRequest);
	}
	
	@GetMapping(value="load-more-posts/{fromPostId}")
	public String changeReactionRequestt(
			@PathVariable("fromPostId") Integer fromPostId,
			Model model,
			Principal principal) {

		Collection<PostShowDTO> postsToShow = postsService.getPostsLowerThanId(getOptionalUsername(principal), fromPostId, additionalLoadPostNumber);
		model.addAttribute("posts", postsToShow);
		return "jsp-includes/posts";
	}
	
	private Optional<String> getOptionalUsername(Principal principal) {
		if (principal == null) {
			return Optional.empty();
		}
		return Optional.of(principal.getName());
	}
}
