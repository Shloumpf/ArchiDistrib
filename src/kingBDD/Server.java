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

	public static void newConversation(String username) {
		final Conversation conversation = new Conversation(username);
		ObjectOutputStream oos = null;

		FileOutputStream file;
		try {
			int nb = 0;
			boolean free = false;
			while (!free) {
				File test = new File("conversation." + nb);
				if (!test.exists())
					free = true;
				else
					nb++;
			}
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