# OOP_Abschlussprojekt

## Aufgabenstellung
Labyrinth-Person
 

Ziel dieser Aufgabe soll es sein ein kleines Spiel zu entwerfen. Erstellen Sie dafür entsprechende Pakete um ihre Klassen zu kapseln.

Diese Serie erstreckt sich über 2 Wochen und ist so geplant, dass 2 Personen daran arbeiten. Sie soll nur in 2er-Teams abgegeben werden. Versuchen Sie also nicht erst in der letzten Woche anzufangen, sondern bearbeiten Sie diese Aufgabe frühzeitig. Sie können gerne die Kleingruppe aus dem vorherigen Projekt wiederverwenden.

Falls Sie die Zweiergruppe für dieses Projekt ändern wollen, machen Sie das bitte über die Zweiergruppenauswahl. Eigentlich ist das im Moodle gar kein Problem, aber manchmal.... Deshalb schicken Sie bitte (und nur im Falle von Änderungen!) an cze+oopuebungsorgafragen@informatik.uni-kiel.de eine Mail mit Gruppennamen und Personen in den Gruppen jeweils für Adressbuchprojekt und Abschlussprojekt. Bitte kontrollieren Sie nachher im Falle einer Gruppen-Änderung auch selbst, ob Sie auch wirklich Ihre Punkte bekommen haben.

 

Angehängt an diese Aufgabe finden Sie eine rudimentäre Version von Labyrinth-Person.

Es handelt sich um ein Programm, dass erlaubt, einen Spieler auf einer Karte zu bewegen. Bereits mitgeliefert sind zwei verschiedene Views für dieses Spiel. Eine, die den Spielstatus auf der Console ausgibt, und eine, die mit Hilfe von Java Swing eine graphische Darstellung erzeugt.

Die Bestandteile dieses Programms kommunizieren wie unten dargestellt.



Dieses Spiel soll zu einer funktionierenden Implementierung von Labyrinth-Person erweitert werden:

1. Generieren Sie ein Spielfeld sowie Start und Ziel.

Statt einem leeren Feld sollte beim Start also ein Labyrinth mit fester Größe (mindestens 10x10) mit Start und Ziel generiert werden.

Labyrinth-Person startet auf dem Startfeld und muss das Zielfeld erreichen, um zu gewinnen.

Überlegen Sie sich, wie Sie verschiedene Arten von Feldern (Start, Ziel, Gang, Wand) auf Ihrem 2-dimensionalen Spielfeld darstellen und speichern können.

Passen Sie dabei sowohl die grafische View als auch die konsolenbasierte View entsprechend an.

2. Schwierigkeit und Verfolger:

Labyrinth-Person wird auf dem Weg durch das Labyrinth verfolgt. Das Spiel findet rundenbasiert statt. Wenn Labyrinth-Person einen Zug ausführt, bewegen sich seine Verfolger entsprechend.

Generieren Sie Verfolger, die Labyrinth-Person (mehr oder weniger gut) verfolgen. Sobald Labyrinth-Person sich auf dem gleichen Feld wie ein Verfolger befindet, ist das Spiel verloren.

Die Schwierigkeit, also die Anzahl der Verfolger, die Geschwindigkeit der Verfolger, ihre Zielstrebigkeit oder Ähnliches, sollte über die GUI anpassbar sein.

3. Darstellung und GUI
 
Überlegen Sie sich eine ansprechende grafische Darstellung und ersetzen Sie die tristen Rechtecke in der gegebenen Implementierung. 
Außerdem sollte das Spiel aus der GUI heraus neu startbar sein.
 
 
 
4. Dokumentation (Am besten schon während der vorherigen Schritte anfangen):
 
Alle Methoden, Attribute und Klassen sollen hinreichend dokumentiert sein (Kommentare und Javadoc).
 
Die Dokumentation enthält die benutze Java-Version, die benutze Entwicklungsumgebung und Beschreibung, wie Ihr Spiel gestartet und gespielt werden soll.
Erklären Sie außerdem, welche Features Sie eingebaut haben und wie ihre GUI funktioniert.
 
5. Kapselung:
 
Alle Attribute aller Klassen sollten privat sein (mit Ausnahme vordefinierter Atttribute).
Model, View und Controller sollten nur über festgelegte und dokumentierte Schnittstellen kommunizieren dürfen.
 
Abgabe:

Die Abgabe besteht aus dem .zip Archiv, im dem alle Klassen in ihrem entsprechenden Paket enthalten sind, sowie ihre Dokumentation als txt Textdatei. Wir erwarten, dass Java-Code ohne Probleme kompiliert und lauffähig ist.
 
 
Tipps:
Die Dokumentation und Methoden von Graphics können hilfreich sein, um Dinge zu zeichnen.
 

Optional: Überlegen Sie sich zusätzliche Features
Gänge die nur Labyrinth-Person passieren kann,
Powerups
Verstecke um Verfolgern zu entgehen
Echtzeit Modus
Rückgängigmachen von Zügen im rundenbasierten Spiel
Erstellen Sie zufällige oder unendliche Labyrinthe
Bestenliste (und Punkte)

## Dokumentation

Wir haben Virtual Studio Code mit
java.vendor = Eclipse Adoptium,
java.version = 21.0.7
und
java 1.8.0 openjdk von redhat benutzt.


## How to use
When the Labyrinth class (our main class) is run, it opens the game in the graphic view in a new window as well as in the console.^

The goal of the game is to reach the orange goal space without being caught.

Automatically a game on difficulty 1 is started. By pressing the buttons on the bottom border agame of the same difficulty can be started by pressing "New Game" or the difficulty can be adjusted by the difficulty buttons, which also starts a new game.

When a game is running you can move the player character (P or the mouse) by pressing the arrow keys on the keyboard. The player can move one space every turn after which the enemies (^ or the cats) will move themselves and try to close in on the player. Neither of the characters can walk into walls (# or black game board spaces). If the player is caught a game over message will be portryed as well a "C" in console view at the players position.

When the game is over, either bc of game over or success, the arrow inputs are blocked and the buttons must be used to start a new game. The game can be closed by stopping the programm or exiting the window/console view.

## What we used