package main;

import java.rmi.*;
import java.rmi.registry.*;
import java.text.DateFormat;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

/**
 * REST Web Service
 *
 * @author lesnierl
 */
@Path("/")
public class Tier2 {

    private static Server server;
    private static List<String> userConnected;

    static {
        userConnected = new LinkedList<String>();
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            Remote r = registry.lookup("Server");
            if (r instanceof Server) {
                server = (Server) r;
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @PUT
    @Path("register")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public String register(JAXBElement<User> u) throws RemoteException {
        User user = u.getValue();
        if (server.register(user.getUsername(), user.getPassword())) {
            return "OK";
        }
        return "NOK";
    }

    @PUT
    @Path("connect")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public String connect(JAXBElement<User> u) throws RemoteException {
        User user = u.getValue();
        if (server.connect(user.getUsername(), user.getPassword())) {
            userConnected.add(user.getUsername());
            return "OK";
        }
        return "NOK";
    }

    @GET
    @Path("join/{username}/{nbConversation}")
    @Produces("text/plain")
    public String join(@PathParam("username") String username, @PathParam("nbConversation") int nbConversation) throws RemoteException {
        if (server.join(username, nbConversation)) {
            return "OK";
        }
        return "NOK";
    }

    @GET
    @Path("list/{username}")
    @Produces("text/plain")
    public String list(@PathParam("username") String username) throws RemoteException {
        String res = "";
        List<Conversation> conversations = server.getHistoricConversations(username);
        if (conversations == null) {
            return res;
        }
        for (Conversation c : conversations) {
            res += c.getId() + ";";
        }
        return res;
    }

    @GET
    @Path("contacts/list/{username}")
    @Produces("text/plain")
    public String contactList(@PathParam("username") String username) throws RemoteException {
        String res = "";
        List<String> contacts = server.getContacts(username);
        for (String s : contacts) {
            res += s + ";";
        }
        return res;
    }

    @GET
    @Path("contacts/connected/{username}")
    @Produces("text/plain")
    public String connected(@PathParam("username") String username) {
        String res = "";
        for (String s : userConnected) {
            if (s.equals(username)) {
                continue;
            }
            res += s + ";";
        }
        return res;
    }

    @GET
    @Path("contacts/add/{username}/{contactName}")
    @Produces("text/plain")
    public String addFriend(@PathParam("username") String username, @PathParam("contactName") String contactName) throws RemoteException {
        if (server.addContact(username, contactName)) {
            return "OK";
        }
        return "NOK";
    }

    @GET
    @Path("conversation/list/{nbConversation}")
    @Produces("text/plain")
    public String getConversation(@PathParam("nbConversation") int nbConversation) throws RemoteException {
        String res = "";
        List<Message> messages = server.getConversation(nbConversation);
        if (messages == null) {
            return res;
        }
        for (Message m : messages) {
            res += m.getDate().toString().substring(8, 10) + "/" + m.getDate().toString().substring(4, 7) + " " + m.getDate().toString().substring(11, 19) + " | " + m.getSenderUsername() + " | " + m.getMessage() + ";";
        }
        return res;
    }

    @PUT
    @Path("conversation/send/{nbConversation}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public String sendMessage(@PathParam("nbConversation") int nbConversation, JAXBElement<Message> m) throws RemoteException {
        Message message = m.getValue();
        if (server.sendMessage(nbConversation, message.getSenderUsername(), message.getMessage())) {
            return "OK";
        }
        return "NOK";
    }

    @GET
    @Path("deconnexion/{username}")
    @Produces("text/plain")
    public String deconnexion(@PathParam("username") String username) {
        if (userConnected.contains(username)) {
            userConnected.remove(username);
            return "OK";
        } else {
            return "NOK";
        }
    }
}
