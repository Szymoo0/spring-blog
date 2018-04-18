package pl.sglebocki.spring.blog.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.sglebocki.spring.blog.dao.AjaxPostDAO;
import pl.sglebocki.spring.blog.dao.TransactionRollbackException;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsChangeRequestDTO;
import pl.sglebocki.spring.blog.dto.ajax.AjaxPostReactionsResponseDTO;

@Service
@Transactional(rollbackOn={TransactionRollbackException.class})
public class AjaxPostServiceImpl implements AjaxPostService {

	@Autowired
	AjaxPostDAO ajaxPostDAO;
	
	@Override
	public AjaxPostReactionsResponseDTO processPostReactionChangeRequest(String username, AjaxPostReactionsChangeRequestDTO changeReactionRequest) {
		return ajaxPostDAO.processPostReactionChangeRequest(username, changeReactionRequest);
	}

}
