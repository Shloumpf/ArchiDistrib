package kingBDD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Server {

	public static void main(String[] args) {
		
	}
	
	public static void setup() {
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
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void register(String username, String password) {
		final User user = new User(username, password);
		ObjectOutputStream oos = null;
		FileOutputStream file;
		try {
			file = new FileOutputStream("user." + username);
			oos = new ObjectOutputStream(file);
			oos.writeObject(user);
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

	public static boolean login(String username, String password) {
		ObjectInputStream ois = null;
		try {
			final FileInputStream file = new FileInputStream("user." + username);
			ois = new ObjectInputStream(file);
			final User user = (User) ois.readObject();
			return user.connection(username, password);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void editConversation(int nb, String username) {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		FileOutputStream outFile;
		final FileInputStream inFile;
		try { 
			inFile = new FileInputStream("conversations");
			ois = new ObjectInputStream(inFile);
			final ManageConversation mc = (ManageConversation) ois.readObject();
			mc.modifyConversation(nb, username);
			outFile = new FileOutputStream("conversations");
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

	public static void newConversation(String username) {
		int nb = 0;
		boolean free = false;
		while (!free) {
			File test = new File("conversation." + nb);
			if (!test.exists())
				free = true;
			else
				nb++;
		}
		editConversation(nb, username);
		final Conversation conversation = new Conversation(nb, username);
		ObjectOutputStream oos = null;
		FileOutputStream file;
		try {
			file = new FileOutputStream("conversation." + nb);
			oos = new ObjectOutputStream(file);
			oos.writeObject(conversation);
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
}