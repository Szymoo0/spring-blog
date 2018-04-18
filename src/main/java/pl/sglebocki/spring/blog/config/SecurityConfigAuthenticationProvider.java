package pl.sglebocki.spring.blog.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import pl.sglebocki.spring.blog.services.UserManagmentService;

@Component
public class SecurityConfigAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserManagmentService userValidatorService;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String username = auth.getName();
        String password = auth.getCredentials().toString();

        // TODO get that value from database
        Collection<GrantedAuthority> grantedAuths = Arrays.asList(new SimpleGrantedAuthority("USER"));

        if (userValidatorService.authenticateUser(username, password)) {
        	return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        }
        throw new BadCredentialsException("Invalid username or password!");
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

}
