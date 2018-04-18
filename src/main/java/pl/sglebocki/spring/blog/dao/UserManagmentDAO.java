package pl.sglebocki.spring.blog.dao;

import pl.sglebocki.spring.blog.entities.UserEntity;

public interface UserManagmentDAO {
	
	public boolean authenticateUser(String username, String password);
	public boolean tryToAddUserAndCheckResult(UserEntity newUser);
	
}
