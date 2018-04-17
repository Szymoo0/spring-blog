package pl.sglebocki.spring.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(order=0)
@PropertySource("classpath:application.properties")
public class PersistanceConfig {
    
}
