package fr.bediss;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.bediss.entities.Produit;
import fr.bediss.services.ProduitService;
import fr.bediss.services.UserService;

@SpringBootApplication
public class SecurityApplication {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
	
	@Autowired
	ProduitService produitService;

	@PostConstruct
	void init_users() {
	produitService.saveProduit(new Produit(null,"PC Dell", 2600.0, new Date()));
	produitService.saveProduit(new Produit(null,"PC Asus", 2800.0, new Date()));
	produitService.saveProduit(new Produit(null,"Imp Epson", 900.0, new Date()));
	} 
	
	@Bean
	BCryptPasswordEncoder getBCE() {
		return new BCryptPasswordEncoder();
	}
}
