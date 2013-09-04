package at.tugraz.kmi.medokyservice.datapreprocessing;

import java.util.HashMap;

public class UserManager {

	HashMap <String, User> users;
	
	public UserManager(){
		users = new HashMap<String, User>();
	}
	
	public User getUser(String name){
		if (users.containsKey(name))
			return users.get(name);
		User user = new User(name);
		users.put(name, user);
		return user;
	}
}
