package fr.bediss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.bediss.entities.User;


@Repository
public interface UserRepositories extends JpaRepository<User, Long> {
	
	User findByUsername (String username);

}
