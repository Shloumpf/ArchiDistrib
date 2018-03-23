# Architecture Distribuée Projet LP MI-AW

Ce qui va suivre montre la démarche pour démarrer le projet

Commencez par cloner le projet et laisser le dossier de côté

## Tier 3

### Prérequis

Afin d'installer le Tier3, il faut tout d'abord installer l'IDE Eclipse

```
https://www.eclipse.org/downloads/
```

### Installation du projet

Il faut maintenant créer un nouveau projet dans File -> New -> Java Project
Donnez-lui le nom qu'il vous plaira.

Créez ensuite un nouveau package dans le dossier src appelé main.

Copiez à présent le contenu du dossier Eclipse/src/main/ depuis le git vers ce package.

### Lancement

Maintenant, il vous suffit simplement de lancer la classe ServerStart.java en faisant Clic-droit sur le projet -> Run as -> Java Application.

Votre server RMI, ainsi que votre Tier 3 entier est démarré.

## Tier 1 & 2

### Prérequis

Commencez par installer NetBeans à l'url suivante, choisissez le logiciel contenant JEE et GlassFish

```
https://netbeans.org/downloads/index.html
```

### Installation du projet

Dans NetBeans :
+ New Project
	-> Java Web
	-> Web Application
	Next
	-> Projet name : Tier2
	Next
	-> Server : GlassFish
	-> Java EE Version : 7
	-> Context Path : /Tier2
	Finish

Clic-droit sur le dossier Source Packages -> New -> Java Package...
Vous nommez le package "main"

Vous pouvez ainsi recréer un second package nommé tier1

A présent, vous pouvez copier l'intégralité des fichiers de main et tier1 contenus dans NetBeans\Tier2\src\java depuis le git vers les deux packages correspondants dans NetBeans

### Lancement du Tier 2

Pour lancer le Tier 2, il suffit de faire :

-> Clic-droit sur la racine du projet (Tier2) -> Clean and Build
-> Clic-droit idem -> Run

Un fenêtre de console "GlassFish Server 4.1.1" devrait apparaître, il suffit alors d'attendre le message :

```
Ìnfos :		Tier2 was successfully deploed in X XXX milliseconds.
```

Si un navigateur internet se lance, vous pouvez le fermer, nous ne l'utiliserons pas dans ce projet.

Votre server Tier 2 est donc lancé, avec le webservice REST correspondant.

### Lancement du Tier 3

Pour lancer une instance du Tier 3, il suffit de faire :

-> Clic-droit sur la classe Tier1.java contenue dans le package tier1 -> Run File

Une console appelée "Tier2 (run)" devrait se lancer contenant l'interface utilisateur du Tier 1

### Utilisation de plusieurs instances

Si vous voulez créer plusieurs clients Tier 1 à la fois, il suffit de répéter l'étape précédente autant de fois que voulu, chaque console ainsi créée est une nouvelle instance indépendante des autres

## Authors

* **Léa Lesnier**
* **Anthony Persello**
* **Pierre Eymery**