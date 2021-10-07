package fr.bediss;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.bediss.entities.Role;
import fr.bediss.entities.User;
import fr.bediss.services.UserService;

@SpringBootApplication
public class SecurityApplication {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@PostConstruct
	void init_users() {
		// ajouter les rôles
		userService.addRole(new Role(null, "ADMIN"));
		userService.addRole(new Role(null, "USER"));
		// ajouter les users
		userService.saveUser(new User(null, "admin", "123", true, null));
		userService.saveUser(new User(null, "Bédiss", "123", true, null));
		userService.saveUser(new User(null, "sebastien", "123", true, null));
		// ajouter les rôles aux users
		userService.addRoleToUser("admin", "ADMIN");
		userService.addRoleToUser("admin", "USER");
		userService.addRoleToUser("Bédiss", "USER");
		userService.addRoleToUser("sebastien", "USER");
	}

	@Bean
	BCryptPasswordEncoder getBCE() {
		return new BCryptPasswordEncoder();
	}
}
