package pl.sglebocki.spring.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(order=0)
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "pl.sglebocki.spring.blog.dao")
public class PersistanceConfig {
    
}
