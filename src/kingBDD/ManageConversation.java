package kingBDD;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ManageConversation implements Serializable {

	private static final long serialVersionUID = -7193808092220789564L;
	private Map<Integer, List<String>> conversationToUser;
	private Map<String, List<Integer>> userToConversation;

	public ManageConversation() {
		this.conversationToUser = new HashMap<Integer, List<String>>();
		this.userToConversation = new HashMap<String, List<Integer>>();
	}

	public void modifyConversation(int nb, String username) {
		if (!this.conversationToUser.containsKey(nb))
			this.conversationToUser.put(nb, new LinkedList<String>());
		this.conversationToUser.get(nb).add(username);
		if (!this.userToConversation.containsKey(username))
			this.userToConversation.put(username, new LinkedList<Integer>());
		this.userToConversation.get(username).add(nb);
	}
	
	public Map<Integer, List<String>> getConversationToUser() {
		return conversationToUser;
	}
	
	public Map<String, List<Integer>> getUserToConversation() {
		return userToConversation;
	}
}