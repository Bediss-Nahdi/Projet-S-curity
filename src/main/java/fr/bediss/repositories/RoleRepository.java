package fr.bediss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bediss.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRole (String role);

}
