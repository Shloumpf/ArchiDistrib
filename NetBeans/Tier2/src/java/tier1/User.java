package tier1;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
public class User implements Serializable {

	private static final long serialVersionUID = 853550801649825853L;
	private String username;
	private String password;
	private List<String> contacts;

	public User(final String username, final String password) {
		this.username = username;
		this.password = password;
		this.contacts = new LinkedList<String>();
	}
	
	public User() {
		new User("", "");
	}

	public boolean connection(String username, String password) {
		return (this.username.equals(username) && this.password.equals(password));
	}

	public void addContact(String username) {
		this.contacts.add(username);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public List<String> getContacts() {
		return this.contacts;
	}

	public String toString() {
		return this.username;
	}
}
