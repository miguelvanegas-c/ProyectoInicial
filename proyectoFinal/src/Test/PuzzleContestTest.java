package Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import puzzle.PuzzleContest;

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
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending = {{'.','.','.','r'},
                            {'.','.','.','b'},
                            {'.','.','g','r'},
                            {'.','b','m','y'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    
    @Test
    public void shouldSimulateThePuzzleStartingForLeftTilt(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending = {{'.','.','.','r'},
                            {'.','.','.','b'},
                            {'.','.','g','r'},
                            {'.','b','m','y'}};   
        puzzleContest.simulate(starting,ending);
        boolean isSolved = puzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    
    @Test
    public void shouldSolveThepuzzleStartingForRightTilt(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending = {{'y','.','.','.'},
                            {'b','.','.','.'},
                            {'r','m','.','.'},
                            {'r','g','b','.'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    @Test
    public void shouldSimulateThepuzzleStartingForRightTilt(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending = {{'y','.','.','.'},
                            {'b','.','.','.'},
                            {'r','m','.','.'},
                            {'r','g','b','.'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        puzzleContest.simulate(starting,ending);
        assertTrue(isSolved);    
    }
    @Test
    public void shouldSolveThepuzzleStartingForUpTilt(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending = {{'.','.','.','.'},
                            {'.','.','.','y'},
                            {'.','.','m','g'},
                            {'r','r','b','b'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    @Test
    public void shouldSimulateThepuzzleStartingForUpTilt(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending = {{'.','.','.','.'},
                            {'.','.','.','y'},
                            {'.','.','m','g'},
                            {'r','r','b','b'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        puzzleContest.simulate(starting,ending);
        assertTrue(isSolved);    
    }
    @Test
    public void shouldSolveThepuzzleStartingForDownTilt(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending = {{'b','g','b','r'},
                            {'.','.','m','r'},
                            {'.','.','.','y'},
                            {'.','.','.','.'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        assertTrue(isSolved);
    }
    @Test
    public void shouldSimulateThepuzzleStartingForDownTilt(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending = {{'.','.','.','.'},
                            {'.','.','.','y'},
                            {'.','.','m','g'},
                            {'r','r','b','b'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        puzzleContest.simulate(starting,ending);
        assertTrue(isSolved);    
    }
    @Test
    public void shouldNotSolverThePuzzleBecauseThereIsntOneTile(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending ={{'.','.','.','.'},
                            {'.','.','.','y'},
                            {'.','.','.','g'},
                            {'r','r','b','b'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        assertFalse(isSolved);  
                            
    }
    
    @Test
    public void shouldNotSolverThePuzzleBecauseTheEndingIsImposible(){
        PuzzleContest puzzleContest = new PuzzleContest();
        char [][] ending ={{'b','.','.','.'},
                            {'.','.','y','.'},
                            {'.','.','m','g'},
                            {'r','r','b','.'}};
        boolean isSolved = puzzleContest.solve(starting, ending);
        assertFalse(isSolved);  
                            
    }
}
