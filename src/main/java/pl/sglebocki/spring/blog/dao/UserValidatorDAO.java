package pl.sglebocki.spring.blog.dao;

import pl.sglebocki.spring.blog.dto.UserSaveDTO;

public interface UserValidatorDAO {
	
	public boolean authenticateUser(String username, String password);
	public boolean tryToAddUserAndCheckResult(UserSaveDTO newUser);
	
}
