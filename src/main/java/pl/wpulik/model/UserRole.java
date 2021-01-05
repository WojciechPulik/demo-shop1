package pl.wpulik.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "roles")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_role")
	private Long id;
	private String role;
	private String description;
	@OneToMany(mappedBy = "role")
	private List<User> users = new ArrayList<>();
	
	public UserRole() {}
	
	/*	
	private UserRole(Long id, String role, String description) {
		this.id = id;
		this.role = role;
		this.description = description;
	}

	public static UserRole setRoleAsUSER() {
		return new UserRole(1L, "USER", "zwykły użytkownik");
	}
*/	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "UserRole [id=" + id + ", role=" + role + ", description=" + description + "]";
	}
	
	
}
