# Compte Rendu du TP 1 : Patrons de Conceptions

Noms des étudiants du binôme : MAACH Salma, MONTUSCLAT Thomas

## Exercices 1
C'est le design pattern composite. La classe `MobileObject` est la classe composante et la classe `Vehicle` est la classe composite. 
La classe `Vehicle` contient des objets de type `MobileObject` et les traite comme des objets simples. La classe `Vehicle` est responsable de la gestion 
des objets `MobileObject` et de la coordination de leurs actions.

*Doit-on récrire la méthode getVelocity() ou la méthode getMass() pour la nouvelle classe ?*

Nous n'avons pas besoin de réécrire les méthodes `getVelocity()` et `getMass()` pour la nouvelle classe `TagAlongBike`.


## Exercices 2
Le design pattern utilisé dans la méthode `getVelocity()` pour parcourir les composants d'un véhicule est le design pattern **Iterator**.
Il permet de parcourir les éléments d'une collection sans avoir besoin de connaitre et exposer son code interne.

Grâce au design pattern iterateur nous n'avons pas besoin de modifier la méthode `getVelocity()` pour changer la structure de donnée utilisée pour stocker les composants d'un véhicule de `Set` à `List`.

## Exercices 3
```java
private static Clock instance;

private Clock() {
}

public static Clock getInstance() {
    if (instance == null) {
        instance = new Clock();
    }

    return instance;
}
```

Nous avons créé une instance statique de la classe `Clock` appelée `instance`. La méthode `getInstance()` vérifie si l'instance est nulle, si c'est le cas, elle crée une nouvelle instance de `Clock`. Elle retourne alors soit la nouvelle instance soit l'existante. De cette manière, nous nous assurons qu'il n'y a qu'une seule instance de la classe `Clock` dans l'application.
De plus nous avons passé le constructeur de la classe en privé afin d'empêcher d'instancier des objets Clock sans passer par `getInstance()`.

## Exercices 4
Les classes `Bike` et `Wheel` ne sont pas dans le même package. Nous avons des dépendances cycliques entre les deux classes. La classe `Bike` dépend de la classe `Wheel` et la classe `Wheel` dépend de la classe `Bike`.
Cette dépendance n'adhère pas aux bonnes pratiques.

La classe `Wheel` est utilisée pour calculer la vitesse de la classe `Bike` grâce à `getVelocity()`.

Y a-t-il déjà une abstraction de la classe Bike qui isole cette fonctionnalité ?
Il y a une abstraction de la classe `Bike` qui isole cette fonctionnalité dans `Vehicle` dans le package transport.
Il faut alors modifier l'attribut `drive` de la classe `Wheel` pour qu'il soit de type `Vehicle` au lieu de `Bike` et qu'on appelle `getPush()` sur une instance de `Vehicle`.

## Exercices 5
Utiliser le patron de conception patron de méthode[^4] pour centraliser cette étape commune à un seul endroit et d'éviter le code en doublon.

Modifiez la classe NamedLogger et ses sous-classes pour réaliser ce patron.

Pour cela, nous avons créé une méthode abstraite `logMessage` dans la classe `NamedLogger` qui est appelée par la méthode `log` pour chaque sous-classe.
De cette manière on évite de duppliquer du code dans les classes `FileLogger` et `ConsoleLogger`. On a le squelette commun dans notre classe mère `NamedLogger` et on laisse les classes filles implémenter la méthode `logMessage` selon les différents besoins.

```java
@Override
public void log(String format, Object... args) {
    String entry = String.format(format, args);
    String message = String.format("%s\t%s\n", this.name, entry);
    logMessage(message);
}

protected abstract void logMessage(String message);
```

Dans notre exemple nous avions entry et message qui étaient duppliqués avant l'implémentation du design pattern.

## Exercices 6
Pour implémenter le design pattern méthode de fabrique nous avons créé une classe abstraite `LoggerFactory` qui contient la méthode `getLogger` qui return un ConsoleLogger, de cette manière il suffit de modifier la déclaration dans nos trois classes `Wheel`, `BikeSimulator` et `Vehicle` en appellant notre méthode et en passant le nom désiré en paramètre.

```java
public abstract class FactoryLogger {
    
    public static Logger getLogger(String name) {
        return new ConsoleLogger(name);
    }
}
```

```java
private final Logger logger = FactoryLogger.getLogger("BikeSimulator");

private final Logger logger = FactoryLogger.getLogger("Vehicle");

private final Logger logger = FactoryLogger.getLogger("Wheel");
```

On obtient donc les logs dans la console de manière centralisée.

## Exercices 7
Pour implémenter le design pattern décorateur nous avons créé notre décorateur abstrait `LoggerDecorator` ainsi que notre décorateur concret `TimestampedLoggerDecorator`.
Cette classe possède un attribut `logger` de type `Logger` qui va être notre élément à décorer. On redéfinit la méthode `logMessage` pour ajouter l'heure actuelle à notre message.

```java
private Logger logger;

public TimeStampedLoggerDecorator(Logger logger) {
    this.logger = logger;
}

@Override
// récupération de l'heure actuelle afin de l'ajouter au message de log
public void log(String format, Object... args) {
    String now = new SimpleDateFormat("HH:mm:ss").format(new Date());
    this.logger.log(String.format("[%s] %s", now, format), args);
}
```

## Exercices 8

Peut-on avoir plusieurs lignes dans le fichier fr.polytech.sim.cycling.Bike ? À quoi correspond chaque de ces lignes ?

Le design pattern utilisé par la classe Context vis-à-vis de ServiceLoader est Façade. La classe Context fournit une interface simplifiée pour l'utilisation de ServiceLoader et joue le rôle de point d'entrée.
Au lieu du mot clé **new** on utilise alors `inject()` de la classe context de cette manière :

```java
Bike bike = Context.inject(Bike.class);
```

De plus, il est tout à fait possible d'avoir plusieurs lignes dans le fichier `fr.polytech.sim.cycling.Bike`. 
Chaque ligne correspond à une implémentation de l'interface `Bike` que l'on souhaite injecter et notre `ServiceLoader` retourne alors un itérator. 

## Exercices 9

Le type de retour de `injectAll()` est un itérator sur l'ensemble des instances la classe passée en paramètre.
Le design pattern utilisé est l'Iterator permettant de parcourir les éléments d'une collection sans avoir besoin de connaitre et exposer son code interne comme expliqué précédemment.

Implémentation de la méthode `injectAll()` :

```java
public static <T> Iterator<T> injectAll(Class<T> klass) {
    return ServiceLoader.load(klass).iterator();
}
```

Maintenant que l'on sait qu'on peut avoir plusieurs instances de vélos retournés par `injectAll()`, on peut boucler dessus notre iterator pour simuler plusieurs vélos.

On ajoute plusieurs lignes dans le fichier `fr.polytech.sim.cycling.Bike` une pour un `SimpleBike` et une pour un `TagAlongBike`.

fr.polytech.sim.cycling.SimpleBike
fr.polytech.sim.cycling.TagAlongBike


```java
Bike bike;

Iterator<Bike> bikes = Context.injectAll(Bike.class);

while (bikes.hasNext()) {
    bike = bikes.next();
    
    this.logger.log(bike.getClass().getSimpleName() + "'s speed %.2f Km/h.", bike.getVelocity());
    this.logger.log(bike.getClass().getSimpleName() + "'s mass %.2f Kg.", bike.getMass());
}
```


