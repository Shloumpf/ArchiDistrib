package tier1;

import java.util.Scanner;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class Tier1 {

    private static WebTarget service = null;

    public static void main(String pArgs[]) throws Exception {

        service = ClientBuilder.newClient().target("http://localhost:8080/Tier2");

        System.out.println("Bienvenue");
        boolean connected = false;
        int choice = 0;
        String username = "";
        Scanner sc = new Scanner(System.in);
        while (!connected) {
            System.out.println("Que voulez-vous faire ?\n    1) Créer un compte\n    2) Vous connecter\n    3) Quitter");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == 3) {
                connected = true;
            }
            if (choice != 1 && choice != 2) {
                continue;
            }
            System.out.println("Nom d'utilisateur :");
            username = sc.nextLine();
            System.out.println("Mot de passe :");
            String password = sc.nextLine();
            User user = new User(username, password);
            if (choice == 1) {
                if (service.path("register").request(MediaType.TEXT_PLAIN).put(Entity.xml(user)).readEntity(String.class).equals("OK")) {
                    System.out.println("Compte créé");
                } else {
                    System.out.println("Nom d'utilisateur déjà pris");
                }
            }
            if (choice == 2) {
                if (service.path("connect").request(MediaType.TEXT_PLAIN).put(Entity.xml(user)).readEntity(String.class).equals("OK")) {
                    System.out.println("Connecté");
                    connected = true;
                } else {
                    System.out.println("Mauvais identifiants");
                }
            }
        }
        if (choice != 3) {
            choice = 0;
            while (choice != 4) {
                System.out.println("Que voulez-vous faire ?\n    1) Créer/rejoindre une conversation\n    2) Afficher vos conversations\n    3) Gérer les contacts\n    4) Quitter");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 1) {
                    int nbConversation = -1;
                    while (nbConversation == -1) {
                        System.out.println("Quel est le numéro de conversation ?");
                        nbConversation = sc.nextInt();
                        sc.nextLine();
                        if (service.path("join/" + username + "/" + nbConversation).request().get(String.class).equals("NOK")) {
                            nbConversation = -1;
                        }
                    }
                    int choiceConv = 0;
                    while (choiceConv != 3) {
                        System.out.println("Que voulez-vous faire ?\n    1) Afficher la conversation en cours\n    2) Envoyer un message\n    3) Quitter");
                        choiceConv = sc.nextInt();
                        sc.nextLine();
                        if (choiceConv == 1) {
                            String[] messages = service.path("conversation/list/" + nbConversation).request().get(String.class).split(";");
                            for (String m : messages) {
                                System.out.println(m);
                            }
                            System.out.println("\n");
                        }
                        if (choiceConv == 2) {
                            System.out.println("Tapez votre message : ");
                            String text = sc.nextLine();
                            Message message = new Message(username, text);
                            if (service.path("conversation/send/" + nbConversation).request(MediaType.TEXT_PLAIN).put(Entity.xml(message)).readEntity(String.class).equals("OK")) {
                                System.out.println("Message envoyé");
                            } else {
                                System.out.println("Votre message n'a pas pu être envoyé");
                            }
                        }
                    }
                }
                if (choice == 2) {
                    System.out.println("Conversations actives : ");
                    String[] conversations = service.path("list/" + username).request().get(String.class).split(";");
                    for (String s : conversations) {
                        System.out.print(s + " | ");
                    }
                    System.out.println("");
                }

                if (choice == 3) {
                    int choiceContact = 0;
                    while (choiceContact != 4) {
                        System.out.println("Que voulez-vous faire ?\n    1) Voir vos contacts\n    2) Lister les utilisateurs connectés\n    3) Ajouter un contact\n    4) Quitter");
                        choiceContact = sc.nextInt();
                        sc.nextLine();
                        if (choiceContact == 1) {
                            System.out.println("Vos contacts : ");
                            String[] contacts = service.path("contacts/list/" + username).request().get(String.class).split(";");
                            for (String c : contacts) {
                                System.out.println(c + " | ");
                            }
                            System.out.println("");
                        }
                        if (choiceContact == 2) {
                            System.out.println("Les contacts connectés : ");
                            String[] contacts = service.path("contacts/connected/" + username).request().get(String.class).split(";");
                            for (String c : contacts) {
                                System.out.println(c + " | ");
                            }
                            System.out.println("");
                        }
                        if (choiceContact == 3) {
                            System.out.println("Quel est le nom du contact à ajouter ?");
                            String contactName = sc.nextLine();
                            if (service.path("contacts/add/" + username + "/" + contactName).request().get(String.class).equals("OK")) {
                                System.out.println("Le contact " + contactName + " a été ajouté");
                            } else {
                                System.out.println("Le contact " + contactName + " n'a pas pu être rajouté, veuillez vérifier son nom");
                            }
                        }
                    }
                }
            }
            service.path("deconnexion/" + username).request().get(String.class).equals("NOK");
            System.out.println("Au revoir !");
        }
    }
}
