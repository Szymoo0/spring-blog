package pl.sglebocki.spring.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pl.sglebocki.spring.blog.dao.UserValidatorDAO;
import pl.sglebocki.spring.blog.dto.UserSaveDTO;

@Service
public class UserValidatorServiceImpl implements UserValidatorService {

	@Autowired 
	UserValidatorDAO userValidatorDAO;
	
	@Value("${resources.images}")
	String imageDirectory;
	
	@Override
	public boolean authenticateUser(String username, String password) {
		return userValidatorDAO.authenticateUser(username, password);
	}
	
	@Override
	public boolean tryToAddUserAndCheckIfSuccess(UserSaveDTO newUser) {
		return userValidatorDAO.tryToAddUserAndCheckResult(newUser);

	}


}
