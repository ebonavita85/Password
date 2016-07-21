package it.bonavita.entity;

import java.util.List;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Export {

	@ElementList(name="Passwords")
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
