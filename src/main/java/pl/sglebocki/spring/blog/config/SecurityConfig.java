package pl.sglebocki.spring.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] ALLOWED_ONLY_TO_AUTHENTICATED = {
			"/posts/**"
	};
	
	@Autowired
	Unauthenticated401Response unauthenticated401Response;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers(ALLOWED_ONLY_TO_AUTHENTICATED).authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/authentication/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();

        http.csrf().disable(); // TODO delete after development stage
        http.headers().frameOptions().disable(); // TODO delete after development stage
    }
}
