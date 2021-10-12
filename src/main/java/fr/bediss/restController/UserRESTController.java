package fr.bediss.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.bediss.entities.User;
import fr.bediss.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserRESTController {

	@Autowired
	UserService userService;

	@RequestMapping(path = "all", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	@RequestMapping(value = "/login/{username}", method = RequestMethod.GET)
	public User getUserByUsername(@PathVariable("username") String username) {
		return userService.findUserByUsername(username);
	}
}
