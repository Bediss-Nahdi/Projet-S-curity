package fr.bediss.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.json.JsonParseException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


import fr.bediss.entities.User;

/* TROISIEME CLASS A CREER DANS LE MODULE SECURITY*/

// Pourquoi cette Class : Une génération du Token est là suite une validation d'un username te passeword valide 

// 1 // Faire un extends UsernamePasswordAuthenticationFilter
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// 2 // Importer la Class AuthenticationManager ci-dessous;
	private AuthenticationManager authenticationManager;

	// 3 // Boutons droit - source - Genrate Constructor using fields
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	// 4 // Redéfinir une méthode ==> Boutons droit - source - overide /
	// implemntation méthodes --> attemptAuthentication

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		/* 4 - A */
		// Extraction d'un User
		User user = null;
		try {
			/* 4 - B */
			// Il faut désérialiser le User
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}

	// 5 // J'implémenet une méthode successfulAuthentication ==> Boutons droit -
	// source - overide /
	// implemntation méthodes --> successfulAuthentication
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authResult
				.getPrincipal();
		
		List<String> roles = new ArrayList<>();
		springUser.getAuthorities().forEach(au -> {
			roles.add(au.getAuthority());
		});
		
		
		String jwt = JWT.create().withSubject(springUser.getUsername())
				.withArrayClaim("roles", roles.toArray(new String[roles.size()]))
				.withExpiresAt(new Date(System.currentTimeMillis() + SecParams.EXP_TIME)) // == date d'expiration (après 10 jours)
				.sign(Algorithm.HMAC256(SecParams.SECRET));
		response.addHeader("Authorization", jwt);
	}

}
