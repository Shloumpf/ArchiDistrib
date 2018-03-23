package main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerStart {

	public static void main(String[] pArgs) {
		try {
			ServerImpl server = new ServerImpl();
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("Server", server);
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}