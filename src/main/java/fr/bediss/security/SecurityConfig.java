package fr.bediss.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


										/* PREMIERE CLASS A CREER DANS LE MODULE SECURITY*/


// 1 // Ajouter l'annotation sur ma class : @EnableWebSecurity

// 2 // Faire un extends WebSecurityConfigurerAdapter
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	// 3 // Ajouter les deux attributs ci-dessous car nous allons en avoir besoin plus tard
	
	@Autowired
	UserDetailsService userDetailsService;  //: Incoryable c'est une Inetrface :p ==> c'est pour ça que dans la deuxime class  MyUserDetailsService on implémente l'interface UserDetailsService 
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;  //: Incoryable c'est une class :p

	
	// 4 // Importer ces deux méthodes : bouton droit / soucre / overird méthode ==> importer les méthodes ci-dessous (cocher au final deux cases)
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/* 4 - A */
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		
		
	}
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* 4 - B */
		http.csrf().disable();
		
		/* 4 - C */
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
		/* Cette partie c'est pour les users */
		
		/* 4 - D */
		http.authorizeRequests().antMatchers("/login").permitAll(); //==> donner à tout le monde le droit de se connecter
			
		// Un fois que le contrôller est fait [ -dans notre cas : getAllUsers()- ]
		http.authorizeRequests().antMatchers("/all").hasAuthority("ADMIN");
		
		
		
		/* Cette partie c'est pour les produits */
		
		// UNE FOIS QUE TOUTES LES CLASS SONT MISES EN PLACE  ==> Restrindre l'accès aux api grâce antMatchers
		//consulter tous les produits

		http.authorizeRequests().antMatchers("/api/all/**").hasAnyAuthority("ADMIN","USER");

		 //consulter un produit par son id

		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/**").hasAnyAuthority("ADMIN","USER");

		 //ajouter un nouveau produit
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/**").hasAuthority("ADMIN");

		 //modifier un produit
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/**").hasAuthority("ADMIN");

		//supprimer un produit
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/**").hasAuthority("ADMIN");
		
		
		
		/* 4 - E */
		http.authorizeRequests().anyRequest().authenticated(); // ==> (à part l'url "/login") : il faut une authentification

		
		
		// UNE FOIS QUE LA TROISIEME CLASS appelé JWTAuthenticationFilter a été crée on met (et pas avant):
		http.addFilter(new JWTAuthenticationFilter (authenticationManager())) ;
		
		// UNE FOIS QUE LA QUATRIEME CLASS appelé JWTAuthorizationFilter a été crée on met (et pas avant):
		http.addFilterBefore(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
		
		
		
		
		

	}
	
	

	
	 
	
	
	
	

}
