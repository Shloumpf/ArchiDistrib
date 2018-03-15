package kingBDD;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Conversation implements Serializable {

	private static final long serialVersionUID = -4277959164441740149L;
	List<String> users;
	List<Message> messages;
	
	public Conversation(String username) {
		this.users = new LinkedList<String>();
		this.users.add(username);
		this.messages = new LinkedList<Message>();
	}
	
	public void addMessage(String username, String message) {
		this.messages.add(new Message(username, message));
	}
	
	public List<String> getUsers() {
		return users;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
}
