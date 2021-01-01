package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.wpulik.service.AddressService;
import pl.wpulik.service.UserService;
import pl.wpulik.dto.RegistrationDTO;
import pl.wpulik.model.Address;
import pl.wpulik.model.User;
import pl.wpulik.model.UserRole;
import pl.wpulik.repository.UserRoleRepository;

@Controller
public class UserController {
	
	private UserService userService;
	private AddressService addressService;
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	public UserController(UserService userService, AddressService addressService,
			UserRoleRepository userRoleRepository) {
		this.userService = userService;
		this.addressService = addressService;
		this.userRoleRepository = userRoleRepository;
	}

	@GetMapping("/registration")
	public String registrationForm(Model model) {
		RegistrationDTO registDto = RegistrationDTO.toDtoMapping(new User(), new Address());
		model.addAttribute("registDto", registDto);
		return "registrationform";		
	}
	
	@PostMapping("/register")
	public String registerNewUser(@ModelAttribute RegistrationDTO registDto) {
		System.out.println(registDto.toString());
		userService.addUser(RegistrationDTO.fromDtoMapping(registDto));
		return "redirect:/registration";//TODO: user panel
	}
	
	
	
	
}
