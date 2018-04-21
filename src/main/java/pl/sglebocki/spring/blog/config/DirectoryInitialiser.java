package pl.sglebocki.spring.blog.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DirectoryInitialiser implements ApplicationListener<ApplicationStartedEvent>{

	@Autowired
	@Qualifier("imagePath")
	private Path imagePath;
	
	@Override
	public void onApplicationEvent(ApplicationStartedEvent arg0) {
		try {
			Files.createDirectories(imagePath);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
