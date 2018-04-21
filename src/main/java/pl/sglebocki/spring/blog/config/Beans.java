package pl.sglebocki.spring.blog.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

	@Bean("imagePath")
	public Path imagePath(@Value("${resources.images}") String imagePath) {
		if(imagePath.startsWith("~")) {
			imagePath = System.getProperty("user.home") + imagePath.substring(1);
		}
		return Paths.get(imagePath);
	}
	
}
