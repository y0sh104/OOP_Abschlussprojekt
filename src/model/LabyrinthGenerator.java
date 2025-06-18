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
    public FieldType[][] generateSimpleLabyrinth(int width, int height) {
        FieldType[][] labyrinth = new FieldType[height][width];
        
        // Alle Felder als Gänge initialisieren
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                labyrinth[y][x] = FieldType.PATH;
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
        int wallCount = (width * height) / 8; // Etwa 12,5% Wände
        for (int i = 0; i < wallCount; i++) {
            int x = 2 + random.nextInt(width - 4);
            int y = 2 + random.nextInt(height - 4);
            labyrinth[y][x] = FieldType.WALL;
        }
        
        // Start und Ziel setzen
        labyrinth[1][1] = FieldType.START;
        labyrinth[height-2][width-2] = FieldType.GOAL;
        
        return labyrinth;
    }
}