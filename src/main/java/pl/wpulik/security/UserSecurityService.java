package pl.wpulik.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.wpulik.model.User;
import pl.wpulik.model.UserRole;
import pl.wpulik.repository.UserRepository;
import pl.wpulik.repository.UserRoleRepository;

@Service
public class UserSecurityService {
	
	private static final String DEFAULT_ROLE = "ROLE_USER";
	private UserRepository userRepository;
	private UserRoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserSecurityService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Autowired
	public void setRoleRepository(UserRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public void addWithDefaultRole(User user) {
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.setRole(defaultRole);
		String passwordHash = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordHash);
		userRepository.save(user);
	}
	
	
}




