package pl.sglebocki.spring.blog.dao;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface ImageDAO {
	public Optional<String> saveImageAndGetImageLink(String userName, MultipartFile image);
	public void registerImageLinkToDelete(String imageLink);
	public void begin();
	public void commit();
	public void rollback();
}
