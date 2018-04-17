package pl.sglebocki.spring.blog.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.sglebocki.spring.blog.aspects.ImageManipulationTransaction;
import pl.sglebocki.spring.blog.dto.UserSaveDTO;
import pl.sglebocki.spring.blog.entities.UserEntity;

@Repository
@Transactional(rollbackOn={TransactionRollbackException.class})
public class UserValidatorDAOImpl implements UserValidatorDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ImageSaver imageSaver;

	@Override
	public boolean authenticateUser(String username, String password) {
		UserEntity user = entityManager.find(UserEntity.class, username);
		if (user != null && password != null && password.equals(user.getPassword())) {
			return true;
		}
		return false;
	}
	
	@Override
	@ImageManipulationTransaction
	public boolean tryToAddUserAndCheckResult(UserSaveDTO newUser) {
		if (entityManager.find(UserEntity.class, newUser.getUsername()) == null) {
			UserEntity newUserEntity = getEntityObjectFromDTOobject(newUser);
			Optional<String> imageLink = imageSaver.saveImageAndGetImageLink(newUser.getUsername(), newUser.getAvatar());
			if(imageLink.isPresent()) {
				newUserEntity.setAvatar(imageLink.get());
			}
			entityManager.persist(newUserEntity);
			return true;
		}
		return false;
	}

	private UserEntity getEntityObjectFromDTOobject(UserSaveDTO postEntity)  {
		if(postEntity == null) {
			return null;
		}
		UserEntity retVal = new UserEntity();
		retVal.setUsername(postEntity.getUsername());
		retVal.setPassword(postEntity.getPassword());
		return retVal;
	}

}
