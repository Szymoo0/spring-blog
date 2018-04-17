package pl.sglebocki.spring.blog.dao;

import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;

public interface AjaxPostDAO {

	AjaxPostReactionsResponseDTO processPostReactionChangeRequest(String username, AjaxPostReactionsChangeRequestDTO changeReactionRequest);

}