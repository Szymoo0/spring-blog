package pl.sglebocki.spring.blog.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.sglebocki.spring.blog.aspects.ImageManipulationTransaction;
import pl.sglebocki.spring.blog.dao.ImageDAO;
import pl.sglebocki.spring.blog.dao.TransactionRollbackException;
import pl.sglebocki.spring.blog.dao.UserManagmentDAO;
import pl.sglebocki.spring.blog.dto.UserSaveDTO;
import pl.sglebocki.spring.blog.entities.UserEntity;

@Service
@Transactional(rollbackOn={TransactionRollbackException.class})
class UserManagmentServiceImpl implements UserManagmentService {

	@Autowired 
	private UserManagmentDAO userManagmentDAO;
	
	@Autowired
	private ImageDAO imageDAO;
	
	@Override
	public boolean authenticateUser(String username, String password) {
		return userManagmentDAO.authenticateUser(username, password);
	}
	
	@Override
	@ImageManipulationTransaction
	public boolean tryToAddUserAndCheckIfSuccess(UserSaveDTO newUserDTO) {
		UserEntity newUserEntity = getUserEntityFromDTO(newUserDTO);
		newUserEntity.setAvatar(saveUserAvatarAndGetItsLink(newUserDTO));
		boolean userAddingResult = userManagmentDAO.tryToAddUserAndCheckResult(newUserEntity);
		if(userAddingResult == false) {
			deleteNotSavedUserAvatar();
		}
		return userAddingResult;
	}
	
	private UserEntity getUserEntityFromDTO(UserSaveDTO newUserDTO)  {
		UserEntity retVal = new UserEntity();
		retVal.setUsername(newUserDTO.getUsername());
		retVal.setPassword(newUserDTO.getPassword());
		return retVal;
	}
	
	private String saveUserAvatarAndGetItsLink(UserSaveDTO newUserDTO) {
		Optional<String> imageLink = imageDAO.saveImageAndGetImageLink(newUserDTO.getUsername(), newUserDTO.getAvatar());
		return imageLink.orElse(null); // TODO orElse -> some default
	}
	
	private void deleteNotSavedUserAvatar() {
		imageDAO.rollback();
	}
}
