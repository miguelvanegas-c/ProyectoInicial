package puzzle;
import shapes.*;

/**
 * It is a glue that just can move one time.
 * 
 * @author Miguel Angel Vanegas Cardenas y Allan Steef Contreras
 * @version 1.0
 */
public class FragilGlue extends Glue{
    private boolean canMove;
    
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
        canMove = true;
    }
    
    /**
     * tilt to the left
     */
    @Override
    public void tiltLeft(){
        super.tiltLeft();
        roughGlue();
    }
    
    /**
     * tilt to the down.
     */
    @Override
    public void tiltDown(){
        super.tiltDown();
        roughGlue();
    }
    
    /**
     * tilt to the up
     */
    @Override
    public void tiltUp(){
        super.tiltUp();
        roughGlue();
    }
    
    /**
     * tilt to the right
     */
    @Override
    public void tiltRight(){
        super.tiltRight();
        roughGlue();
    }
    
    /**
     * is possible make a right tilt.
     */
    @Override
    public boolean isPosibleRightTilt(){
       if(canMove){
           return super.isPosibleRightTilt();
       }
       return false;
    }
    
    /**
     * is possible make a left tilt.
     */
    @Override
    public boolean isPosibleLeftTilt(){
       if(canMove){
           return super.isPosibleLeftTilt();
       }
       return false;
    }
    
    /**
     * is possible make  up tilt.
     */
    @Override
    public boolean isPosibleUpTilt(){
       if(canMove){
           return super.isPosibleUpTilt();
       }
       return false;
    }
    
    /**
     * is possible make a down tilt.
     */
    @Override
    public boolean isPosibleDownTilt(){
       if(canMove){
           return super.isPosibleDownTilt();
       }
       return false;
    }
    
    /**
     * change the state of the fragil glue when it can´t move more. 
     */
    public void roughGlue(){
        for(int row = 0; row < height ; row++){
            for(int col = 0 ; col < width ; col ++){
                if (glueBoard[row][col] != null) glueBoard[row][col].changeColor("white");
            }
        }
    }
    
    /**
     * make that the glue can´t move.
     */
    public void makeNoMove(){
        canMove = false;
    }
    
}
