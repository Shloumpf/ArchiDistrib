package kingBDD;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 853550801649825853L;
	private String username;
	private String password;

	public User(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	public boolean connection(String username, String password) {
		return (this.username.equals(username) && this.password.equals(password));
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String toString() {
		return this.username;
	}
}
