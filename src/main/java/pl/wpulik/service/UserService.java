package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.dto.UserDTO;
import pl.wpulik.model.Address;
import pl.wpulik.model.Order;
import pl.wpulik.model.User;
import pl.wpulik.repository.UserRepository;
import pl.wpulik.repository.UserRoleRepository;

@Transactional
@Service
public class UserService {
	
	private UserRepository userRepository;
	private UserRoleRepository userRoleRepository;
	private AddressRepoService addressRepoService;
	private OrderRepoService orderRepoService;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository,
			AddressRepoService addressRepoService, OrderRepoService orderRepoService,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.addressRepoService = addressRepoService;
		this.orderRepoService = orderRepoService;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User addUser(User user) {
		user.setRole(userRoleRepository.getOne(1L));
		String passwordHash = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordHash);
		addressRepoService.addAddress(user.getAddress());
		return userRepository.save(user);
	}
	
	public User getById(Long id) {
		User user = userRepository.findById(id).get();
		user.getAddress();
		return user;
	}
	
	public UserDTO getDtoById(Long id) {
		User user = userRepository.findById(id).get();
		if(user.getAddress()==null) {
			user.setAddress(new Address("00-000", 
					"---", "---", "---", "---", "---", "---", "---"));
		}
		if(user.getDiscount()==null)
			user.setDiscount(0.0);
		return UserDTO.toDtoMapping(user);
	}
	
	public User updateUser(User user) {
		Address address = addressRepoService.getById(user.getAddress().getId());
		address = updateUserAddressMapping(address, user);
		User userToUpdate = userRepository.getOne(user.getId());
		userToUpdate.setAddress(address);
		user = updateUserMapping(userToUpdate, user);
		return userRepository.save(user);
	}
	
	private Address updateUserAddressMapping(Address address, User user) {
		address.setFirstName(user.getFirstName());
		address.setLastName(user.getLastname());
		address.setPhoneNumber(user.getAddress().getPhoneNumber());
		address.setStreet(user.getAddress().getStreet());
		address.setBuildingNumber(user.getAddress().getBuildingNumber());
		address.setCity(user.getAddress().getCity());
		address.setZipCode(user.getAddress().getZipCode());
		return addressRepoService.addAddress(address);//update
	}
	
	private User updateUserMapping(User toUpdate, User form) {
		toUpdate.setFirstName(form.getFirstName());
		toUpdate.setLastname(form.getLastname());
		toUpdate.setGender(form.getGender());
		toUpdate.setEmail(form.getEmail());
		return toUpdate;
	}
	
	public Page<Order> getAllUserOrders(Pageable pageable, Long userId){
		return orderRepoService.findAllUserOrders(pageable, userId);
	}
	

}
