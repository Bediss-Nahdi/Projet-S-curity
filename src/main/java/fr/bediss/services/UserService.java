package fr.bediss.services;

import fr.bediss.entities.Role;
import fr.bediss.entities.User;



public interface UserService {

	User saveUser(User user);
	User findUserByUsername (String username);
	Role addRole(Role role);
	User addRoleToUser(String username, String rolename);
	
}
