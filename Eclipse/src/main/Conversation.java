package main;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Conversation implements Serializable {

	private static final long serialVersionUID = -4277959164441740149L;
	private int id;
	private List<String> users;
	private List<Message> messages;
	
	public Conversation(int id, String username) {
		this.id = id;
		this.users = new LinkedList<String>();
		this.users.add(username);
		this.messages = new LinkedList<Message>();
	}
	
	public Conversation() {
		new Conversation(0, "");
	}
	
	public void addMessage(Message message) {
		this.messages.add(message);
	}
	
	public int getId() {
		return this.id;
	}
	
	public List<String> getUsers() {
		return this.users;
	}
	
	public List<Message> getMessages() {
		return this.messages;
	}
}
