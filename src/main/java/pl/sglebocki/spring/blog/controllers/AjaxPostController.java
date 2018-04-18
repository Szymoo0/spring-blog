package pl.sglebocki.spring.blog.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;
import pl.sglebocki.spring.blog.services.AjaxPostService;

@Controller
@RequestMapping("/posts/ajax")
public class AjaxPostController {

	@Autowired
	private AjaxPostService ajaxPostService;
	
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
	
}
