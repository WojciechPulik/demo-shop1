package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.User;
import pl.wpulik.repository.UserRepository;
import pl.wpulik.repository.UserRoleRepository;

@Transactional
@Service
public class UserService {
	
	private UserRepository userRepository;
	private UserRoleRepository userRoleRepository;
	private AddressRepoService addressRepoService;
	
	@Autowired
	public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository,
			AddressRepoService addressRepoService) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.addressRepoService = addressRepoService;
	}
	
	public User addUser(User user) {
		user.setRole(userRoleRepository.getOne(1L));
		addressRepoService.addAddress(user.getAddress());
		return userRepository.save(user);
	}
	
	public User getById(Long id) {
		User user = userRepository.findById(id).get();
		return user;
	}

}
