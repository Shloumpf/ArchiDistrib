package tier1;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1795432780554687087L;
	private String senderUsername;
	private Date date;
	private String message;
	
	public Message(String senderUsername, String message) {
		this.senderUsername = senderUsername;
		this.date = new Date();
		this.message = message;
	}
	
	public Message() {
		new Message("", "");
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
