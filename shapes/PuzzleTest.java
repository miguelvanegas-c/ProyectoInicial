import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PuzzleTest {

    private Puzzle puzzle;

    @Before
    public void setUp() {
        // Creamos un puzzle con configuración inicial
        char[][] starting = {
            {'.', 'r', '.'},
            {'b', '.', '.'},
            {'.', 'g', 'm'}
        };
        
        char[][] ending = {
            {'r', '.', '.'},
            {'.', 'b', '.'},
            {'m', '.', 'g'}
        };
        
        puzzle = new Puzzle(starting, ending);
    }

    @Test
    public void shouldAddTileInPuzzle() {
        puzzle.addTile(1, 1, "red"); // Agregamos una baldosa roja en la posición 1,1
        assertEquals('r', puzzle.getStartingMatriz()[0][0]); // Verifica si starting añadió la baldosa.
        assertNotNull(puzzle.getBoard()[0][0]); // Verifica si el board añadió la baldosa.
    }
    
    @Test
    public void shouldNotAddTileIfPositionIsOccupied() {
        puzzle.addTile(1, 1, "yellow"); // Intenta agregar en una posición ocupada
        assertNotEquals('y', puzzle.getStartingMatriz()[1][1]);
    }

    @Test
    public void testDeleteTile() {
        puzzle.addTile(2, 2, "blue");
        puzzle.deleteTile(2, 2);
        assertEquals('.', puzzle.getStartingMatriz()[1][1]); // Verifica que la baldosa se eliminó
    }
    @Test
    public void testRelocateTile() {
    puzzle.addTile(2, 2, "magenta"); // Agregamos una baldosa magenta en la posición (2, 2)
    
    int[] from = {2, 2}; // Posición de origen (2, 2)
    int[] to = {1, 1};   // Posición de destino (1, 1)

    puzzle.relocateTile(from, to); // Movemos la baldosa

    // Verificamos que la baldosa se movió a la nueva posición
    assertEquals('m', puzzle.getStartingMatriz()[0][0]); // Comprobamos que la baldosa en la posición (1, 1) es 'm'

    // Verificamos que la posición original se vació
    assertEquals('.', puzzle.getStartingMatriz()[1][1]); // Comprobamos que la posición (2, 2) ahora está vacía

    // Verifica que el estado del tablero es correcto
    assertNull(puzzle.getBoard()[1][1]); // La posición (2, 2) debería ser nula
    assertNotNull(puzzle.getBoard()[0][0]); // La posición (1, 1) debería tener la baldosa
    }
   
    

   

    @Test
    public void testCharToColor() {
        assertEquals("red", puzzle.charToColor('r'));
        assertEquals("blue", puzzle.charToColor('b'));
    }

    @Test
    public void testColorToChar() {
        assertEquals('r', puzzle.colorToChar("red"));
        assertEquals('g', puzzle.colorToChar("green"));
    }

    @Test
    public void testTiltUp() {
        puzzle.tilt('u');
        char[][] expectedBoard = {
            {'b', 'r', 'm'},
            {'.', 'g', '.'},
            {'.', '.', '.'}
        };
        assertArrayEquals(expectedBoard, puzzle.getStartingMatriz());
    }

    @Test
    public void testTiltDown() {
        puzzle.tilt('d');
        char[][] expectedBoard = {
            {'.', '.', '.'},
            {'.', 'r', '.'},
            {'b', 'g', 'm'}
        };
        assertArrayEquals(expectedBoard, puzzle.getStartingMatriz());
    }
    @Test
    public void testAddGlue() {
    puzzle.addTile(2, 2, "red"); // Asegúrate de agregar la baldosa primero
    puzzle.addGlue(2, 2);  // Añadimos pegamento en la baldosa
    
    // Verificamos que la baldosa ahora está pegada
    assertTrue(puzzle.getBoard()[1][1].isGluedMidle()); // Verifica el estado de la baldosa
    }
    @Test
    public void testDeleteGlue() {
    puzzle.addTile(2, 2, "blue"); // Agregamos una baldosa azul en la posición (2, 2)
    puzzle.addGlue(2, 2);  // Añadimos pegamento en la baldosa

    // Verificamos que la baldosa está pegada antes de eliminar el pegamento
    assertTrue(puzzle.getBoard()[1][1].isGluedMidle()); // Verificamos que está pegada (ajustar índices)

    puzzle.deleteGlue(2, 2);  // Eliminamos el pegamento

    // Verificamos que el pegamento se ha eliminado
    assertFalse(puzzle.getBoard()[1][1].isGluedMidle()); // Verificamos que ya no está pegada (ajustar índices)
    }
    @Test
    public void testMoveWithHoles() {
    puzzle.makeHole(1, 1); // Creamos un agujero en la posición (1, 1)
    puzzle.addTile(0, 0, "red"); // Agregamos una baldosa roja en la posición (0, 0)

    int[] from = {0, 0}; // Coordenadas de la baldosa roja
    int[] to = {1, 1};   // Coordenadas del agujero

    puzzle.relocateTile(from, to); // Usamos relocateTile para mover la baldosa al agujero

    // Verificamos que la baldosa se cayó en el agujero
    assertNull(puzzle.getBoard()[1][1]); // Verificamos que la posición del agujero no tiene baldosa
    assertEquals('.', puzzle.getStartingMatriz()[0][0]); // Verificamos que la posición original ahora está vacía
    }




    

    @Test
    public void testMakeHole() {
        puzzle.makeHole(1, 1); // Creamos un agujero en la posición (1, 1)
        assertNotNull(puzzle.getMatrizHole()); // Verificamos que efectivamente hay un agujero en la posición (1, 1)
    }

    @Test
    public void testTileFallsThroughHole() {
    puzzle.makeHole(1, 1); // Creamos un agujero en la posición (1, 1)
    puzzle.addTile(0, 0, "red"); // Agregamos una baldosa roja en la posición (0, 0)
    int[] from = {0, 0}; // Coordenadas de la baldosa roja
    int[] to = {1, 1};   // Coordenadas del agujero

    puzzle.relocateTile(from, to); // Usamos relocateTile para mover la baldosa al agujero

    // Verificamos que la baldosa se cayó en el agujero
    assertNull(puzzle.getBoard()[1][1]); // Verificamos que la baldosa no está en la posición del agujero
    assertEquals('.', puzzle.getStartingMatriz()[0][0]); // Verificamos que la posición original ahora está vacía
    }

}