package kingBDD;

import java.util.Date;

public class Message {
	
	private String senderUsername;
	private Date date;
	private String message;
	
	public Message(String senderUsername, String message) {
		this.senderUsername = senderUsername;
		this.date = new Date();
		this.message = message;
	}
	
	public String getSenderUsername() {
		return this.senderUsername;
	}
	
	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
