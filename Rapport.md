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

    public static Clock getInstance() {
        if (instance == null) {
            return new Clock();
        }

        return instance;
    }
```

Nous avons créé une instance statique de la classe `Clock` appelée `instance`. La méthode `getInstance()` vérifie si l'instance est nulle, si c'est le cas, elle crée une nouvelle instance de `Clock` et la retourne. Sinon, elle retourne l'instance existante. De cette manière, nous nous assurons qu'il n'y a qu'une seule instance de la classe `Clock` dans l'application.

## Exercices 4
Les classes `Bike` et `Wheel` ne sont pas dans le même package. Nous avons des dépendances cycliques entre les deux classes. La classe `Bike` dépend de la classe `Wheel` et la classe `Wheel` dépend de la classe `Bike`.
Cette dépendance n'adhère pas aux bonnes pratiques.


## Exercices 5

## Exercices 6

## Exercices 7

## Exercices 8

## Exercices 9


