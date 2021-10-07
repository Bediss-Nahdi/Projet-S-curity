package fr.bediss.services.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bediss.entities.Role;
import fr.bediss.entities.User;
import fr.bediss.repositories.RoleRepository;
import fr.bediss.repositories.UserRepositories;
import fr.bediss.services.UserService;


@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	UserRepositories userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	public User saveUser(User user) {
		//Je crypte mon mot de passe avant de l'enrgister
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		//Enregistrement
		return userRepository.save(user);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Role addRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public User addRoleToUser(String username, String rolename) {
		User usr = userRepository.findByUsername(username);
		Role role = roleRepository.findByRole(rolename);
		
		usr.getRoles().add(role);
		
		return usr;

	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
