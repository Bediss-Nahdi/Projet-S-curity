package fr.bediss.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.bediss.entities.User;
import fr.bediss.services.UserService;

							/* DEUXIEME CLASS A CREER DANS LE MODULE SECURITY*/

// 1 // Mettre l'annotation @Service
// 2 // Faire un extends UserDetailsService (qui je rapelle que UserDetailsService est une interface)
// 3 // Importer les méthodes de UserDetailsService

@Service
public class MyUserDetailsService implements UserDetailsService {

	// 5 // Comme j'ai besoin de rammener le user name et le role par voie de
	// conséquence
	@Autowired
	UserService userService; // PK? ==> pour nle findByUsername :p

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 4 // Je dois aller (logiquement) chercher le username et par voie de
		// conséquence le role
		User user = userService.findUserByUsername(username);

		// 6 // Je teste si l'utilisateuir existe dans la BDD sinon je lève une
		// exception
		if (user == null) {
			throw new UsernameNotFoundException("Utilisateur introuvable !");
		} else {
			List<GrantedAuthority> auths = new ArrayList<>();

			// c'est un copier coller : c'est pour récuperer pour chaque utilisateur son role
			user.getRoles().forEach(role -> {
				GrantedAuthority auhority = new SimpleGrantedAuthority(role.getRole());

				auths.add(auhority);
			});
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),auths);
		}
	}

}
