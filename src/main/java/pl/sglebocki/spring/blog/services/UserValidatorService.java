package pl.sglebocki.spring.blog.services;

import pl.sglebocki.spring.blog.dto.UserSaveDTO;

public interface UserValidatorService {

	boolean authenticateUser(String username, String password);
	boolean tryToAddUserAndCheckIfSuccess(UserSaveDTO newUser);
	
}
