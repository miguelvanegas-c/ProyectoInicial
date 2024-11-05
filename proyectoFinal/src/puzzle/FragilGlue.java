package puzzle;
import shapes.*;

/**
 * It is a glue that just can move one time.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras
 * @version 1.0
 */
public class FragilGlue extends Glue{
    
    /**
     * Constructor for objects of class FragilGlue
     * @param matriz, matriz of the board.
     * @param board, board.
     * @param row, row of the midle
     * @param colum, colum of the midle
     * @param height, height of the matriz
     * @param width, width of the puzzle.
     * @param puzzle, the puzzle.
     *
     */
    public FragilGlue(char[][] matriz, Tile[][] board,int row,int colum, int height, int width,Puzzle puzzle){
        super(matriz,board, row, colum, height, width, puzzle);
    }

}
