package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements Server {

	private static final long serialVersionUID = -8293495650002929694L;

	protected ServerImpl() throws RemoteException {
		super();
		setup();
	}

	public void setup() {
		File test = new File("conversations");
		if (test.exists())
			return;
		final ManageConversation mc = new ManageConversation();
		ObjectOutputStream oos = null;
		FileOutputStream file;
		try {
			file = new FileOutputStream("conversations");
			oos = new ObjectOutputStream(file);
			oos.writeObject(mc);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean register(String username, String password) throws RemoteException {
		File test = new File("user." + username);
		if (test.exists())
			return false;
		final User user = new User(username, password);
		ObjectOutputStream oos = null;
		FileOutputStream file;
		try {
			file = new FileOutputStream("user." + username);
			oos = new ObjectOutputStream(file);
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}

	public boolean connect(String username, String password) {
		File test = new File("user." + username);
		if (!test.exists())
			return false;
		ObjectInputStream ois = null;
		try {
			final FileInputStream file = new FileInputStream("user." + username);
			ois = new ObjectInputStream(file);
			final User user = (User) ois.readObject();
			ois.close();
			return user.connection(username, password);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean join(String username, int nbConversation) {
		File test = new File("conversation." + nbConversation);
		editConversation(nbConversation, username);
		if (!test.exists()) {
			final Conversation conversation = new Conversation(nbConversation, username);
			ObjectOutputStream oos = null;
			FileOutputStream file;
			try {
				file = new FileOutputStream("conversation." + nbConversation);
				oos = new ObjectOutputStream(file);
				oos.writeObject(conversation);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					if (oos != null) {
						oos.flush();
						oos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	public void editConversation(int nb, String username) {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		FileOutputStream outFile;
		final FileInputStream inFile;
		try {
			inFile = new FileInputStream("conversations");
			ois = new ObjectInputStream(inFile);
			final ManageConversation mc = (ManageConversation) ois.readObject();
			ois.close();
			mc.modifyConversation(nb, username);
			outFile = new FileOutputStream("conversations", false);
			oos = new ObjectOutputStream(outFile);
			oos.writeObject(mc);
			oos.flush();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Conversation> getHistoricConversations(String username) throws RemoteException {
		ObjectInputStream ois = null;
		try {
			FileInputStream file = new FileInputStream("conversations");
			ois = new ObjectInputStream(file);
			final ManageConversation mc = (ManageConversation) ois.readObject();
			ois.close();
			List<Integer> conversationsNb = mc.getConversationsFromUser(username);
			if (conversationsNb == null)
				return null;
			List<Conversation> response = new LinkedList<Conversation>();
			for (Iterator<Integer> i = conversationsNb.iterator(); i.hasNext();) {
				file = new FileInputStream("conversation." + i.next());
				ois = new ObjectInputStream(file);
				response.add((Conversation) ois.readObject());
				ois.close();
			}
			return response;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean addContact(String username, String friend) throws RemoteException {
		File test = new File("user." + username);
		if (!test.exists())
			return false;
		test = new File("user." + friend);
		if (!test.exists())
			return false;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			FileInputStream inFile = new FileInputStream("user." + username);
			ois = new ObjectInputStream(inFile);
			User user = (User) ois.readObject();
			ois.close();
			user.addContact(friend);
			FileOutputStream outFile = new FileOutputStream("user." + username);
			oos = new ObjectOutputStream(outFile);
			oos.writeObject(user);
			oos.flush();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public List<String> getContacts(String username) throws RemoteException {
		ObjectInputStream ois = null;
		try {
			FileInputStream inFile = new FileInputStream("user." + username);
			ois = new ObjectInputStream(inFile);
			User user = (User) ois.readObject();
			ois.close();
			return user.getContacts();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Message> getConversation(int nbConversation) throws RemoteException {
		ObjectInputStream ois = null;
		try {
			FileInputStream inFile = new FileInputStream("conversation." + nbConversation);
			ois = new ObjectInputStream(inFile);
			Conversation conversation = (Conversation) ois.readObject();
			ois.close();
			return conversation.getMessages();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean sendMessage(int nbConversation, String username, String message) throws RemoteException {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		FileOutputStream outFile;
		final FileInputStream inFile;
		try {
			inFile = new FileInputStream("conversation." + nbConversation);
			ois = new ObjectInputStream(inFile);
			final Conversation c = (Conversation) ois.readObject();
			ois.close();
			c.addMessage(new Message(username, message));
			outFile = new FileOutputStream("conversation." + nbConversation, false);
			oos = new ObjectOutputStream(outFile);
			oos.writeObject(c);
			oos.flush();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}