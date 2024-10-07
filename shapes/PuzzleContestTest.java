import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 * That class is gonna test all the case of solve, with a lot of the cycles defined in PuzzleContest
 * Class.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steff Contreras.
 * @version 1.0.0
 */
public class PuzzleContestTest{
    private char [][] starting;
    @Before
    public void setUp(){
        char [][] starting = {{'r', 'r', '.','y'},
                              {'b', '.', '.','.'},
                              {'.', 'g', 'm','.'},
                              {'b', '.', '.','.' }};
        this.starting = starting;
    }
    
    @Test
    public void shouldSolveThePuzzleStartingForLeftTilt(){
        char [][] ending = {{'.','.','.','r'},
                            {'.','.','.','b'},
                            {'.','.','g','r'},
                            {'.','b','m','y'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    
    @Test
    public void shouldSimulateThePuzzleStartingForLeftTilt(){
        char [][] ending = {{'.','.','.','r'},
                            {'.','.','.','b'},
                            {'.','.','g','r'},
                            {'.','b','m','y'}};   
        PuzzleContest.simulate(starting,ending);
        boolean isSolved = PuzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    
    @Test
    public void shouldSolveThepuzzleStartingForRightTilt(){
        char [][] ending = {{'y','.','.','.'},
                            {'b','.','.','.'},
                            {'r','m','.','.'},
                            {'r','g','b','.'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    @Test
    public void shouldSimulateThepuzzleStartingForRightTilt(){
        char [][] ending = {{'y','.','.','.'},
                            {'b','.','.','.'},
                            {'r','m','.','.'},
                            {'r','g','b','.'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        PuzzleContest.simulate(starting,ending);
        assertTrue(isSolved);    
    }
    @Test
    public void shouldSolveThepuzzleStartingForUpTilt(){
        char [][] ending = {{'.','.','.','.'},
                            {'.','.','.','y'},
                            {'.','.','m','g'},
                            {'r','r','b','b'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    @Test
    public void shouldSimulateThepuzzleStartingForUpTilt(){
        char [][] ending = {{'.','.','.','.'},
                            {'.','.','.','y'},
                            {'.','.','m','g'},
                            {'r','r','b','b'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        PuzzleContest.simulate(starting,ending);
        assertTrue(isSolved);    
    }
    @Test
    public void shouldSolveThepuzzleStartingForDownTilt(){
        char [][] ending = {{'b','g','b','r'},
                            {'.','.','m','r'},
                            {'.','.','.','y'},
                            {'.','.','.','.'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    @Test
    public void shouldSimulateThepuzzleStartingForDownTilt(){
        char [][] ending = {{'.','.','.','.'},
                            {'.','.','.','y'},
                            {'.','.','m','g'},
                            {'r','r','b','b'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        PuzzleContest.simulate(starting,ending);
        assertTrue(isSolved);    
    }
    @Test
    public void shouldNotSolverThePuzzleBecauseThereIsntOneTile(){
        char [][] ending ={{'.','.','.','.'},
                            {'.','.','.','y'},
                            {'.','.','.','g'},
                            {'r','r','b','b'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        assertFalse(isSolved);  
                            
    }
    
    @Test
    public void shouldNotSolverThePuzzleBecauseTheEndingIsImposible(){
        char [][] ending ={{'b','.','.','.'},
                            {'.','.','y','.'},
                            {'.','.','m','g'},
                            {'r','r','b','.'}};
        boolean isSolved = PuzzleContest.solve(starting, ending);
        assertFalse(isSolved);  
                            
    }
}
