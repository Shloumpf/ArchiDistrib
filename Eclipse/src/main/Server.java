package main;

import java.rmi.*;
import java.util.List;

public interface Server extends Remote {

	public boolean register(String username, String password) throws RemoteException;
	
	public boolean connect(String username, String password) throws RemoteException;
	
	public boolean join(String username, int nbConversation) throws RemoteException;

	public List<Conversation> getHistoricConversations(String username) throws RemoteException;

	public boolean addContact(String username, String friend) throws RemoteException;
	
	public List<String> getContacts(String username) throws RemoteException;
	
	public List<Message> getConversation(int nbConversation) throws RemoteException;
	
	public boolean sendMessage(int nbConversation, String username, String message) throws RemoteException;
}
