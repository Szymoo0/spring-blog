package pl.sglebocki.spring.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers("/css/**", "/js/**", "/images/**", "/dynamicimages/**", "/posts/change-reaction").permitAll()
                .antMatchers("/", "/post/**", "/authentication/login", "/authentication/register", "/authentication/save").permitAll()
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
