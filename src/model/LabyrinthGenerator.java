package model;

import java.util.Random;

/**
 * Ein Superdummer Labyrinth Generator - aber so richtig dumm.
 */
public class LabyrinthGenerator {
    
    private final Random random;
    
    /**
     * Erstellt einen neuen Labyrinth-Generator.
     */
    public LabyrinthGenerator() {
        this.random = new Random();
    }
    
    /**
     * Generiert ein einfaches Labyrinth mit Wänden am Rand und Gängen innen.
     * 
     * @param width Breite des Labyrinths
     * @param height Höhe des Labyrinths
     * @return 2D-Array mit Feldtypen
     */

    private void carveMaze(FieldType[][] maze, int x, int y) {
        // Richtungsoffsets: oben, rechts, unten, links
        int[][] directions = {{0,-1}, {1,0}, {0,1}, {-1,0}};
        // Mische die Richtungen für zufällige Gänge
        shuffleArray(directions);

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int nx = x + dx * 2;
            int ny = y + dy * 2;

            if (isInBounds(maze, nx, ny) && maze[ny][nx] == FieldType.WALL) {
                maze[y + dy][x + dx] = FieldType.PATH; // Wand zwischen Feldern entfernen
                maze[ny][nx] = FieldType.PATH;         // Zielzelle zum Pfad machen
                carveMaze(maze, nx, ny);               // Rekursiv weitermachen
            }
        }
    }
    private boolean isInBounds(FieldType[][] maze, int x, int y) {
        return x > 0 && x < maze[0].length - 1 && y > 0 && y < maze.length - 1;
    }

    private void shuffleArray(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int[] temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }




    public FieldType[][] generateSimpleLabyrinth(int width, int height) {
        FieldType[][] labyrinth = new FieldType[height][width];
        
        // Alle Felder als Gänge initialisieren
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                labyrinth[y][x] = FieldType.PATH;
            }
        }
        // Alle Felder auf WALL setzen.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                labyrinth[y][x] = FieldType.WALL;
            }
        }

        // Untere Reihe bekommt auch PATHs
        for (int x = 1; x < width - 1; x++) {
            if (random.nextDouble() < 0.7) {
                labyrinth[height - 2][x] = FieldType.PATH;
            }
        }



        // Wände am Rand setzen
        for (int x = 0; x < width; x++) {
            labyrinth[0][x] = FieldType.WALL;           // Obere Wand
            labyrinth[height-1][x] = FieldType.WALL;    // Untere Wand
        }
        for (int y = 0; y < height; y++) {
            labyrinth[y][0] = FieldType.WALL;           // Linke Wand
            labyrinth[y][width-1] = FieldType.WALL;     // Rechte Wand
        }
        
        // Einige zufällige Wände innen hinzufügen
        int wallCount = (width * height) / 3; // Etwa 1/3 Wände
        for (int i = 0; i < wallCount; i++) {
            int x = 1 + random.nextInt(width - 4);
            int y = 1 + random.nextInt(height - 2);
            labyrinth[y][x] = FieldType.WALL;
        }

        if (labyrinth[2][1] == FieldType.WALL) {
            labyrinth[2][1] = FieldType.PATH;
        }

        // Pfad vom Start zum Ziel sicherstellen
        int x = 1;
        int y = 1;
        while (x < width - 2) {
            x++;
            labyrinth[y][x] = FieldType.PATH;
        }
        while (y < height - 2) {
            y++;
            labyrinth[y][x] = FieldType.PATH;
        }

        labyrinth[1][1] = FieldType.PATH;
        carveMaze(labyrinth, 1, 1);

        // Start und Ziel setzen
        labyrinth[1][1] = FieldType.START;
        labyrinth[height-2][width-2] = FieldType.GOAL; 

        //PATH über Ecke immer sperren -> wäre zu einfach.
        labyrinth[1][width - 2] = FieldType.WALL;
        
        return labyrinth;
    }
}