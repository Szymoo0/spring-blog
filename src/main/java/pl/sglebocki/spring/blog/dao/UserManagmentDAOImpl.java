package pl.sglebocki.spring.blog.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import pl.sglebocki.spring.blog.entities.UserEntity;

@Repository
class UserManagmentDAOImpl implements UserManagmentDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean authenticateUser(String username, String password) {
		UserEntity user = entityManager.find(UserEntity.class, username);
		if (user != null && password != null && password.equals(user.getPassword())) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean tryToAddUserAndCheckResult(UserEntity newUser) {
		if (entityManager.find(UserEntity.class, newUser.getUsername()) == null) {
			entityManager.persist(newUser);
			return true;
		}
		return false;
	}

}
