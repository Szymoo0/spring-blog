package pl.sglebocki.spring.blog.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostAuthorize;

import pl.sglebocki.spring.blog.entities.PostEntity;

public interface PostsDAO extends JpaRepository<PostEntity, Long>, PostsComplexDAO {
	Optional<PostEntity> findById(Long id);
	@Query("SELECT pe FROM PostEntity pe where pe.id = ?1")
	@PostAuthorize("returnObject.isPresent() and returnObject.get().author.username == authentication.name")
	Optional<PostEntity> findByIdWithAuth(Long id);
}
