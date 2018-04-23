package pl.sglebocki.spring.blog.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.sglebocki.spring.blog.dto.UserSaveDTO;
import pl.sglebocki.spring.blog.services.UserManagmentService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	UserManagmentService userValidatorService;
	
	@GetMapping("register")
	public String registerForm(Model model) {
		model.addAttribute("newUser", new UserSaveDTO());

		return "registration-form";
	}
	
	@PostMapping("save")
	public String registerProcessForm(@Valid @ModelAttribute("newUser") UserSaveDTO newUser, BindingResult bindedResult, Model model) {
		if(bindedResult.hasErrors()) {
			model.addAttribute("error", StatusCodes.INVALID_USERNAME_OR_PASSWORD);
			return "registration-form";
		}

		if (userValidatorService.tryToAddUserAndCheckIfSuccess(newUser)) {
			return "redirect:/authentication/login";
		}
		
		model.addAttribute("error", StatusCodes.USERNAME_ALREADY_EXISTS);
		return "registration-form";
	}
	
	@GetMapping("login")
	public String loginForm() {

		return "login-form";
	}
	
	@RequestMapping(value="logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/authentication/login?logout";
	}
	
}
