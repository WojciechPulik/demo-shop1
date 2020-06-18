package pl.wpulik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	UserRole findByRole(String role);

}
