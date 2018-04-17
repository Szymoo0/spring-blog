package pl.sglebocki.spring.blog.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;
import pl.sglebocki.spring.blog.services.AjaxPostService;

@Controller
@RequestMapping("/posts/ajax")
public class PostAjaxController {

	@Autowired
	private AjaxPostService ajaxPostService;
	
	@PostMapping(value="change-reaction", produces="application/json")
	@ResponseBody
	public AjaxPostReactionsResponseDTO aaa(@RequestBody AjaxPostReactionsChangeRequestDTO changeReactionRequest, Principal principal) {
		
		AjaxPostReactionsResponseDTO response = ajaxPostService.processPostReactionChangeRequest(principal.getName(), changeReactionRequest);
		
		AjaxPostReactionsResponseDTO responsee = new AjaxPostReactionsResponseDTO();
		responsee.setLikes(10);
		responsee.setDislikes(5);
		return response;
	}
	
}
