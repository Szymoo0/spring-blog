package pl.sglebocki.spring.blog.services;

import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;

public interface AjaxPostService {

	public AjaxPostReactionsResponseDTO processPostReactionChangeRequest(String username, AjaxPostReactionsChangeRequestDTO changeReactionRequest);

}
