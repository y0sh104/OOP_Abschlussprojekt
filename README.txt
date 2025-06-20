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

## Anleitung

### How to use


### What we used

Grafische Umsetzung:
Um Buttons darzustellen, die reaktiv auf Inputs des Users reagieren, nutzen wir das Modul javax.swing.JButton.
Damit wir diese Buttons anschließend passend in unser GUI einfügen können, nutzen wir ebenfalls javax.swing.JPanel.

Mit diesen beiden Module gelingt es, die Buttons für die Spielsteuerung in unserem GUI anzuzeigen und mit ActionListeners eine Interaktion
mit dem Spiel zu ermöglichen.

Damit die Charaktere im Spiel nicht nur geometrische Figuren sind, haben wir uns dazu entschieden, diese mit PNG-Bildern zu ersetzen.
Dafür nutzen wir die Module java.awt.image.BufferedImage und imageio.ImageIO.
Damit wir die auf die PNG-Dateien zugreifen können, nutzen wir ebenfalls java.io.InputStream.

Somit ist sichergestellt, dass die PNG-Bilder die geometrischen Figuren ersetzen, aber dennoch derselben Spiellogik folgen.

Weitere grafische Entscheidungen:
Das Spielfeld wird als 2D-Array dargestellt, um gezielt mit Koordinaten jedes Feld auszuwählen.
Jedes Feld hat einen sogenannten FieldType, um die Funktion eindeutig zuordnen zu können.
Das Start- sowie das Zielfeld sind farblich hervorgehoben und immer an derselben Stelle.
Um ein Ausbrechen an den Rändern zu verhindern, haben wir um das gesamte Spielfeld eine wall gezogen, damit alle begehbaren Felder
eingeschlossen sind.