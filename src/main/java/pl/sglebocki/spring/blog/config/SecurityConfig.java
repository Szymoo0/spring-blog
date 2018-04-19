package pl.sglebocki.spring.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] allowedToAllResources = {
			"/css/**", 
			"/js/**", 
			"/images/**", 
			"/dynamicimages/**"
			};
	private static final String[] allowedToAllPaths = {
			"/",
			"/post/**", 
			"/authentication/login", 
			"/authentication/register", 
			"/authentication/save", 
			"/posts/ajax/load-more-posts/**",
			"/posts/ajax/change-reaction"
			};
	
	@Autowired
	Unauthenticated401Response unauthenticated401Response;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers(allowedToAllResources).permitAll()
                .antMatchers(allowedToAllPaths).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/authentication/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
}
