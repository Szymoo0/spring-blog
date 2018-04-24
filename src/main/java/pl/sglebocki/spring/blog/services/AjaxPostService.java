package pl.sglebocki.spring.blog.services;

import java.security.Principal;

import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;

public interface AjaxPostService {

	public AjaxPostReactionsResponseDTO processPostReactionChangeRequest(Principal principal, AjaxPostReactionsChangeRequestDTO changeReactionRequest);

}
