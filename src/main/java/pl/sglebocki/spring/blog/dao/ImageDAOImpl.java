package pl.sglebocki.spring.blog.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class ImageDAOImpl implements ImageDAO {

	@Value("${resources.images}")
	private String imagePath;
	private LinkedList<Path> savedImages = new LinkedList<>();
	private LinkedList<Path> imagesToDelate = new LinkedList<>();

	public ImageDAOImpl() {
	}
	
	public ImageDAOImpl(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public Optional<String> saveImageAndGetImageLink(String userName, MultipartFile image) {
		if(image != null && !image.isEmpty() && image.getContentType().startsWith("image")) {
			Date date = new Date();
			String imageLink = userName + date.getTime() + image.getOriginalFilename();
			try {
				Path imageSavePath = Paths.get(imagePath, imageLink);
				savedImages.add(imageSavePath);
				image.transferTo(imageSavePath.toFile());
			} catch (IOException e) {
				throw new TransactionRollbackException(e);
			}
			return Optional.of(imageLink);
		}
		return Optional.empty();
	}
	
	@Override
	public void registerImageLinkToDelete(String imageLink) {
		if (imageLink != null) {
			imagesToDelate.push(Paths.get(imagePath.toString(), imageLink));
		}
	}
	
	@Override
	public void begin() {
		if(savedImages.size() != 0 || imagesToDelate.size() != 0) {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void commit() {
		while(imagesToDelate.size() != 0) {
			Path imageToDelete = imagesToDelate.pop();
			try {
				Files.deleteIfExists(imageToDelete);
			} catch (IOException e) {
				// TODO Log somewhere.
				e.printStackTrace();
			}
		}
		savedImages.clear();
	}
	
	@Override
	public void rollback() {
		while(savedImages.size() != 0) {
			Path imageToDelete = savedImages.pop();
			try {
				Files.deleteIfExists(imageToDelete);
			} catch (IOException e) {
				// TODO Log somewhere.
				e.printStackTrace();
			}
		}
		imagesToDelate.clear();
	}
	
}