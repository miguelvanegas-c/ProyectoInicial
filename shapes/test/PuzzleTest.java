package test;
import puzzle.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    
    
    //Test of the first two cycles.
    @Test
    public void shouldAddTileInPuzzle() {
        puzzle.addTile(1, 1, "red",1); // Agregamos una baldosa roja en la posición 1,1
        assertEquals('r', puzzle.getStartingMatriz()[0][0]); // Verifica si starting añadió la baldosa.
        assertNotNull(puzzle.getBoard()[0][0]); // Verifica si el board añadió la baldosa.
    }
    
    @Test
    public void shouldValideOutOfRangeException() {
        PuzzleException thrown = assertThrows(PuzzleException.class,() -> { 
            puzzle.valideOutOfRange(5,5);
        });
        assertEquals(PuzzleException.ERROR_OUT_RANGE,thrown.getMessage());
    }
    
    @Test
    public void shouldNotAddTileIfPositionIsOccupied() {
        PuzzleException thrown = assertThrows(PuzzleException.class,() ->{
            puzzle.valideEmptySpace(0,1);
        });
        puzzle.addTile(1, 2, "yellow",1); // Intenta agregar en una posición ocupada
        assertNotEquals('y', puzzle.getStartingMatriz()[0][1]);
        assertEquals(PuzzleException.ERROR_SPACE_NO_EMPTY,thrown.getMessage());//verifica que la excepcion lanzada es igual a la esperada
    }

    @Test
    public void shouldDeleteTile() {
        puzzle.addTile(2, 2, "blue",1);
        puzzle.deleteTile(2, 2);
        assertEquals('.', puzzle.getStartingMatriz()[1][1]); // Verifica que la baldosa se eliminó
    }
    
    @Test
    public void shouldValideThereIsntTileToDelete(){
        PuzzleException thrown = assertThrows(PuzzleException.class,() ->{
            puzzle.valideTileToDelete(1,1);
        });
        assertEquals(PuzzleException.ERROR_NO_TILE_DELETE,thrown.getMessage());
    }
    
    @Test
    public void shouldRelocateTile() {
    puzzle.addTile(2, 2, "magenta",1); // Agregamos una baldosa magenta en la posición (2, 2)
    
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
    public void shouldNotRelocateBecauseTheTileIsGlued(){
        puzzle.addGlue(2,1,1);
        int[] from = {1,2};
        int[] to = {1,1};
        puzzle.relocateTile(from, to);
        PuzzleException thrown = assertThrows(PuzzleException.class,() ->{
            puzzle.valideTileNoGlued(0,1);
        });
        assertEquals(PuzzleException.ERROR_TILE_GLUED,thrown.getMessage());
        assertNotEquals(puzzle.getStartingMatriz()[0][1],'.');
        assertNotEquals(puzzle.getStartingMatriz()[0][0],'r');
    }
    
    @Test
    public void shouldNotRelocateBecauseThereIsntTileToRelocate(){
        int[] from = {1,1};
        int[] to = {1,3};
        puzzle.relocateTile(from, to);
        PuzzleException thrown = assertThrows(PuzzleException.class,() ->{
            puzzle.valideNotEmptySpace(0,0);
        });
        assertEquals(PuzzleException.ERROR_SPACE_EMPTY,thrown.getMessage());
    }
    
    @Test
    public void shouldTiltUp() {
        puzzle.tilt('u');
        char[][] expectedBoard = {
            {'b', 'r', 'm'},
            {'.', 'g', '.'},
            {'.', '.', '.'}
        };
        assertArrayEquals(expectedBoard, puzzle.getStartingMatriz());
    }

    @Test
    public void shouldTiltDown() {
        puzzle.tilt('d');
        char[][] expectedBoard = {
            {'.', '.', '.'},
            {'.', 'r', '.'},
            {'b', 'g', 'm'}
        };
        assertArrayEquals(expectedBoard, puzzle.getStartingMatriz());
    }
    
    @Test
    public void shouldTiltRight() {
        puzzle.tilt('r');
        char[][] expectedBoard = {
            {'.', '.', 'r'},
            {'.', '.', 'b'},
            {'.', 'g', 'm'}
        };
        assertArrayEquals(expectedBoard, puzzle.getStartingMatriz());
    }

    @Test
    public void shouldTiltLeft() {
        puzzle.tilt('l');
        char[][] expectedBoard = {
            {'r', '.', '.'},
            {'b', '.', '.'},
            {'g', 'm', '.'}
        };
        assertArrayEquals(expectedBoard, puzzle.getStartingMatriz());
    }
    
    @Test
    public void shouldAddGlue() {
        puzzle.addTile(2, 2, "red",1); // Asegúrate de agregar la baldosa primero
        puzzle.addGlue(2, 2, 1);  // Añadimos pegamento en la baldosa
    
        // Verificamos que la baldosa ahora está pegada
        assertTrue(puzzle.getBoard()[1][1].isGluedMidle()); // Verifica el estado de la baldosa
    }
    
    @Test
    public void shouldNotAddGlueBecauseTheTileIsGluedMidle() {
        puzzle.addTile(2, 2, "red",1); // Asegúrate de agregar la baldosa primero
        puzzle.addGlue(2, 2, 1);  // Añadimos pegamento en la baldosa
        puzzle.addGlue(2,2,1);
        PuzzleException thrown = assertThrows(PuzzleException.class,()->{
            puzzle.valideTileNotIsGluedMidle(1,1);       
        });
        // Verificamos que la baldosa ahora está pegada
        assertTrue(puzzle.getBoard()[1][1].isGluedMidle()); // Verifica el estado de la baldosa
        assertEquals(PuzzleException.ERROR_TILE_GLUED_MIDLE,thrown.getMessage());
    }
    
    @Test
    public void shouldDeleteGlue() {
        puzzle.addTile(2, 2, "blue",1); // Agregamos una baldosa azul en la posición (2, 2)
        puzzle.addGlue(2, 2, 1);  // Añadimos pegamento en la baldosa
    
        // Verificamos que la baldosa está pegada antes de eliminar el pegamento
        assertTrue(puzzle.getBoard()[1][1].isGluedMidle()); // Verificamos que está pegada (ajustar índices)
    
        puzzle.deleteGlue(2, 2);  // Eliminamos el pegamento
    
        // Verificamos que el pegamento se ha eliminado
        assertFalse(puzzle.getBoard()[1][1].isGluedMidle()); // Verificamos que ya no está pegada (ajustar índices)
    }
    
    @Test
    public void shouldNotDeleteGlueBecauseTheTileNotIsGluedMidle() {
        puzzle.addTile(2, 2, "blue",1); 
        puzzle.deleteGlue(2, 2);  
        PuzzleException thrown = assertThrows(PuzzleException.class,()->{
            puzzle.valideTileIsGluedMidle(1,1);       
        });
        assertFalse(puzzle.getBoard()[1][1].isGluedMidle()); 
        assertEquals(PuzzleException.ERROR_TILE_NOT_GLUED_MIDLE,thrown.getMessage());
    }
    
    @Test
    public void ShouldDeleteTileForHoleInTheTilt() {
        puzzle.makeHole(1, 3); 
        puzzle.tilt('r');   
        assertNull(puzzle.getBoard()[0][2]);  
    }

    @Test
    public void shouldMakeHole() {
        puzzle.makeHole(1, 1); // Creamos un agujero en la posición (1, 1)
        assertNotNull(puzzle.getMatrizHole()); // Verificamos que efectivamente hay un agujero en la posición (1, 1)
    }

    @Test
    public void shouldTestIntelligentTilt(){
        char[][] expectedBoard = {
            {'r', '.', '.'},
            {'b', '.', '.'},
            {'g', 'm', '.'}
        }; 
        puzzle.tilt();
        assertEquals(expectedBoard, puzzle.getStartingMatriz());
    }
    
    //Test cycle 4
    
    @Test
    public void shouldNotRelocateBecauseTheTileIsFixed(){
        puzzle.addTile(1,1,"blue",2);
        int[] from = {1,1};
        int[] to = {1,3};
        puzzle.relocateTile(from, to);
        PuzzleException thrown = assertThrows(PuzzleException.class,() ->{
            puzzle.valideTileNoFixed(0,0);
        });
        assertEquals(PuzzleException.ERROR_TILE_FIXED,thrown.getMessage());
        assertNotEquals(puzzle.getStartingMatriz()[0][0],'.');
        assertNotEquals(puzzle.getStartingMatriz()[0][2],'b');
    }    
    
    @Test
    public void shouldNotDeleteTileBecauseTheTileIsFixed() {
        puzzle.addTile(2, 2, "blue",2);
        puzzle.deleteTile(2, 2);
        PuzzleException thrown = assertThrows(PuzzleException.class,() ->{
            puzzle.valideTileNoFixed(1,1);
        });
        assertEquals(PuzzleException.ERROR_TILE_FIXED,thrown.getMessage());
        assertNotEquals('.', puzzle.getStartingMatriz()[1][1]); // Verifica que la baldosa se eliminó
    }
    
    @Test
    public void shouldNotMoveTileBecauseIsRough(){
        puzzle.addTile(1,1,"blue",3);
        puzzle.tilt('r');
        assertEquals('b', puzzle.getStartingMatriz()[0][0]);
    }
    
    @Test
    public void shouldNotAddGlueBecauseTheTileIsfreelance() {
        puzzle.addTile(2, 2, "red",4); // Asegúrate de agregar la baldosa primero
        puzzle.addGlue(2, 2, 1);  // Añadimos pegamento en la baldosa
        PuzzleException thrown = assertThrows(PuzzleException.class,()->{
            puzzle.valideTileNotFreelanceOrFlying(1,1);       
        });
        // Verificamos que la baldosa ahora está pegada
        assertFalse(puzzle.getBoard()[1][1].isGluedMidle()); // Verifica el estado de la baldosa
        assertEquals(PuzzleException.ERROR_TILE_FREELANCE_FLYING,thrown.getMessage());
    }
    
    @Test
    public void ShouldNotDeleteFlyingTileForHoleInTheTilt() {
        puzzle.addTile(1,1,"yellow",5);
        puzzle.makeHole(1, 3); 
        puzzle.tilt('r');   
        assertEquals(puzzle.getStartingMatriz()[0][2],'y');  
    }
    
    @Test
    public void ShouldMoveJustOneTimeTheSuperFragilTile(){
        puzzle.deleteTile(2,1);
        puzzle.addTile(1,1,"red",6);
        puzzle.tilt('d');
        assertNotNull(puzzle.getBoard()[1][0]);
    }
    
    @Test 
    public void shouldNotMoveFragilGlue(){
        puzzle.deleteTile(2,1);
        puzzle.addTile(2, 2, "red",1); 
        puzzle.addGlue(2, 2, 2);
        puzzle.tilt('l');
        puzzle.tilt('r');
        assertEquals(puzzle.getStartingMatriz()[1][0],'r'); 
    }
}