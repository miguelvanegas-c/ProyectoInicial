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
        assertEquals('r', puzzle.getStartingMatriz()[0][0]); //Verifica si starting si añadio la baldoza.
        assertNotNull(puzzle.getBoard()[0][0]); // Verifica si el board si añadio la baldoza.
    }
    
    @Test
    public void shouldNotAddTileThePositionIsOcuppiedByAnotherTile(){
        puzzle.addTile(1, 2, "yellow");   
        assertNotEquals('y',puzzle.getStartingMatriz()[0][1]);
        assertNotEquals(
    }

    @Test
    public void testDeleteTile() {
        puzzle.addTile(2, 2, "blue");
        puzzle.deleteTile(2, 2);
        assertEquals('.', puzzle.getStartingMatriz()[1][1]);
    }

    @Test
    public void testRelocateTile() {
        puzzle.addTile(3, 3, "magenta");
        int[] from = {3, 3};
        int[] to = {1, 1};
        puzzle.relocateTile(from, to);
        assertEquals('m', puzzle.getStartingMatriz()[0][0]); // Comprobamos que la baldosa se movió
        assertEquals('.', puzzle.getStartingMatriz()[2][2]); // Comprobamos que la posición inicial se vació
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
        puzzle.addTile(1, 1, "red");
        puzzle.addGlue(1, 1);  // Añadimos pegamento en la baldosa
        assertTrue(puzzle.getBoard()[0][0].isGluedMidle()); // Verificamos si está pegada
    }

    @Test
    public void testDeleteGlue() {
        puzzle.addTile(2, 2, "blue");
        puzzle.addGlue(2, 2);  // Añadimos pegamento en la baldosa
        puzzle.deleteGlue(2, 2);  // Eliminamos el pegamento
        assertFalse(puzzle.getBoard()[1][1].isGluedMidle()); // Verificamos si el pegamento se eliminó
    }
}
