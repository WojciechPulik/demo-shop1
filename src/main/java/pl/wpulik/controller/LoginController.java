package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.wpulik.repository.UserRepository;
import pl.wpulik.repository.UserRoleRepository;

@Controller
public class LoginController {
	
	
	private UserRepository userRepository;
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	public LoginController(UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}
	
	@GetMapping("/loginform")
	public String loginForm() {
		return "loginform";		
	}
	
	
	
	

}
