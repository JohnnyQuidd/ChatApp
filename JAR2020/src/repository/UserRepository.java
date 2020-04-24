package repository;

import java.util.ArrayList;

import javax.ejb.Stateless;

import model.User;

@Stateless
public class UserRepository {
	private ArrayList<User> listOfUsers = new ArrayList<User>();
	
	public UserRepository() {
		listOfUsers.add(new User("admin", "admin"));
		listOfUsers.add(new User("guest", "guest"));
	}
	
	public ArrayList<User> getAllRegisteredUsers() {
		return listOfUsers;
	}
	
	public User findUserByUsername(String username) {
		for(User u : listOfUsers) {
			if(u.getUsername().equals(username))
				return u;
		}
		return null;
	}
	
	public void registerNewUser(User user) {
		if(listOfUsers == null)
			listOfUsers = new ArrayList<User>();
		listOfUsers.add(user);
	}
}
