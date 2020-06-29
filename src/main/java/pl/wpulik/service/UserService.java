package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.User;
import pl.wpulik.repository.UserRepository;

@Transactional
@Service
public class UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User addUser(User user) {
		userRepository.save(user);
		return user;
	}
	
	public User getById(Long id) {
		User user = userRepository.findById(id).get();
		return user;
	}

}
