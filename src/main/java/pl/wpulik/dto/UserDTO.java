package pl.wpulik.dto;

import java.util.ArrayList;
import java.util.List;

import pl.wpulik.model.Address;
import pl.wpulik.model.Gender;
import pl.wpulik.model.Order;
import pl.wpulik.model.User;
import pl.wpulik.model.UserRole;

public class UserDTO {
	
	private Long id;
	private String firstName;
	private String lastname;
	private Gender gender;
	private String email;
	private Address address;
	private UserRole role;
	private List<Order> orders = new ArrayList<>();
	private Double discount;
	
	public UserDTO() {}
	
	public UserDTO(Long id, String firstName, String lastname, Gender gender, String email,
			Address address, UserRole role, List<Order> orders, Double discount) {
		this.id = id;
		this.firstName = firstName;
		this.lastname = lastname;
		this.gender = gender;
		this.email = email;
		this.address = address;
		this.role = role;
		this.orders = orders;
		this.discount = discount;
	}
	
	public static UserDTO toDtoMapping(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastname(user.getLastname());
		dto.setGender(user.getGender());
		dto.setEmail(user.getEmail());
		dto.setAddress(user.getAddress());
		dto.setRole(user.getRole());
		dto.setOrders(user.getOrders());
		dto.setDiscount(user.getDiscount());
		return dto;
	}
	
	public static User fromDtoMapping(UserDTO dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setFirstName(dto.getFirstName());
		user.setLastname(dto.getLastname());
		user.setGender(dto.getGender());
		user.setEmail(dto.getEmail());
		user.setAddress(dto.getAddress());
		user.setRole(dto.getRole());
		user.setOrders(dto.getOrders());
		user.setDiscount(dto.getDiscount());
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastname=" + lastname + ", gender=" + gender
				+ ", email=" + email + ", address=" + address + ", role=" + role
				+ ", orders=" + orders + ", discount=" + discount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		return true;
	}
	
	
	

}
