package pl.wpulik.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;


import pl.wpulik.model.Address;
import pl.wpulik.model.Gender;
import pl.wpulik.model.User;


public class RegistrationDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private String firstName;
	private String lastname;
	private Gender gender;
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	private Address address;
	
	public RegistrationDTO(String firstName, String lastname, Gender gender, @NotEmpty String email,
			@NotEmpty String password, Address address) {
		this.firstName = firstName;
		this.lastname = lastname;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.address = address;
	}
	
	public static RegistrationDTO toDtoMapping(User user, Address address) {
		return new RegistrationDTO(user.getFirstName(), user.getLastname(), user.getGender(), 
				user.getEmail(), user.getPassword(), address);
	}
	
	public static User fromDtoMapping(RegistrationDTO dto) {
		User user = new User();
		Address address = dto.getAddress();
		address.setFirstName(dto.getFirstName());
		address.setLastName(dto.getLastname());
		address.setEmail(dto.getEmail());
		user.setAddress(address);
		user.setFirstName(dto.getFirstName());
		user.setLastname(dto.getLastname());
		user.setGender(dto.getGender());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		return user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "RegistrationDTO [firstName=" + firstName + ", lastname=" + lastname + ", gender=" + gender + ", email="
				+ email + ", password=" + password + ", address=" + address + "]";
	}
	
	
	
	

}
